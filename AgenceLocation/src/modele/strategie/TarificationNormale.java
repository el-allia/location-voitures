package modele.strategie;

public class TarificationNormale implements Tarification {

    @Override
    public double calculerPrix(AgenceLocation.Voiture.Reservations r) {
        long jours = r.getNombreJours();
        return r.getVoiture().getPrixParJour() * jours;
    }
}
