package Entity;

public class Student {

    private int id;
    private String name;
    private String course;

    // Constructor
    public Student(int i, String n, String c) {
        id = i;
        name = n;
        course = c;
    }

    // Display Method
    void display() {
        System.out.println("Id : " + id);
        System.out.println("Name : " + name);
        System.out.println("Course : " + course);
    }

    public static void main(String[] args) {

        Student s1 = new Student(2, "Abul", "C++");
        s1.display();
    }
}