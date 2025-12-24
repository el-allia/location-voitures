package AgenceLocation;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;





public class modele {
	public interface Observateur {
	    void notifier(String message);
	}
	
	public class DB{
		

		public class DatabaseConnection {
		    private static Connection connection = null;
		    
		    
		    private static final String DB_TYPE = "sqlite"; 
		    
		    
		    
		    
		    private static final String SQLITE_URL = "jdbc:sqlite:database/agencelocation.db";
		    
		    
		    public static Connection getConnection() throws SQLException {
		        if (connection == null || connection.isClosed()) {
		            try {
		                 if (DB_TYPE.equals("sqlite")) {
		                   
		                    Class.forName("org.sqlite.JDBC");
		                    connection = DriverManager.getConnection(SQLITE_URL);
		                    System.out.println("Connected to SQLite database");
		                } else {
		                    throw new SQLException("Unsupported database type: " + DB_TYPE);
		                }
		            } catch (ClassNotFoundException e) {
		                throw new SQLException("JDBC Driver not found. Please add the driver to your classpath.", e);
		            }
		        }
		        return connection;
		    }
		    
		    
		    public static void closeConnection() {
		        try {
		            if (connection != null && !connection.isClosed()) {
		                connection.close();
		                System.out.println("Database connection closed");
		            }
		        } catch (SQLException e) {
		            System.err.println("Error closing database connection: " + e.getMessage());
		        }
		    }
		    
		    
		    public static boolean testConnection() {
		        try {
		            Connection conn = getConnection();
		            return conn != null && !conn.isClosed();
		        } catch (SQLException e) {
		            System.err.println("Connection test failed: " + e.getMessage());
		            return false;
		        }
		    }
		}

		public class DatabaseCleaner {

		    public static void clean() {

		        try (Connection conn = DatabaseConnection.getConnection();
		             Statement stmt = conn.createStatement()) {

		            // Enable FK constraints (important for SQLite)
		            stmt.execute("PRAGMA foreign_keys = ON");

		            stmt.executeUpdate("DELETE FROM historique");
		            stmt.executeUpdate("DELETE FROM reservations");
		            stmt.executeUpdate("DELETE FROM clients");
		            stmt.executeUpdate("DELETE FROM voitures");

		            // Reset AUTOINCREMENT counters (SQLite specific)
		            stmt.executeUpdate("DELETE FROM sqlite_sequence");

		            System.out.println("✅ Database cleaned successfully.");

		        } catch (SQLException e) {
		            System.err.println("❌ Error cleaning database");
		            e.printStackTrace();
		        }
		    }
		}
		public class DBManager {
			   
		    private static LocalDate parseDate(String dateStr) {
		        if (dateStr == null || dateStr.trim().isEmpty()) {
		            return null;
		        }
		        try {
		            return LocalDate.parse(dateStr);
		        } catch (DateTimeParseException e) {
		            System.err.println("Warning: Could not parse date: " + dateStr);
		            return null;
		        }
		    }
		    
		   
		     
