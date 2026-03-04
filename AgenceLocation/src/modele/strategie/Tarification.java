package modele.strategie;



public interface Tarification {
    double calculerPrix(AgenceLocation.Voiture.Reservations reservation);
}
