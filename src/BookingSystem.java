import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BookingSystem {
    private List<Patient> patients;
    private List<Physiotherapist> physiotherapists;
    private List<Appointment> appointments;

    public BookingSystem() {
        patients = new ArrayList<>();
        physiotherapists = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    public void addPatient(Patient p) {
        patients.add(p);
    }

    public void removePatient(String id) {
        patients.removeIf(p -> p.id.equals(id));
    }

    public void generateReport() {
        System.out.println("=== Treatment Report ===");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d'th' MMMM yyyy, HH:mm");

        for (Physiotherapist p : physiotherapists) {
            System.out.println("Physiotherapist: " + p.name);
            System.out.println("  Phone: " + p.phone);
            for (Treatment t : p.getTreatments()) {
                String start = t.getDateTime().format(formatter);
                String end = t.getDateTime().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm"));
                System.out.println("  Treatment: " + t.getName() + ", Time: " + start + "–" + end);
            }
        }
    }

    public void loadSampleData() {
        // Add Physiotherapists

        Physiotherapist ph1 = new Physiotherapist("PH001", "Dr. Sara", "Islamabad", "03001234567");
        ph1.getExpertise().add("Massage");
        ph1.getExpertise().add("Physiotherapy");
        ph1.getExpertise().add("Neural mobilisation"); // based on treatment name
        ph1.addTreatment(new Treatment("Massage", LocalDateTime.of(2025, 5, 1, 10, 0), ph1));
        ph1.addTreatment(new Treatment("Neural mobilisation", LocalDateTime.of(2025, 5, 6, 14, 0), ph1));

        Physiotherapist ph2 = new Physiotherapist("PH002", "Dr. Kamran", "Lahore", "03009876543");
        ph2.getExpertise().add("Rehabilitation");
        ph2.getExpertise().add("Acupuncture"); // MUST add this so booking by expertise works
        ph2.addTreatment(new Treatment("Pool rehabilitation", LocalDateTime.of(2025, 5, 7, 9, 0), ph2));
        ph2.addTreatment(new Treatment("Acupuncture", LocalDateTime.of(2025, 5, 9, 11, 0), ph2));

        Physiotherapist ph3 = new Physiotherapist("PH003", "Dr. Hira", "Karachi", "03111234567");
        ph3.getExpertise().add("Osteopathy");
        ph3.getExpertise().add("Massage"); // Also offers massage
        ph3.getExpertise().add("Mobilisation of the spine");
        ph3.addTreatment(new Treatment("Mobilisation of the spine", LocalDateTime.of(2025, 5, 12, 15, 0), ph3));
        ph3.addTreatment(new Treatment("Massage", LocalDateTime.of(2025, 5, 2, 13, 0), ph3));

        physiotherapists.add(ph1);
        physiotherapists.add(ph2);
        physiotherapists.add(ph3);

        // Add Patients
        patients.add(new Patient("P001", "Ali", "Rawalpindi", "03001234567"));
        patients.add(new Patient("P002", "Ayesha", "Islamabad", "03004561234"));
        patients.add(new Patient("P003", "Zain", "Lahore", "03007891234"));
        patients.add(new Patient("P004", "Fatima", "Karachi", "03111234567"));
        patients.add(new Patient("P005", "Hassan", "Peshawar", "03451234567"));
        patients.add(new Patient("P006", "Maryam", "Faisalabad", "03211234567"));
        patients.add(new Patient("P007", "Tariq", "Multan", "03334567891"));
        patients.add(new Patient("P008", "Noor", "Sialkot", "03021234567"));
        patients.add(new Patient("P009", "Usman", "Hyderabad", "03104567891"));
        patients.add(new Patient("P010", "Laiba", "Quetta", "03457891234"));
    }


    public void displayAllPatients() {
        System.out.println("\nRegistered Patients:");
        for (Patient p : patients) {
            System.out.println("- " + p.getDetails());
        }
    }

    public Patient findPatientById(String id) {
        for (Patient p : patients) {
            if (p.id.equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public void bookAppointmentByExpertise(String expertise, Patient patient, Scanner scanner) {
        System.out.println("\nAvailable Treatments for expertise: " + expertise);
        List<Treatment> all = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d'th' MMMM yyyy, HH:mm");

        for (Physiotherapist ph : physiotherapists) {
            if (ph.getExpertise().contains(expertise)) {
                for (Treatment t : ph.getTreatments()) {
                    boolean taken = appointments.stream()
                            .anyMatch(a -> a.getTreatment().equals(t) && a.getStatus().equals("booked"));
                    if (!taken) {
                        String start = t.getDateTime().format(formatter);
                        System.out.println("ID: " + all.size() + " | " + t.getName() + " at " + start + " by " + ph.name);
                        all.add(t);
                    }
                }
            }
        }

        if (all.isEmpty()) {
            System.out.println("No available treatments.");
            return;
        }

        System.out.print("Enter ID to book: ");
        int idx = Integer.parseInt(scanner.nextLine());
        Treatment selected = all.get(idx);
        String bookingId = "BKG" + (appointments.size() + 1);
        Appointment a = new Appointment(bookingId, selected, patient);
        appointments.add(a);
        patient.bookAppointment(a);
        System.out.println("Booking confirmed. ID: " + bookingId);
    }

    public void bookAppointmentByPhysio(String name, Patient patient, Scanner scanner) {
        Physiotherapist ph = null;
        for (Physiotherapist x : physiotherapists) {
            if (x.name.equalsIgnoreCase(name)) {
                ph = x;
                break;
            }
        }

        if (ph == null) {
            System.out.println("No physiotherapist found.");
            return;
        }

        List<Treatment> available = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d'th' MMMM yyyy, HH:mm");

        for (Treatment t : ph.getTreatments()) {
            boolean taken = appointments.stream()
                    .anyMatch(a -> a.getTreatment().equals(t) && a.getStatus().equals("booked"));
            if (!taken) {
                String start = t.getDateTime().format(formatter);
                System.out.println("ID: " + available.size() + " | " + t.getName() + " at " + start);
                available.add(t);
            }
        }

        if (available.isEmpty()) {
            System.out.println("No available treatments for this physiotherapist.");
            return;
        }

        System.out.print("Enter ID to book: ");
        int idx = Integer.parseInt(scanner.nextLine());
        Treatment selected = available.get(idx);
        String bookingId = "BKG" + (appointments.size() + 1);
        Appointment a = new Appointment(bookingId, selected, patient);
        appointments.add(a);
        patient.bookAppointment(a);
        System.out.println("Booking confirmed. ID: " + bookingId);
    }
    public void displayAllAppointments() {
        System.out.println("\n=== Booked Appointments ===");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d'th' MMM yyyy, HH:mm");

        if (appointments.isEmpty()) {
            System.out.println("No appointments have been booked yet.");
            return;
        }

        for (Appointment a : appointments) {
            Treatment t = a.getTreatment();
            Patient p = a.getPatient();
            String start = t.getDateTime().format(formatter);
            String end = t.getDateTime().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm"));

            System.out.println("Booking ID: " + a.getBookingId());
            System.out.println("  Patient: " + p.name + " (" + p.id + ")");
            System.out.println("  Treatment: " + t.getName());
            System.out.println("  Time: " + start + "–" + end);
            System.out.println("  Physiotherapist: " + t.getPhysiotherapist().name);
            System.out.println("  Status: " + a.getStatus());
            System.out.println();
        }
    }

    public void cancelAppointment(String bookingId) {
        for (Appointment a : appointments) {
            if (a.getBookingId().equals(bookingId) && a.getStatus().equals("booked")) {
                a.changeStatus("cancelled");
                System.out.println("Booking " + bookingId + " cancelled.");
                return;
            }
        }
        System.out.println("Booking not found or already attended/cancelled.");
    }

    public void attendAppointment(String bookingId) {
        for (Appointment a : appointments) {
            if (a.getBookingId().equals(bookingId) && a.getStatus().equals("booked")) {
                a.changeStatus("attended");
                System.out.println("Appointment " + bookingId + " marked as attended.");
                return;
            }
        }
        System.out.println("Booking not found or already attended/cancelled.");
    }
    public void displayAllPatientsAndTreatmentCatalog() {
        // Part 1: Show all patients
        System.out.println("\n=== Registered Patients ===");
        for (Patient p : patients) {
            System.out.println("- " + p.name + " (" + p.id + "), " + p.address + ", " + p.phone);
        }

        // Part 2: Show treatment catalog
        System.out.println("\n=== Treatment Report ===");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d'th' MMMM yyyy, HH:mm");

        for (Physiotherapist p : physiotherapists) {
            System.out.println("Physiotherapist: " + p.name);
            System.out.println("  Phone: " + p.phone);
            for (Treatment t : p.getTreatments()) {
                String start = t.getDateTime().format(formatter);
                String end = t.getDateTime().plusHours(1).format(DateTimeFormatter.ofPattern("HH:mm"));
                System.out.println("  Treatment: " + t.getName() + ", Time: " + start + "–" + end);
            }
        }
    }

    public List<Physiotherapist> getPhysiotherapists() {
        return physiotherapists;
    }
}