		    public static ArrayList<VoitureData> getAllVoituresData() {
		        ArrayList<VoitureData> voituresData = new ArrayList<>();
		        String sql = "SELECT id, marque, modele, prix, disponible FROM voitures";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             Statement stmt = conn.createStatement();
		             ResultSet rs = stmt.executeQuery(sql)) {
		            
		            while (rs.next()) {
		                int id = rs.getInt("id");
		                String marque = rs.getString("marque");
		                String modele = rs.getString("modele");
		                double prix = rs.getDouble("prix");
		                boolean disponible = rs.getBoolean("disponible");
		                
		                voituresData.add(new VoitureData(id, marque, modele, prix, disponible));
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching voitures: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return voituresData;
		    }
		    
		   
		    public static VoitureData getVoitureDataById(int voitureId) {
		        String sql = "SELECT id, marque, modele, prix, disponible, FROM voitures WHERE id = ?";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            
		            pstmt.setInt(1, voitureId);
		            ResultSet rs = pstmt.executeQuery();
		            
		            if (rs.next()) {
		                int id = rs.getInt("id");
		                String marque = rs.getString("marque");
		                String modele = rs.getString("modele");
		                double prix = rs.getDouble("prix");
		                boolean disponible = rs.getBoolean("disponible");
		                
		                return new VoitureData(id, marque, modele, prix, disponible);
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching voiture: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return null;
		    }
		    
		    public static boolean insertVoiture(String marque, String modele, double prix,
		            boolean disponible, String pathToImage) {
		String sql = """
		INSERT INTO voitures (marque, modele, prix, disponible, path_to_image)
		VALUES (?, ?, ?, ?, ?)
		""";

		try (Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setString(1, marque);
		pstmt.setString(2, modele);
		pstmt.setDouble(3, prix);
		pstmt.setBoolean(4, disponible);
		pstmt.setString(5, pathToImage);

		return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
		System.err.println("Error inserting voiture: " + e.getMessage());
		return false;
		}
		}

		    public static boolean updateVoiture(int id, String marque, String modele,
		            double prix, boolean disponible, String pathToImage) {
		String sql = """
		UPDATE voitures
		SET marque = ?, modele = ?, prix = ?, disponible = ?, path_to_image = ?,
		updated_at = CURRENT_TIMESTAMP
		WHERE id = ?
		""";

		try (Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setString(1, marque);
		pstmt.setString(2, modele);
		pstmt.setDouble(3, prix);
		pstmt.setBoolean(4, disponible);
		pstmt.setString(5, pathToImage);
		pstmt.setInt(6, id);

		return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
		System.err.println("Error updating voiture: " + e.getMessage());
		return false;
		}
		}

		    public static ArrayList<ClientData> getAllClientsData() {
		        ArrayList<ClientData> clientsData = new ArrayList<>();
		        String sql = "SELECT id, f_name, l_name, adress, num_tell, num_permis FROM clients";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             Statement stmt = conn.createStatement();
		             ResultSet rs = stmt.executeQuery(sql)) {
		            
		            while (rs.next()) {
		                int id = rs.getInt("id");
		                String fName = rs.getString("f_name");
		                String lName = rs.getString("l_name");
		                String adress = rs.getString("adress");
		                String numTellStr = rs.getString("num_tell");
		                int numPermis = rs.getInt("num_permis");
		                
		                // Convert phone number string to int
		                int numTell = 0;
		                if (numTellStr != null && !numTellStr.isEmpty()) {
		                    try {
		                        String digitsOnly = numTellStr.replaceAll("[^0-9]", "");
		                        if (!digitsOnly.isEmpty()) {
		                            numTell = Integer.parseInt(digitsOnly);
		                        }
		                    } catch (NumberFormatException e) {
		                        System.err.println("Warning: Could not parse phone number: " + numTellStr);
		                    }
		                }
		                
		                clientsData.add(new ClientData(id, fName, lName, adress, numTell, numPermis));
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching clients: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return clientsData;
		    }
		    
		   
		    public static ClientData getClientDataById(int clientId) {
		        String sql = "SELECT id, f_name, l_name, adress, num_tell, num_permis FROM clients WHERE id = ?";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            
		            pstmt.setInt(1, clientId);
		            ResultSet rs = pstmt.executeQuery();
		            
		            if (rs.next()) {
		                int id = rs.getInt("id");
		                String fName = rs.getString("f_name");
		                String lName = rs.getString("l_name");
		                String adress = rs.getString("adress");
		                String numTellStr = rs.getString("num_tell");
		                int numPermis = rs.getInt("num_permis");
		                
		                int numTell = 0;
		                if (numTellStr != null && !numTellStr.isEmpty()) {
		                    try {
		                        String digitsOnly = numTellStr.replaceAll("[^0-9]", "");
		                        if (!digitsOnly.isEmpty()) {
		                            numTell = Integer.parseInt(digitsOnly);
		                        }
		                    } catch (NumberFormatException e) {
		                        System.err.println("Warning: Could not parse phone number: " + numTellStr);
		                    }
		                }
		                
		                return new ClientData(id, fName, lName, adress, numTell, numPermis);
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching client: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return null;
		    }
		    
		    
		    public static ClientData getClientDataByNumPermis(int numPermis) {
		        String sql = "SELECT id, f_name, l_name, adress, num_tell, num_permis FROM clients WHERE num_permis = ?";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            
		            pstmt.setInt(1, numPermis);
		            ResultSet rs = pstmt.executeQuery();
		            
		            if (rs.next()) {
		                int id = rs.getInt("id");
		                String fName = rs.getString("f_name");
		                String lName = rs.getString("l_name");
		                String adress = rs.getString("adress");
		                String numTellStr = rs.getString("num_tell");
		                int numPermisDb = rs.getInt("num_permis");
		                
		                int numTell = 0;
		                if (numTellStr != null && !numTellStr.isEmpty()) {
		                    try {
		                        String digitsOnly = numTellStr.replaceAll("[^0-9]", "");
		                        if (!digitsOnly.isEmpty()) {
		                            numTell = Integer.parseInt(digitsOnly);
		                        }
		                    } catch (NumberFormatException e) {
		                        System.err.println("Warning: Could not parse phone number: " + numTellStr);
		                    }
		                }
		                
		                return new ClientData(id, fName, lName, adress, numTell, numPermisDb);
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching client by num_permis: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return null;
		    }
		    
		    public static boolean insertClient(String fName, String lName, String adress,
		            String numTell, int numPermis, boolean loyale) {
		String sql = """
		INSERT INTO clients (f_name, l_name, adress, num_tell, num_permis, loyale)
		VALUES (?, ?, ?, ?, ?, ?)
		""";

		try (Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setString(1, fName);
		pstmt.setString(2, lName);
		pstmt.setString(3, adress);
		pstmt.setString(4, numTell);
		pstmt.setInt(5, numPermis);
		pstmt.setBoolean(6, loyale);

		return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
		System.err.println("Error inserting client: " + e.getMessage());
		return false;
		}
		}
		    public static boolean updateClient(int id, String fName, String lName,
		            String adress, String numTell, boolean loyale) {
		String sql = """
		UPDATE clients
		SET f_name = ?, l_name = ?, adress = ?, num_tell = ?, loyale = ?,
		updated_at = CURRENT_TIMESTAMP
		WHERE id = ?
		""";

		try (Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setString(1, fName);
		pstmt.setString(2, lName);
		pstmt.setString(3, adress);
		pstmt.setString(4, numTell);
		pstmt.setBoolean(5, loyale);
		pstmt.setInt(6, id);

		return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
		System.err.println("Error updating client: " + e.getMessage());
		return false;
		}
		}

		    
		    
		    
		    public static ArrayList<ReservationData> getAllReservationsData() {
		        ArrayList<ReservationData> reservationsData = new ArrayList<>();
		        String sql = "SELECT id, client_id, voiture_id, date_reservation, " +
		                     "date_affectation, date_retour, prix, statut FROM reservations";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             Statement stmt = conn.createStatement();
		             ResultSet rs = stmt.executeQuery(sql)) {
		            
		            while (rs.next()) {
		                int id = rs.getInt("id");
		                int clientId = rs.getInt("client_id");
		                int voitureId = rs.getInt("voiture_id");
		                LocalDate dateReservation = parseDate(rs.getString("date_reservation"));
		                LocalDate dateAffectation = parseDate(rs.getString("date_affectation"));
		                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
		                double prix = rs.getDouble("prix");
		                String statut = rs.getString("statut");
		                
		                reservationsData.add(new ReservationData(id, clientId, voitureId, dateReservation, 
		                    dateAffectation, dateRetour, prix, statut));
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching reservations: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return reservationsData;
		    }
		    
		   
		    public static ArrayList<ReservationData> getReservationsDataByClientId(int clientId) {
		        ArrayList<ReservationData> reservationsData = new ArrayList<>();
		        String sql = "SELECT id, voiture_id, date_reservation, " +
		                     "date_affectation, date_retour, prix, statut " +
		                     "FROM reservations WHERE client_id = ?";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            
		            pstmt.setInt(1, clientId);
		            ResultSet rs = pstmt.executeQuery();
		            
		            while (rs.next()) {
		                int id = rs.getInt("id");
		                int voitureId = rs.getInt("voiture_id");
		                LocalDate dateReservation = parseDate(rs.getString("date_reservation"));
		                LocalDate dateAffectation = parseDate(rs.getString("date_affectation"));
		                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
		                double prix = rs.getDouble("prix");
		                String statut = rs.getString("statut");
		                
		                reservationsData.add(new ReservationData(id, clientId, voitureId, dateReservation, 
		                    dateAffectation, dateRetour, prix, statut));
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching reservations: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return reservationsData;
		    }
		    
		    
		    public static ArrayList<ReservationData> getReservationsDataByVoitureId(int voitureId) {
		        ArrayList<ReservationData> reservationsData = new ArrayList<>();
		        String sql = "SELECT id, client_id, date_reservation, " +
		                     "date_affectation, date_retour, prix, statut " +
		                     "FROM reservations WHERE voiture_id = ?";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            
		            pstmt.setInt(1, voitureId);
		            ResultSet rs = pstmt.executeQuery();
		            
		            while (rs.next()) {
		                int id = rs.getInt("id");
		                int clientId = rs.getInt("client_id");
		                LocalDate dateReservation = parseDate(rs.getString("date_reservation"));
		                LocalDate dateAffectation = parseDate(rs.getString("date_affectation"));
		                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
		                double prix = rs.getDouble("prix");
		                String statut = rs.getString("statut");
		                
		                reservationsData.add(new ReservationData(id, clientId, voitureId, dateReservation, 
		                    dateAffectation, dateRetour, prix, statut));
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching reservations: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return reservationsData;
		    }
		    public static boolean insertReservation(int clientId, int voitureId,
		            LocalDate dateReservation,
		            LocalDate dateAffectation,
		            LocalDate dateRetour,
		            double prix, String statut) {
		String sql = """
		INSERT INTO reservations
		(client_id, voiture_id, date_reservation, date_affectation,
		date_retour, prix, statut)
		VALUES (?, ?, ?, ?, ?, ?, ?)
		""";

		try (Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setInt(1, clientId);
		pstmt.setInt(2, voitureId);
		pstmt.setString(3, dateReservation.toString());
		pstmt.setString(4, dateAffectation != null ? dateAffectation.toString() : null);
		pstmt.setString(5, dateRetour.toString());
		pstmt.setDouble(6, prix);
		pstmt.setString(7, statut);

		return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
		System.err.println("Error inserting reservation: " + e.getMessage());
		return false;
		}
		}
		    public static boolean updateReservation(int id, LocalDate dateAffectation,
		            LocalDate dateRetour, double prix,
		            String statut) {
		String sql = """
		UPDATE reservations
		SET date_affectation = ?, date_retour = ?, prix = ?, statut = ?,
		updated_at = CURRENT_TIMESTAMP
		WHERE id = ?
		""";

		try (Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setString(1, dateAffectation != null ? dateAffectation.toString() : null);
		pstmt.setString(2, dateRetour.toString());
		pstmt.setDouble(3, prix);
		pstmt.setString(4, statut);
		pstmt.setInt(5, id);

		return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
		System.err.println("Error updating reservation: " + e.getMessage());
		return false;
		}
		}

		    
		    public static ArrayList<HistoriqueData> getHistoriqueDataByClientId(int clientId) {
		        ArrayList<HistoriqueData> historiqueData = new ArrayList<>();
		        String sql = "SELECT voiture_id, date_allocation, date_retour " +
		                     "FROM historique WHERE client_id = ? ORDER BY date_allocation";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            
		            pstmt.setInt(1, clientId);
		            ResultSet rs = pstmt.executeQuery();
		            
		            while (rs.next()) {
		                int voitureId = rs.getInt("voiture_id");
		                LocalDate dateAllocation = parseDate(rs.getString("date_allocation"));
		                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
		                
		                historiqueData.add(new HistoriqueData(clientId, voitureId, dateAllocation, dateRetour));
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching historique: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return historiqueData;
		    }
		    
		   
		    public static ArrayList<HistoriqueData> getAllHistoriqueData() {
		        ArrayList<HistoriqueData> historiqueData = new ArrayList<>();
		        String sql = "SELECT client_id, voiture_id, date_allocation, date_retour " +
		                     "FROM historique ORDER BY client_id, date_allocation";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             Statement stmt = conn.createStatement();
		             ResultSet rs = stmt.executeQuery(sql)) {
		            
		            while (rs.next()) {
		                int clientId = rs.getInt("client_id");
		                int voitureId = rs.getInt("voiture_id");
		                LocalDate dateAllocation = parseDate(rs.getString("date_allocation"));
		                LocalDate dateRetour = parseDate(rs.getString("date_retour"));
		                
		                historiqueData.add(new HistoriqueData(clientId, voitureId, dateAllocation, dateRetour));
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching historique: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return historiqueData;
		    }
		    public static boolean insertHistorique(int clientId, int voitureId,
		            LocalDate dateAllocation, LocalDate dateRetour) {
		String sql = """
		INSERT INTO historique (client_id, voiture_id, date_allocation, date_retour)
		VALUES (?, ?, ?, ?)
		""";

		try (Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setInt(1, clientId);
		pstmt.setInt(2, voitureId);
		pstmt.setString(3, dateAllocation.toString());
		pstmt.setString(4, dateRetour.toString());

		return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
		System.err.println("Error inserting historique: " + e.getMessage());
		return false;
		}
		}
		    public static boolean updateHistorique(int id, LocalDate dateRetour) {
		        String sql = """
		            UPDATE historique
		            SET date_retour = ?, updated_at = CURRENT_TIMESTAMP
		            WHERE id = ?
		            """;

		        try (Connection conn = DatabaseConnection.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {

		            pstmt.setString(1, dateRetour.toString());
		            pstmt.setInt(2, id);

		            return pstmt.executeUpdate() > 0;

		        } catch (SQLException e) {
		            System.err.println("Error updating historique: " + e.getMessage());
		            return false;
		        }
		    }

		    
		    public static ArrayList<VoitureData> getAvailableVoituresData() {
		        ArrayList<VoitureData> voituresData = new ArrayList<>();
		        String sql = "SELECT id, marque, modele, prix, disponible FROM voitures WHERE disponible = 1";
		        
		        try (Connection conn = DatabaseConnection.getConnection();
		             Statement stmt = conn.createStatement();
		             ResultSet rs = stmt.executeQuery(sql)) {
		            
		            while (rs.next()) {
		                int id = rs.getInt("id");
		                String marque = rs.getString("marque");
		                String modele = rs.getString("modele");
		                double prix = rs.getDouble("prix");
		                boolean disponible = rs.getBoolean("disponible");
		                
		                voituresData.add(new VoitureData(id, marque, modele, prix, disponible));
		            }
		        } catch (SQLException e) {
		            System.err.println("Error fetching available voitures: " + e.getMessage());
		            e.printStackTrace();
		        }
		        
		        return voituresData;
		    }
		    
		    
		    public static class VoitureData {
		        public final int id;
		        public final String marque;
		        public final String modele;
		        public final double prix;
		        public final boolean disponible;
		        
		        public VoitureData(int id, String marque, String modele, double prix, boolean disponible) {
		            this.id = id;
		            this.marque = marque;
		            this.modele = modele;
		            this.prix = prix;
		            this.disponible = disponible;
		        }
		    }
		    
		    
		    public static class ClientData {
		        public final int id;
		        public final String fName;
		        public final String lName;
		        public final String adress;
		        public final int numTell;
		        public final int numPermis;
		        
		        public ClientData(int id, String fName, String lName, String adress, int numTell, int numPermis) {
		            this.id = id;
		            this.fName = fName;
		            this.lName = lName;
		            this.adress = adress;
		            this.numTell = numTell;
		            this.numPermis = numPermis;
		        }
		    }
		    
		    
		    public static class ReservationData {
		        public final int id;
		        public final int clientId;
		        public final int voitureId;
		        public final LocalDate dateReservation;
		        public final LocalDate dateAffectation;
		        public final LocalDate dateRetour;
		        public final double prix;
		        public final String statut;
		        
		        public ReservationData(int id, int clientId, int voitureId, LocalDate dateReservation, 
		                              LocalDate dateAffectation, LocalDate dateRetour, double prix, String statut) {
		            this.id = id;
		            this.clientId = clientId;
		            this.voitureId = voitureId;
		            this.dateReservation = dateReservation;
		            this.dateAffectation = dateAffectation;
		            this.dateRetour = dateRetour;
		            this.prix = prix;
		            this.statut = statut;
		        }
		    }
		    
		    
		    public static class HistoriqueData {
		        public final int clientId;
		        public final int voitureId;
		        public final LocalDate dateAllocation;
		        public final LocalDate dateRetour;
		        
		        public HistoriqueData(int clientId, int voitureId, LocalDate dateAllocation, LocalDate dateRetour) {
		            this.clientId = clientId;
		            this.voitureId = voitureId;
		            this.dateAllocation = dateAllocation;
		            this.dateRetour = dateRetour;
		        }
		    }
		}
		public class FillData {

		    public static void filldata() {

		        /* =========================
		           INSERT VOITURES
		           ========================= */

		        DBManager.insertVoiture(
		                "Toyota", "Corolla", 4500.0, true,
		                "images/toyota_corolla.jpg"
		        );

		        DBManager.insertVoiture(
		                "Hyundai", "i20", 4000.0, true,
		                "images/hyundai_i20.jpg"
		        );

		        DBManager.insertVoiture(
		                "Peugeot", "208", 4200.0, false,
		                "images/peugeot_208.jpg"
		        );

		        DBManager.insertVoiture(
		                "Renault", "Clio 5", 4300.0, true,
		                "images/renault_clio5.jpg"
		        );

		        DBManager.insertVoiture(
		                "Volkswagen", "Golf 7", 5000.0, false,
		                "images/vw_golf7.jpg"
		        );

		        /* =========================
		           INSERT CLIENTS
		           ========================= */

		        DBManager.insertClient(
		                "Ahmed", "Benkhaled",
		                "Bab Ezzouar, Alger",
		                "0556123456",
		                123456,
		                true
		        );

		        DBManager.insertClient(
		                "Sara", "Mansouri",
		                "Hydra, Alger",
		                "0667458921",
		                234567,
		                false
		        );

		        DBManager.insertClient(
		                "Yacine", "Toumi",
		                "Oran Centre",
		                "0778899455",
		                345678,
		                true
		        );

		        DBManager.insertClient(
		                "Lina", "Cherif",
		                "Constantine",
		                "0544321987",
		                456789,
		                false
		        );

		        /* =========================
		           INSERT RESERVATIONS
		           ========================= */

		        DBManager.insertReservation(
		                1, // Aimed
		                3, // Budget 208
		                LocalDate.of(2025, 1, 10),
		                LocalDate.of(2025, 1, 10),
		                LocalDate.of(2025, 1, 15),
		                21000.0,
		                "confirmee"
		        );

		        DBManager.insertReservation(
		                2, // Sr
		                5, // Golf 7
		                LocalDate.of(2025, 2, 1),
		                LocalDate.of(2025, 2, 2),
		                LocalDate.of(2025, 2, 6),
		                25000.0,
		                "confirmee"
		        );

		        DBManager.insertReservation(
		                3, // Assen
		                1, // Gorilla
		                LocalDate.of(2025, 3, 5),
		                null,
		                LocalDate.of(2025, 3, 10),
		                22500.0,
		                "en_attente"
		        );

		        /* =========================
		           INSERT HISTORIQUE
		           ========================= */

		        DBManager.insertHistorique(
		                1, // Aimed
		                3, // Budget 208
		                LocalDate.of(2025, 1, 10),
		                LocalDate.of(2025, 1, 15)
		        );

		        DBManager.insertHistorique(
		                2, // Sr
		                5, // Golf 7
		                LocalDate.of(2025, 2, 2),
		                LocalDate.of(2025, 2, 6)
		        );

		        System.out.println("✅ Test data inserted successfully.");
		    }
		}

	}
	public class Strategie{
		public interface Tarification {
		    double calculerPrix(AgenceLocation.Voiture.Reservations reservation);
		}
		public class AgenceLocation {
			static Boolean is_created=false;
			public class Voiture{
				   public class Reservations{
				    	int ID;
				    	Clients client;
				    	LocalDate date_reservation,date_afectation,date_retoure;
				    	double prix;
				    	String statut;
				    	 Reservations(int ID,Clients client,Voiture voiture,LocalDate date_reservation,LocalDate date_afectation ,LocalDate date_retoure,double prix,String statut){
				    		 this.ID=ID;
				    		 this.client=client;
				    		 this.date_reservation=date_reservation;
				    		 this.date_afectation=date_afectation;
				    		 this.date_retoure=date_retoure;
				    		 this.prix=prix;
				    		 this.statut=statut;
				    		 
				    	 }
				    	 void afecter() {
				    		 if (this.date_afectation==LocalDate.now()) {
				    			 this.client.Alocate(voiture, date_afectation, date_retoure);
				    		 }else {
				    			 throw new RuntimeException("not the day of afection");
				    		 }
				    		 
				    	 }
						public  Voiture getVoiture() {
							
							return Voiture.this;
						}
						public long getNombreJours() {
							
							return ChronoUnit.DAYS.between(date_afectation, date_retoure);
						}
						
				    	
				    	
				    	
				    	
				    	
				    }
				int ID;
				String marque;
				String modele;
				double prix;
				Boolean disponible;
				ArrayList <Reservations> reseve;
				public Voiture(int ID,String modele,String marque,double prix,Boolean disponible ){
					this.ID=ID;
					this.marque=marque;
					this.modele=modele;
					this.prix=prix;
					this.disponible=disponible;
					
				}
				void seeVoiture() {
					System.out.println(ID);
					System.out.println(modele);
					System.out.println(marque);
					System.out.println(prix);
					System.out.println("disponible"+disponible);
					
				}
						
			
				
			}
			public class Clients{
				int ID,num_tell,num_permis;
				String f_name,l_name,adress;
				public class Alocation{
					Voiture voiture_alouer;
					LocalDate date_alocation,date_retoure;
					Alocation(Voiture voiture_alouer,LocalDate date_alocation,LocalDate date_retoure){
						this.voiture_alouer=voiture_alouer;
						this.date_alocation=date_alocation;
						this.date_retoure=date_retoure;}
					void seealocation() {
						this.voiture_alouer.seeVoiture();
						System.out.println("from "+this.date_alocation +" to "+this.date_retoure);
					}
					
				}
				ArrayList<Alocation> historique=new ArrayList<Alocation>();
				Clients(String f_name, String l_name,String adress,int ID,int num_tell,int num_permis ){
					this.ID=ID;
					this.adress=adress;
					this.f_name=f_name;
					this.l_name=l_name;
					this.num_permis=num_permis;
					this.num_tell=num_tell;
					this.historique=new ArrayList<Alocation>();
			
					
					
				}
				void Alocate(Voiture voiture_alouer,LocalDate date_alocation,LocalDate date_retoure){
					if (date_alocation.isBefore(date_retoure)) {
						this.historique.add(new Alocation(voiture_alouer,date_alocation,date_retoure));
						
						
					}
				}
				void seeClient() {
					System.out.println(f_name+" " +l_name);
					System.out.println("permis : "+num_permis);
					System.out.println("adress: "+adress);
					System.out.println("tellephon: "+ num_tell);
				}
				void seehistorique() {
					for (Alocation aloc :this.historique) {
						aloc.seealocation();
					}
						
					
				}
			}
		 
		    Voiture voiture;/*we will treat them one at the time */
		    Clients client;
		    
		    AgenceLocation( Voiture voiture,Clients client){
		    	if (!is_created) {
		    	this.client=client;
		    	this.voiture=voiture;
		    	
		    	is_created=true;
		    	}
		    	else {
		    		throw new RuntimeException("AgenceLoation must be unique");
		    		
		    	}
		    
		    	
		    }

		    
		    void addcar(int ID,String modele,String marque,double prix,Boolean disponible) {
		    	this.voiture=new Voiture(ID,modele,marque,prix,disponible);
		    	
		    }
		    
		   
		     
		    public void initializeFromDatabase(int voitureId, int clientId) {
		        DB.DBManager.VoitureData voitureData = DB.DBManager.getVoitureDataById(voitureId);
		        DB.DBManager.ClientData clientData = DB.DBManager.getClientDataById(clientId);
		        
		        if (voitureData != null) {
		            this.voiture = new Voiture(voitureData.id, voitureData.modele, voitureData.marque, 
		                voitureData.prix, voitureData.disponible);
		        }
		        
		        if (clientData != null) {
		            this.client = new Clients(clientData.fName, clientData.lName, clientData.adress, 
		                clientData.id, clientData.numTell, clientData.numPermis);
		            
		            
		            loadHistoriqueForClient(this.client);
		        }
		    }
		    
		    
		    public Voiture createVoitureFromData(DB.DBManager.VoitureData voitureData) {
		        if (voitureData == null) return null;
		        return new Voiture(voitureData.id, voitureData.modele, voitureData.marque, 
		            voitureData.prix, voitureData.disponible);
		    }
		    
		    
		    public Clients createClientFromData(DB.DBManager.ClientData clientData) {
		        if (clientData == null) return null;
		        
		        Clients client = new Clients(clientData.fName, clientData.lName, clientData.adress, 
		            clientData.id, clientData.numTell, clientData.numPermis);
		        
		        
		        loadHistoriqueForClient(client);
		        
		        return client;
		    }
		    
		   
		    public Voiture.Reservations createReservationFromData(DB.DBManager.ReservationData reservationData, 
		            Clients client, Voiture voiture) {
		        if (reservationData == null || client == null || voiture == null) return null;
		        
		        return voiture.new Reservations(reservationData.id, client, voiture, 
		            reservationData.dateReservation, reservationData.dateAffectation, 
		            reservationData.dateRetour, reservationData.prix, reservationData.statut);
		    }
		    
		  
		    private void loadHistoriqueForClient(Clients client) {
		        ArrayList<DB.DBManager.HistoriqueData> historiqueData = DB.DBManager.getHistoriqueDataByClientId(client.ID);
		        
		        for (DB.DBManager.HistoriqueData histData : historiqueData) {
		            
		            Voiture voiture = getVoitureById(histData.voitureId);
		            if (voiture == null) {
		                DB.DBManager.VoitureData voitureData = DB.DBManager.getVoitureDataById(histData.voitureId);
		                if (voitureData != null) {
		                    voiture = createVoitureFromData(voitureData);
		                }
		            }
		            
		            if (voiture != null) {
		                client.Alocate(voiture, histData.dateAllocation, histData.dateRetour);
		            }
		        }
		    }
		    
		   
		    private Voiture getVoitureById(int voitureId) {
		        
		        if (this.voiture != null && this.voiture.ID == voitureId) {
		            return this.voiture;
		        }
		        
		        DB.DBManager.VoitureData voitureData = DB.DBManager.getVoitureDataById(voitureId);
		        return createVoitureFromData(voitureData);
		    }
		   
		    public ArrayList<Voiture> getAllVoituresFromDB() {
		        ArrayList<Voiture> voitures = new ArrayList<>();
		        ArrayList<DB.DBManager.VoitureData> voituresData = DB.DBManager.getAllVoituresData();
		        
		        for (DB.DBManager.VoitureData voitureData : voituresData) {
		            voitures.add(createVoitureFromData(voitureData));
		        }
		        
		        return voitures;
		    }
		    
		   
		    public ArrayList<Clients> getAllClientsFromDB() {
		        ArrayList<Clients> clients = new ArrayList<>();
		        ArrayList<DB.DBManager.ClientData> clientsData = DB.DBManager.getAllClientsData();
		        
		        for (DB.DBManager.ClientData clientData : clientsData) {
		            clients.add(createClientFromData(clientData));
		        }
		        
		        return clients;
		    }
		        public ArrayList<Voiture.Reservations> getAllReservationsFromDB() {
		        ArrayList<Voiture.Reservations> reservations = new ArrayList<>();
		        ArrayList<DB.DBManager.ReservationData> reservationsData = DB.DBManager.getAllReservationsData();
		        
		        
		        java.util.HashMap<Integer, Clients> clientsCache = new java.util.HashMap<>();
		        java.util.HashMap<Integer, Voiture> voituresCache = new java.util.HashMap<>();
		        
		        for (DB.DBManager.ReservationData resData : reservationsData) {
		          
		            Clients client = clientsCache.get(resData.clientId);
		            if (client == null) {
		            	DB.DBManager.ClientData clientData = DB.DBManager.getClientDataById(resData.clientId);
		                if (clientData != null) {
		                    client = createClientFromData(clientData);
		                    clientsCache.put(resData.clientId, client);
		                }
		            }
		            
		            
		            Voiture voiture = voituresCache.get(resData.voitureId);
		            if (voiture == null) {
		                voiture = createVoitureFromData(DB.DBManager.getVoitureDataById(resData.voitureId));
		                if (voiture != null) {
		                    voituresCache.put(resData.voitureId, voiture);
		                }
		            }
		            
		            if (client != null && voiture != null) {
		                Voiture.Reservations reservation = createReservationFromData(resData, client, voiture);
		                if (reservation != null) {
		                    reservations.add(reservation);
		                }
		            }
		        }
		        
		        return reservations;
		    }
		    
		}
		public class TarificationFidelite implements Tarification {

		    @Override
		    public double calculerPrix(AgenceLocation.Voiture.Reservations r) {
		        double base = r. getVoiture().prix * r.getNombreJours();
		        return base * 0.85; // -15%
		    }
		}
		public class TarificationLongueDuree implements Tarification {

		    @Override
		    public double calculerPrix(AgenceLocation.Voiture.Reservations r) {
		        long jours = r.getNombreJours();
		        double prixDeBase = r.getVoiture().prix * jours;
		        if (jours >= 7) {
		            return prixDeBase * 0.9; // -10%
		        }
		        return prixDeBase;
		    }
		}
		public class TarificationNormale implements Tarification {

		    @Override
		    public double calculerPrix(AgenceLocation.Voiture.Reservations r) {
		        long jours = r.getNombreJours();
		        return r.getVoiture().prix * jours;
		    }
		}
	
	
	}
	
	public class SujetObservable {
	    private List<Observateur> observateurs = new ArrayList<>();

	    public void ajouterObservateur(Observateur o) {
	        observateurs.add(o);
	    }

	    public void notifierTous(String message) {
	        for (Observateur o : observateurs) {
	            o.notifier(message);
	        }
	    }
	}




}
