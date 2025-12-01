package agenceLocation.modele;

public class Clients {

    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private boolean fidelite;

    public Clients(int id, String nom, String prenom, String telephone, String email, boolean fidelite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
       	this.email = email;
        this.fidelite = fidelite;
    }

    // --- Getters ---
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
    public boolean isFidele() { return fidelite; }

    // --- Setters ---
    public void setFidelite(boolean fidelite) {
        this.fidelite = fidelite;
    }

    @Override
    public String toString() {
        return "Client { "
                + "id = " + id
                + ", nom = '" + nom + '\''
                + ", prenom = '" + prenom + '\''
                + ", telephone = '" + telephone + '\''
                + ", email = '" + email + '\''
                + ", fidelite = " + (fidelite ? "Oui" : "Non")
                + " }";
    }
}
