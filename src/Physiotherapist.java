import java.util.ArrayList;
import java.util.List;

public class Physiotherapist extends Person {
    private List<String> expertise;
    private List<Treatment> treatments;

    public Physiotherapist(String id, String name, String address, String phone) {
        super(id, name, address, phone);
        this.expertise = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public List<Treatment> getAvailableTreatmentsByExpertise(String area) {
        List<Treatment> matched = new ArrayList<>();
        if (expertise.contains(area)) {
            for (Treatment t : treatments) {
                matched.add(t);
            }
        }
        return matched;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }
}
