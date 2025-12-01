package modele.strategie;


public class TarificationAvecOptions implements Tarification {

    private Tarification base;

    public TarificationAvecOptions(Tarification base) {
        this.base = base;
    }

    @Override
    public double calculerPrix(Reservation r) {
        double prix = base.calculerPrix(r);

        if (r.isGps()) prix += 1000;
        if (r.isAssurance()) prix += 2000;

        return prix;
    }
}
