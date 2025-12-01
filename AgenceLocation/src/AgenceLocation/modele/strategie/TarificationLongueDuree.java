package modele.strategie;
import agenceLocation.modele.Reservation;

public class TarificationLongueDuree implements Tarification {

    @Override
    public double calculerPrix(Reservation r) {
        long jours = r.getNombreJours();
        double prixDeBase = r.getVoiture().getPrixParJour() * jours;

        if (jours >= 7) {
            return prixDeBase * 0.9;  // -10%
        }

        return prixDeBase;
    }
}

