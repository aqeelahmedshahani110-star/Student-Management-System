import java.sql.*;
import java.util.Scanner;

public class AirportTicketSystem {
    // MySQL Connection
    static final String URL =
            "jdbc:mysql://localhost:3306/airport_management";
    static final String USER = "root";
    static final String PASSWORD = "sha256";
    // Database Connection Method
    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
    // Main Method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n=================================");
            System.out.println("   AIRPORT TICKET SYSTEM");
            System.out.println("=================================");
            System.out.println("1. View Available Flights");
            System.out.println("2. Book a Ticket");
            System.out.println("3. Display Booked Ticket");
            System.out.println("4. Exit");
            System.out.println("=================================");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    viewAvailableFlights();
                    break;

                case 2:
                    bookTicket(sc);
                    break;

                case 3:
                    displayBookedTickets();
                    break;

                case 4:
                    System.out.println(
                            "Thank you for using Airport Ticket System!"
                    );
                    sc.close();
                    return;

                default:
                    System.out.println(
                            "Invalid choice! Please select 1-4."
                    );
            }
        }
    }
    //1. VIEW AVAILABLE FLIGHTS
    public static void viewAvailableFlights() {

        String sql =
                "SELECT * FROM flights " +
                        "WHERE available_seats > 0";

        try (
                Connection con = getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            System.out.println(
                    "\n========== AVAILABLE FLIGHTS =========="
            );

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "\nFlight ID: " +
                                rs.getInt("flight_id")
                );

                System.out.println(
                        "Flight Number: " +
                                rs.getString("flight_number")
                );

                System.out.println(
                        "From: " +
                                rs.getString("source")
                );

                System.out.println(
                        "To: " +
                                rs.getString("destination")
                );

                System.out.println(
                        "Departure: " +
                                rs.getTimestamp("departure_time")
                );

                System.out.println(
                        "Arrival: " +
                                rs.getTimestamp("arrival_time")
                );

                System.out.println(
                        "Available Seats: " +
                                rs.getInt("available_seats")
                );

                System.out.println(
                        "Price: Rs. " +
                                rs.getDouble("price")
                );

                System.out.println(
                        "---------------------------------"
                );
            }

            if (!found) {
                System.out.println(
                        "No flights available."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database Error: " +
                            e.getMessage()
            );
        }
    }
    // 2. BOOK A TICKET
    public static void bookTicket(Scanner sc) {

        Connection con = null;

        try {

            // Show available flights first
            viewAvailableFlights();

            System.out.print(
                    "\nEnter Flight ID: "
            );

            int flightId = sc.nextInt();
            sc.nextLine();


            System.out.print(
                    "Enter Passenger Name: "
            );

            String passengerName =
                    sc.nextLine();


            System.out.print(
                    "Enter CNIC: "
            );

            String cnic =
                    sc.nextLine();


            System.out.print(
                    "Enter Seat Number: "
            );

            String seatNumber =
                    sc.nextLine();


            System.out.println(
                    "\nSelect Ticket Class:"
            );

            System.out.println(
                    "1. Economy"
            );

            System.out.println(
                    "2. Business"
            );

            System.out.println(
                    "3. First Class"
            );

            System.out.print(
                    "Enter choice: "
            );

            int classChoice =
                    sc.nextInt();

            sc.nextLine();


            String ticketClass;

            if (classChoice == 1) {

                ticketClass = "Economy";

            } else if (classChoice == 2) {

                ticketClass = "Business";

            } else if (classChoice == 3) {

                ticketClass = "First Class";

            } else {

                System.out.println(
                        "Invalid class!"
                );

                return;
            }
            // Connect to database

            con = getConnection();

            con.setAutoCommit(false);
            // Check flight

            String checkFlight =
                    "SELECT available_seats " +
                            "FROM flights " +
                            "WHERE flight_id = ?";


            PreparedStatement flightPS =
                    con.prepareStatement(
                            checkFlight
                    );

            flightPS.setInt(
                    1,
                    flightId
            );


            ResultSet flightRS =
                    flightPS.executeQuery();


            if (!flightRS.next()) {

                System.out.println(
                        "Flight not found!"
                );

                con.rollback();
                return;
            }


            int seats =
                    flightRS.getInt(
                            "available_seats"
                    );


            if (seats <= 0) {

                System.out.println(
                        "No seats available!"
                );

                con.rollback();
                return;
            }


            // Check duplicate seat

            String checkSeat =
                    "SELECT ticket_id " +
                            "FROM tickets " +
                            "WHERE flight_id = ? " +
                            "AND seat_number = ?";


            PreparedStatement seatPS =
                    con.prepareStatement(
                            checkSeat
                    );


            seatPS.setInt(
                    1,
                    flightId
            );

            seatPS.setString(
                    2,
                    seatNumber
            );


            ResultSet seatRS =
                    seatPS.executeQuery();


            if (seatRS.next()) {

                System.out.println(
                        "This seat is already booked!"
                );

                con.rollback();
                return;
            }


            // Insert ticket

            String insertTicket =
                    "INSERT INTO tickets " +
                            "(passenger_name, cnic, flight_id, " +
                            "seat_number, ticket_class) " +
                            "VALUES (?, ?, ?, ?, ?)";


            PreparedStatement ticketPS =
                    con.prepareStatement(
                            insertTicket
                    );


            ticketPS.setString(
                    1,
                    passengerName
            );

            ticketPS.setString(
                    2,
                    cnic
            );

            ticketPS.setInt(
                    3,
                    flightId
            );

            ticketPS.setString(
                    4,
                    seatNumber
            );

            ticketPS.setString(
                    5,
                    ticketClass
            );


            ticketPS.executeUpdate();


            // Decrease available seats

            String updateFlight =
                    "UPDATE flights " +
                            "SET available_seats = " +
                            "available_seats - 1 " +
                            "WHERE flight_id = ?";


            PreparedStatement updatePS =
                    con.prepareStatement(
                            updateFlight
                    );


            updatePS.setInt(
                    1,
                    flightId
            );


            updatePS.executeUpdate();


            // Save changes

            con.commit();


            System.out.println(
                    "\n================================="
            );

            System.out.println(
                    "     TICKET BOOKED SUCCESSFULLY"
            );

            System.out.println(
                    "================================="
            );

            System.out.println(
                    "Passenger: " +
                            passengerName
            );

            System.out.println(
                    "CNIC: " +
                            cnic
            );

            System.out.println(
                    "Seat: " +
                            seatNumber
            );

            System.out.println(
                    "Class: " +
                            ticketClass
            );

            System.out.println(
                    "================================="
            );


        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (SQLException ignored) {
            }

            System.out.println(
                    "Booking Error: " +
                            e.getMessage()
            );

        } finally {

            try {

                if (con != null) {
                    con.close();
                }

            } catch (SQLException ignored) {
            }
        }
    }


    // ==========================================
    // 3. DISPLAY BOOKED TICKETS
    // ==========================================

    public static void displayBookedTickets() {

        String sql =
                "SELECT " +
                        "t.ticket_id, " +
                        "t.passenger_name, " +
                        "t.cnic, " +
                        "f.flight_number, " +
                        "f.source, " +
                        "f.destination, " +
                        "t.seat_number, " +
                        "t.ticket_class, " +
                        "f.price, " +
                        "t.booking_date " +

                        "FROM tickets t " +

                        "JOIN flights f " +
                        "ON t.flight_id = f.flight_id";


        try (
                Connection con = getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            System.out.println(
                    "\n========== BOOKED TICKETS =========="
            );


            boolean found = false;


            while (rs.next()) {

                found = true;

                System.out.println(
                        "\nTicket ID: " +
                                rs.getInt("ticket_id")
                );

                System.out.println(
                        "Passenger: " +
                                rs.getString("passenger_name")
                );

                System.out.println(
                        "CNIC: " +
                                rs.getString("cnic")
                );

                System.out.println(
                        "Flight: " +
                                rs.getString("flight_number")
                );

                System.out.println(
                        "Route: " +
                                rs.getString("source") +
                                " -> " +
                                rs.getString("destination")
                );

                System.out.println(
                        "Seat: " +
                                rs.getString("seat_number")
                );

                System.out.println(
                        "Class: " +
                                rs.getString("ticket_class")
                );

                System.out.println(
                        "Price: Rs. " +
                                rs.getDouble("price")
                );

                System.out.println(
                        "Booking Date: " +
                                rs.getTimestamp("booking_date")
                );

                System.out.println(
                        "---------------------------------"
                );
            }


            if (!found) {

                System.out.println(
                        "No tickets booked yet."
                );
            }


        } catch (SQLException e) {

            System.out.println(
                    "Database Error: " +
                            e.getMessage()
            );
        }
    }
}