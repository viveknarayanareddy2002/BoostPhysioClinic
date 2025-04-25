import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookingSystem bookingSystem = new BookingSystem();
        Scanner scanner = new Scanner(System.in);
        bookingSystem.loadSampleData();

        String choice;
        do {
            System.out.println("\n=== BOOST PHYSIO CLINIC BOOKING SYSTEM ===");
            System.out.println("1. View Patients & Treatments");
            System.out.println("2. Add Patient");
            System.out.println("3. Remove Patient");
            System.out.println("4. Book Appointment by Expertise");
            System.out.println("5. Book Appointment by Physiotherapist");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Attend Appointment");
            System.out.println("8. Generate Report");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    bookingSystem.displayAllPatientsAndTreatmentCatalog();
                    break;

                case "2":
                    System.out.print("Enter ID: ");
                    String newId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter Address: ");
                    String newAddress = scanner.nextLine();
                    System.out.print("Enter Phone: ");
                    String newPhone = scanner.nextLine();
                    Patient newPatient = new Patient(newId, newName, newAddress, newPhone);
                    bookingSystem.addPatient(newPatient);
                    System.out.println("✅ Patient added successfully.");
                    break;

                case "3":
                    System.out.print("Enter ID of patient to remove: ");
                    String removeId = scanner.nextLine();
                    bookingSystem.removePatient(removeId);
                    System.out.println("🗑️ Patient removed successfully (if found).");
                    break;

                case "4":
                    System.out.print("Enter patient ID: ");
                    Patient p1 = bookingSystem.findPatientById(scanner.nextLine());
                    if (p1 != null) {
                        System.out.print("Enter Expertise (e.g., Massage): ");
                        String expertise = scanner.nextLine();
                        bookingSystem.bookAppointmentByExpertise(expertise, p1, scanner);
                    } else {
                        System.out.println("❌ Patient not found.");
                    }
                    break;

                case "5":
                    System.out.print("Enter patient ID: ");
                    Patient p2 = bookingSystem.findPatientById(scanner.nextLine());
                    if (p2 != null) {
                        System.out.print("Enter Physiotherapist Name: ");
                        String name = scanner.nextLine();
                        bookingSystem.bookAppointmentByPhysio(name, p2, scanner);
                    } else {
                        System.out.println("❌ Patient not found.");
                    }
                    break;

                case "6":
                    System.out.print("Enter Booking ID to cancel: ");
                    bookingSystem.cancelAppointment(scanner.nextLine());
                    break;

                case "7":
                    System.out.print("Enter Booking ID to mark attended: ");
                    bookingSystem.attendAppointment(scanner.nextLine());
                    break;

                case "8":
                    bookingSystem.displayAllAppointments();
                    break;

                case "9":
                    System.out.println("👋 Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("❗ Invalid choice. Try again.");
            }

        } while (!choice.equals("9"));
    }
}
