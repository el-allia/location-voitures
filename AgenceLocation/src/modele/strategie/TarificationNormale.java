package modele.strategie;

import modele.strategie.Reservation;
import modele.strategie.Tarification;

public class TarificationNormale implements Tarification {

    @Override
    public double calculerPrix(Reservation r) {
        long jours = r.getNombreJours();
        return r.getVoiture().getPrixParJour() * jours;
    }
}
