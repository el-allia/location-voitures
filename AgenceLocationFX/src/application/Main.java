package application;

import application.model.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    private static String searchQuery = "";
    private static User currentUser = null; // Utilisateur connecté
    private static String currentView = ""; // Vue actuelle pour redirection

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Lotocrini - Location de Voitures");

        showAccueilView();

        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }

    public static void showAccueilView() {
        currentView = "Accueil";
        Scene scene = new Scene(new app.view.AccueilView().getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé, utilisation du style par défaut");
        }
        primaryStage.setScene(scene);
    }

    public static void showCatalogueView() {
        currentView = "Catalogue";
        Scene scene = new Scene(new app.view.CatalogueView().getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé");
        }
        primaryStage.setScene(scene);
    }

    // NOUVELLE MÉTHODE : Catalogue avec recherche
    public static void showCatalogueViewWithSearch(String query) {
        searchQuery = query;
        showCatalogueView();
    }

    public static void showLoginView() {
        currentView = "Login";
        Scene scene = new Scene(new app.view.LoginView().getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé");
        }
        primaryStage.setScene(scene);
    }

    public static void showRegisterView() {
        currentView = "Register";
        Scene scene = new Scene(new app.view.RegisterView().getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé");
        }
        primaryStage.setScene(scene);
    }

    public static void showContactView() {
        currentView = "Contact";
        Scene scene = new Scene(new app.view.ContactView().getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé");
        }
        primaryStage.setScene(scene);
    }

    public static void showVoitureDetailView(app.view.CatalogueView.Voiture voiture) {
        currentView = "VoitureDetail";
        Scene scene = new Scene(new app.view.VoitureDetailView(voiture).getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé");
        }
        primaryStage.setScene(scene);
    }

    public static void showReservationView() {
        // Vérifier si l'utilisateur est connecté
        if (currentUser == null) {
            // Rediriger vers login avec indication
            showLoginViewForReservation();
            return;
        }

        currentView = "Reservation";
        Scene scene = new Scene(new app.view.ReservationView().getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé");
        }
        primaryStage.setScene(scene);
    }

    public static void showAdminView() {
        currentView = "Admin";
        Scene scene = new Scene(new app.view.AdminView().getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé");
        }
        primaryStage.setScene(scene);
    }

    // NOUVELLE MÉTHODE : Profil utilisateur
    public static void showProfileView() {
        // Vérifier si l'utilisateur est connecté
        if (currentUser == null) {
            showLoginView();
            return;
        }

        currentView = "Profile";
        Scene scene = new Scene(new app.view.ProfileView().getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé");
        }
        primaryStage.setScene(scene);
    }

    // NOUVELLE MÉTHODE : Login spécifique pour réservation
    public static void showLoginViewForReservation() {
        currentView = "LoginForReservation";
        Scene scene = new Scene(new app.view.LoginView().getView(), 1200, 800);
        try {
            String cssPath = Main.class.getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.out.println("CSS non trouvé");
        }
        primaryStage.setScene(scene);
    }

    // Getters pour la requête de recherche
    public static String getSearchQuery() {
        return searchQuery;
    }

    // Reset la recherche
    public static void resetSearchQuery() {
        searchQuery = "";
    }

    // NOUVEAU : Gestion de l'utilisateur connecté
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
        showAccueilView();
    }

    public static String getCurrentView() {
        return currentView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}