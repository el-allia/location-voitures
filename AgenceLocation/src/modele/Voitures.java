package agenceLocation.modele;

public class Voitures {

    private int id;
    private String marque;
    private String modele;
    private String categorie;
    private double prixParJour;
    private boolean disponible;

    public Voitures(int id, String marque, String modele, String categorie, double prixParJour, boolean disponible) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.categorie = categorie;
        this.prixParJour = prixParJour;
        this.disponible = disponible;
    }

    // --- Getters ---
    public int getId() { return id; }
    public String getMarque() { return marque; }
    public String getModele() { return modele; }
    public String getCategorie() { return categorie; }
    public double getPrixParJour() { return prixParJour; }
    public boolean isDisponible() { return disponible; }

    // --- Setters ---
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Voiture { "
                + "id = " + id 
                + ", marque = '" + marque + '\''
                + ", modele = '" + modele + '\''
                + ", categorie = '" + categorie + '\''
                + ", prixParJour = " + prixParJour
                + ", disponible = " + (disponible ? "Oui" : "Non")
                + " }";
    }
}
