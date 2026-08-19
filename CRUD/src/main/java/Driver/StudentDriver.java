package Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentDriver {

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/StudentDB",
                    "root",
                    "Satya@2005"
            );

            System.out.println("Connected Successfully");
            Statement st = con.createStatement();

            // insert
//           String insertQuery =
//                    "insert into Student values(6,'Alisha','Java')";
//
//            int insertResult = st.executeUpdate(insertQuery);
//
//           System.out.println(insertResult + " Row Inserted");


            //read
            String selectQuery = "select * from Student";
            ResultSet rs = st.executeQuery(selectQuery);
            System.out.println("\nStudent Table Data");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getString("course")
                );
            }
            //update
//            String updateQuery =
//                    "update Student set course='Python' where id=2";
//
//            int updateResult = st.executeUpdate(updateQuery);
//
//            System.out.println(updateResult + " Row Updated");

//            // delet
//            String deleteQuery =
//                    "delete from Student where id=1";
//
//            int deleteResult = st.executeUpdate(deleteQuery);
//
//            System.out.println(deleteResult + " Row Deleted");
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}