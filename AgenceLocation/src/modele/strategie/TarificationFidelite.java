package modele.strategie;

public class TarificationFidelite implements Tarification {

    @Override
    public double calculerPrix(AgenceLocation.Voiture.Reservations r) {
        double base = r.getVoiture().getPrixParJour() * r.getNombreJours();
        return base * 0.85; // -15%
    }
}
