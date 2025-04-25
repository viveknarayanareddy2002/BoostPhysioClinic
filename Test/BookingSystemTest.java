import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class BookingSystemTest {

    private BookingSystem system;
    private Patient patient;
    private Physiotherapist physio;

    @BeforeEach
    public void setUp() {
        system = new BookingSystem();
        system.loadSampleData();
        patient = system.findPatientById("P001"); // Example patient
        physio = system.getPhysiotherapists().get(0); // Dr. Sara
    }

    @Test
    public void testBookAppointmentInPatient() {
        Treatment treatment = new Treatment("Massage", LocalDateTime.of(2025, 5, 1, 10, 0), physio);
        Appointment appointment = new Appointment("BKG1", treatment, patient);
        patient.bookAppointment(appointment);
        assertEquals(1, patient.getAppointments().size());
        assertEquals("booked", appointment.getStatus());
    }

    @Test
    public void testChangeStatusOfAppointment() {
        Treatment treatment = new Treatment("Massage", LocalDateTime.of(2025, 5, 3, 9, 0), physio);
        Appointment appointment = new Appointment("BKG2", treatment, patient);
        appointment.changeStatus("attended");
        assertEquals("attended", appointment.getStatus());
    }

    @Test
    public void testFindPatientById() {
        Patient p = system.findPatientById("P001");
        assertNotNull(p);
        assertEquals("Ali", p.name); // Assuming Ali is P001
    }

    @Test
    public void testGetAvailableTreatmentsByExpertise() {
        boolean hasAvailable = physio.getAvailableTreatmentsByExpertise("Massage").size() > 0;
        assertTrue(hasAvailable);
    }

    @Test
    public void testTreatmentConstructor() {
        Treatment treatment = new Treatment("Acupuncture", LocalDateTime.of(2025, 5, 9, 11, 0), physio);
        assertEquals("Acupuncture", treatment.getName());
        assertEquals(physio, treatment.getPhysiotherapist());
        assertEquals(LocalDateTime.of(2025, 5, 9, 11, 0), treatment.getDateTime());
    }
}
