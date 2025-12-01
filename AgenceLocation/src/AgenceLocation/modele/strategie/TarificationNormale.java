package modele.strategie;

import agenceLocation.modele.Reservation;
public class TarificationNormale implements Tarification {

    @Override
    public double calculerPrix(Reservation r) {
        long jours = r.getNombreJours();
        return r.getVoiture().getPrixParJour() * jours;
    }
}
