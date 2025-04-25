public class Appointment {
    private String bookingId;
    private Treatment treatment;
    private Patient patient;
    private String status; // "booked", "attended", "cancelled"

    public Appointment(String bookingId, Treatment treatment, Patient patient) {
        this.bookingId = bookingId;
        this.treatment = treatment;
        this.patient = patient;
        this.status = "booked";
    }

    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getBookingId() { return bookingId; }
    public String getStatus() { return status; }
    public Patient getPatient() { return patient; }
    public Treatment getTreatment() { return treatment; }
}
