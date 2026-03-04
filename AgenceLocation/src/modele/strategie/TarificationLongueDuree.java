package modele.strategie;

public class TarificationLongueDuree implements Tarification {

    @Override
    public double calculerPrix(AgenceLocation.Voiture.Reservations r) {
        long jours = r.getNombreJours();
        double prixDeBase = r.getVoiture().getPrixParJour() * jours;
        if (jours >= 7) {
            return prixDeBase * 0.9; // -10%
        }
        return prixDeBase;
    }
}
