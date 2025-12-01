package modele.strategie;

import agenceLocation.modele.Reservation;

public interface Tarification {
    double calculerPrix(Reservation reservation);
}
