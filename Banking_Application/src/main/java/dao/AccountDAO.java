package dao;

import Model.Account;
import Util.DBConnection;
import java.sql.*;

public class AccountDAO {

    // 1. Create a bank Account
    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (holder_name, pin, balance) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, account.getHolderName());
            pstmt.setString(2, account.getPin());
            pstmt.setDouble(3, account.getBalance());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    account.setAccountNumber(generatedId); // Set ID back to the object
                    System.out.println("✅ Account created successfully!");
                    System.out.println("📌 Your Account Number is: " + generatedId);
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
        return false;
    }

    // 2. Fetch Account details
    public Account getAccount(int accNum) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accNum);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                return new Account(
                        rs.getInt("account_number"),
                        rs.getString("holder_name"),
                        rs.getString("pin"),
                        rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
        return null;
    }

    // 3. Authenticate Login
    public Account authenticate(int accNum, String pin) {
        Account account = getAccount(accNum);
        if (account != null && account.getPin().equals(pin)) {
            return account; // Login success, return account object
        }
        return null;
    }

    // 4. Money Transfer
    public boolean transferMoney(Account sender, int receiverAccNum, double amount) {
        String deductSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String addSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String logTxSql = "INSERT INTO transactions (account_number, type, amount) VALUES (?, ?, ?)";

        if (sender.getBalance() < amount) {
            System.out.println("❌ Transfer Failed: Insufficient balance!");
            return false;
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Deduct from Sender
            PreparedStatement deductStmt = conn.prepareStatement(deductSql);
            deductStmt.setDouble(1, amount);
            deductStmt.setInt(2, sender.getAccountNumber());
            int rows1 = deductStmt.executeUpdate();

            // Add to Receiver
            PreparedStatement addStmt = conn.prepareStatement(addSql);
            addStmt.setDouble(1, amount);
            addStmt.setInt(2, receiverAccNum);
            int rows2 = addStmt.executeUpdate();

            if (rows1 == 0 || rows2 == 0) {
                System.out.println("❌ Transfer Failed: Receiver account not found.");
                conn.rollback();
                return false;
            }

            // Log both transactions
            PreparedStatement logSender = conn.prepareStatement(logTxSql);
            logSender.setInt(1, sender.getAccountNumber());
            logSender.setString(2, "TRANSFER_SENT");
            logSender.setDouble(3, amount);
            logSender.executeUpdate();

            PreparedStatement logReceiver = conn.prepareStatement(logTxSql);
            logReceiver.setInt(1, receiverAccNum);
            logReceiver.setString(2, "TRANSFER_RECEIVED");
            logReceiver.setDouble(3, amount);
            logReceiver.executeUpdate();

            conn.commit();
            sender.setBalance(sender.getBalance() - amount);
            System.out.println("✅ Transfer of $" + amount + " successful!");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Transaction Error. Rolling back...");
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}