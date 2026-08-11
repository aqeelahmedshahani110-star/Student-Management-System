```java
import java.sql.*;
import java.util.Scanner;

public class main4 {

    static String url = "jdbc:mysql://localhost:3306/student_management";
    static String username = "root";
    static String password = "sha256";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Course Management");
            System.out.println("7. Attendance Management");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            // ================= ADD STUDENT =================
            if (choice == 1) {

                System.out.print("Enter student name: ");
                String name = sc.nextLine();

                System.out.print("Enter email: ");
                String email = sc.nextLine();

                System.out.print("Enter phone: ");
                String phone = sc.nextLine();

                System.out.print("Enter department: ");
                String department = sc.nextLine();

                try {
                    Connection con = DriverManager.getConnection(
                            url, username, password);

                    String sql = "INSERT INTO students " +
                            "(name, email, phone, department) VALUES (?, ?, ?, ?)";

                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, name);
                    pst.setString(2, email);
                    pst.setString(3, phone);
                    pst.setString(4, department);

                    pst.executeUpdate();

                    System.out.println("Student Added Successfully!");

                    con.close();

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }

            // ================= VIEW STUDENTS =================
            else if (choice == 2) {

                try {
                    Connection con = DriverManager.getConnection(
                            url, username, password);

                    String sql = "SELECT * FROM students";
                    Statement st = con.createStatement();

                    ResultSet rs = st.executeQuery(sql);

                    System.out.println("\n===== STUDENTS =====");

                    while (rs.next()) {

                        System.out.println(
                                "ID: " + rs.getInt("id") +
                                " | Name: " + rs.getString("name") +
                                " | Email: " + rs.getString("email") +
                                " | Phone: " + rs.getString("phone") +
                                " | Department: " + rs.getString("department")
                        );
                    }

                    con.close();

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }

            // ================= UPDATE STUDENT =================
            else if (choice == 3) {

                System.out.print("Enter Student ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter new name: ");
                String name = sc.nextLine();

                System.out.print("Enter new email: ");
                String email = sc.nextLine();

                System.out.print("Enter new phone: ");
                String phone = sc.nextLine();

                System.out.print("Enter new department: ");
                String department = sc.nextLine();

                try {
                    Connection con = DriverManager.getConnection(
                            url, username, password);

                    String sql = "UPDATE students SET name=?, email=?, " +
                            "phone=?, department=? WHERE id=?";

                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, name);
                    pst.setString(2, email);
                    pst.setString(3, phone);
                    pst.setString(4, department);
                    pst.setInt(5, id);

                    int rows = pst.executeUpdate();

                    if (rows > 0)
                        System.out.println("Student Updated Successfully!");
                    else
                        System.out.println("Student Not Found!");

                    con.close();

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }

            // ================= DELETE STUDENT =================
            else if (choice == 4) {

                System.out.print("Enter Student ID to delete: ");
                int id = sc.nextInt();

                try {
                    Connection con = DriverManager.getConnection(
                            url, username, password);

                    String sql = "DELETE FROM students WHERE id=?";

                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, id);

                    int rows = pst.executeUpdate();

                    if (rows > 0)
                        System.out.println("Student Deleted Successfully!");
                    else
                        System.out.println("Student Not Found!");

                    con.close();

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }

            // ================= SEARCH STUDENT =================
            else if (choice == 5) {

                System.out.print("Enter Student ID: ");
                int id = sc.nextInt();

                try {
                    Connection con = DriverManager.getConnection(
                            url, username, password);

                    String sql = "SELECT * FROM students WHERE id=?";

                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, id);

                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {

                        System.out.println("\nStudent Found!");
                        System.out.println("ID: " + rs.getInt("id"));
                        System.out.println("Name: " + rs.getString("name"));
                        System.out.println("Email: " + rs.getString("email"));
                        System.out.println("Phone: " + rs.getString("phone"));
                        System.out.println("Department: " +
                                rs.getString("department"));

                    } else {
                        System.out.println("Student Not Found!");
                    }

                    con.close();

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

            // ================= COURSE MANAGEMENT =================
            else if (choice == 6) {

                System.out.println("\n===== COURSE MANAGEMENT =====");
                System.out.println("1. Add Course");
                System.out.println("2. View Courses");
                System.out.println("3. Back");

                System.out.print("Enter your choice: ");
                int courseChoice = sc.nextInt();
                sc.nextLine();

                // ================= ADD COURSE =================
                if (courseChoice == 1) {

                    System.out.print("Enter course name: ");
                    String courseName = sc.nextLine();

                    System.out.print("Enter instructor name: ");
                    String instructor = sc.nextLine();

                    System.out.print("Enter credit hours: ");
                    int creditHours = sc.nextInt();

                    try {
                        Connection con = DriverManager.getConnection(
                                url, username, password);

                        String sql = "INSERT INTO courses " +
                                "(course_name, instructor, credit_hours) " +
                                "VALUES (?, ?, ?)";

                        PreparedStatement pst = con.prepareStatement(sql);

                        pst.setString(1, courseName);
                        pst.setString(2, instructor);
                        pst.setInt(3, creditHours);

                        pst.executeUpdate();

                        System.out.println("Course Added Successfully!");

                        con.close();

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                }

                // ================= VIEW COURSES =================
                else if (courseChoice == 2) {

                    try {
                        Connection con = DriverManager.getConnection(
                                url, username, password);

                        String sql = "SELECT * FROM courses";
                        Statement st = con.createStatement();

                        ResultSet rs = st.executeQuery(sql);

                        System.out.println("\n===== COURSES =====");

                        while (rs.next()) {

                            System.out.println(
                                    "ID: " + rs.getInt("id") +
                                    " | Course: " +
                                    rs.getString("course_name") +
                                    " | Instructor: " +
                                    rs.getString("instructor") +
                                    " | Credit Hours: " +
                                    rs.getInt("credit_hours")
                            );
                        }

                        con.close();

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }

            // ================= ATTENDANCE MANAGEMENT =================
            else if (choice == 7) {

                System.out.println("\n===== ATTENDANCE MANAGEMENT =====");
                System.out.println("1. Mark Attendance");
                System.out.println("2. View Attendance");
                System.out.println("3. Search Student Attendance");
                System.out.println("4. Attendance Percentage");
                System.out.println("5. Back");

                System.out.print("Enter your choice: ");
                int attendanceChoice = sc.nextInt();
                sc.nextLine();

                // ================= MARK ATTENDANCE =================
                if (attendanceChoice == 1) {

                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = sc.nextLine();

                    System.out.print("Enter status (Present/Absent): ");
                    String status = sc.nextLine();

                    try {
                        Connection con = DriverManager.getConnection(
                                url, username, password);

                        String sql = "INSERT INTO attendance " +
                                "(student_id, attendance_date, status) " +
                                "VALUES (?, ?, ?)";

                        PreparedStatement pst =
                                con.prepareStatement(sql);

                        pst.setInt(1, studentId);
                        pst.setString(2, date);
                        pst.setString(3, status);

                        pst.executeUpdate();

                        System.out.println(
                                "Attendance Marked Successfully!");

                        con.close();

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                // ================= VIEW ATTENDANCE =================
                else if (attendanceChoice == 2) {

                    try {
                        Connection con = DriverManager.getConnection(
                                url, username, password);

                        String sql =
                                "SELECT a.id, a.student_id, s.name, " +
                                "a.attendance_date, a.status " +
                                "FROM attendance a " +
                                "JOIN students s ON a.student_id = s.id";

                        Statement st = con.createStatement();

                        ResultSet rs = st.executeQuery(sql);

                        System.out.println("\n===== ATTENDANCE =====");

                        while (rs.next()) {

                            System.out.println(
                                    "Attendance ID: " +
                                    rs.getInt("id") +
                                    " | Student ID: " +
                                    rs.getInt("student_id") +
                                    " | Name: " +
                                    rs.getString("name") +
                                    " | Date: " +
                                    rs.getDate("attendance_date") +
                                    " | Status: " +
                                    rs.getString("status")
                            );
                        }

                        con.close();

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                // ================= SEARCH STUDENT ATTENDANCE =================
                else if (attendanceChoice == 3) {

                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();

                    try {
                        Connection con = DriverManager.getConnection(
                                url, username, password);

                        String sql =
                                "SELECT a.student_id, s.name, " +
                                "a.attendance_date, a.status " +
                                "FROM attendance a " +
                                "JOIN students s ON a.student_id = s.id " +
                                "WHERE a.student_id=?";

                        PreparedStatement pst =
                                con.prepareStatement(sql);

                        pst.setInt(1, studentId);

                        ResultSet rs = pst.executeQuery();

                        System.out.println(
                                "\n===== STUDENT ATTENDANCE =====");

                        while (rs.next()) {

                            System.out.println(
                                    "Student ID: " +
                                    rs.getInt("student_id") +
                                    " | Name: " +
                                    rs.getString("name") +
                                    " | Date: " +
                                    rs.getDate("attendance_date") +
                                    " | Status: " +
                                    rs.getString("status")
                            );
                        }

                        con.close();

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                // ================= ATTENDANCE PERCENTAGE =================
                else if (attendanceChoice == 4) {

                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();

                    try {
                        Connection con = DriverManager.getConnection(
                                url, username, password);

                        String sql =
                                "SELECT " +
                                "COUNT(*) AS total, " +
                                "SUM(CASE WHEN status='Present' " +
                                "THEN 1 ELSE 0 END) AS present " +
                                "FROM attendance " +
                                "WHERE student_id=?";

                        PreparedStatement pst =
                                con.prepareStatement(sql);

                        pst.setInt(1, studentId);

                        ResultSet rs = pst.executeQuery();

                        if (rs.next()) {

                            int total = rs.getInt("total");
                            int present = rs.getInt("present");

                            if (total > 0) {

                                double percentage =
                                        ((double) present / total) * 100;

                                System.out.println(
                                        "Total Classes: " + total);

                                System.out.println(
                                        "Present: " + present);

                                System.out.println(
                                        "Attendance Percentage: " +
                                        percentage + "%");

                            } else {
                                System.out.println(
                                        "No Attendance Record Found!");
                            }
                        }

                        con.close();

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }

            // ================= EXIT =================
            else if (choice == 8) {

                System.out.println("Thank you!");
                break;

            }

            else {
                System.out.println("Invalid Choice!");
            }
        }

        sc.close();
    }
}
```
