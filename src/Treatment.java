import java.time.LocalDateTime;

public class Treatment {
    private String name;
    private LocalDateTime dateTime;
    private Physiotherapist physiotherapist;

    public Treatment(String name, LocalDateTime dateTime, Physiotherapist physiotherapist) {
        this.name = name;
        this.dateTime = dateTime;
        this.physiotherapist = physiotherapist;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Physiotherapist getPhysiotherapist() {
        return physiotherapist;
    }

    @Override
    public String toString() {
        return name + " at " + dateTime.toString();
    }

    // Optional: Add this if you want to customize how it's shown everywhere
    public String getFormattedTime() {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("EEEE d'th' MMMM yyyy, HH:mm"));
    }
}
