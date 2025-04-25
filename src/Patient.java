import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private List<Appointment> appointments;

    public Patient(String id, String name, String address, String phone) {
        super(id, name, address, phone);
        this.appointments = new ArrayList<>();
    }

    public void bookAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void cancelAppointment(String bookingId) {
        // logic to cancel based on bookingId
    }

    public void attendAppointment(String bookingId) {
        // logic to attend appointment
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
