package agenceLocation.modele;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {

    private Clients client;
    private Voitures voiture;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    private boolean gps;
    private boolean assurance;

    public Reservation(Clients client, Voitures voiture, LocalDate dateDebut, LocalDate dateFin) {
        this.client = client;
        this.voiture = voiture;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public long getNombreJours() {
        return ChronoUnit.DAYS.between(dateDebut, dateFin);
    }

    public Clients getClient() { return client; }
    public Voitures getVoiture() { return voiture; }

    public boolean isGps() { return gps; }
    public boolean isAssurance() { return assurance; }

    public void setGps(boolean gps) { this.gps = gps; }
    public void setAssurance(boolean assurance) { this.assurance = assurance; }
}
