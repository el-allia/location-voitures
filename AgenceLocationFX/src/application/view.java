package application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import java.util.function.Consumer;

public class view {
	public class AccueilView {

	    private BorderPane view;

	    public AccueilView() {
	        createView();
	    }

	    private void createView() {
	        view = new BorderPane();
	        view.getStyleClass().add("accueil-root");

	        // HEADER
	        HeaderView headerView = new HeaderView("Accueil");
	        view.setTop(headerView.getHeader());

	        // CONTENU
	        VBox mainContent = createMainContent();
	        view.setCenter(mainContent);

	        // FOOTER
	        VBox footer = createFooter();
	        view.setBottom(footer);
	    }

	    private VBox createMainContent() {

	        VBox content = new VBox(40);
	        content.setPadding(new Insets(60, 40, 60, 40));
	        content.setAlignment(Pos.CENTER);
	        content.setStyle("-fx-background-color: #f7fafc;");

	        Label title = new Label("Bienvenue chez Lotocrini");
	        title.setStyle(
	                "-fx-font-size: 48px; " +
	                        "-fx-font-weight: 900; " +
	                        "-fx-text-fill: #1a365d;");

	        Label subtitle = new Label("Votre location automobile");
	        subtitle.setStyle(
	                "-fx-font-size: 20px; " +
	                        "-fx-text-fill: #718096; " +
	                        "-fx-font-weight: 500;");

	        ImageView carIcon = loadImage("images/lotocrini_logo.png", 200, 200);
	        carIcon.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

	        Button btnCatalogue = new Button("Accéder au catalogue");
	        btnCatalogue.setStyle(
	                "-fx-font-size: 18px; " +
	                        "-fx-padding: 15 40; " +
	                        "-fx-background-color: #3182ce; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-weight: 700; " +
	                        "-fx-background-radius: 8;");
	        btnCatalogue.setOnAction(e -> app.Main.showCatalogueView());

	        ImageView btnIcon = loadImage("images/voiture_icon.png", 24, 24);
	        btnCatalogue.setGraphic(btnIcon);

	        content.getChildren().addAll(title, subtitle, carIcon, btnCatalogue);

	        return content;
	    }

	    private VBox createFooter() {
	        VBox footer = new VBox(10);
	        footer.setPadding(new Insets(20, 40, 20, 40));
	        footer.setAlignment(Pos.CENTER);
	        footer.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 1 0 0 0;");

	        Label copyright = new Label("© 2025 Lotocrini - Tous droits réservés");
	        copyright.setStyle("-fx-text-fill: #718096; -fx-font-size: 14px;");

	        footer.getChildren().add(copyright);
	        return footer;
	    }

	    private ImageView loadImage(String path, double height, double width) {
	        ImageView imageView = new ImageView();
	        try {
	            Image image = new Image(getClass().getResourceAsStream(path));
	            imageView.setImage(image);
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setPreserveRatio(true);
	        } catch (Exception e) {
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setStyle("-fx-background-color: #e2e8f0; -fx-background-radius: 4;");
	            System.out.println("Image non trouvée: " + path);
	        }
	        return imageView;
	    }

	    public BorderPane getView() {
	        return view;
	    }
	}
	public class AdminView {
	    private BorderPane view;
	    private TableView<Reservation> reservationTable;
	    private ScrollPane scrollPane;

	    public AdminView() {
	        createView();
	    }

	    private void createView() {
	        view = new BorderPane();
	        view.setStyle("-fx-background-color: #f7fafc;");

	        // HEADER
	        HeaderView headerView = new HeaderView("Admin");
	        view.setTop(headerView.getHeader());

	        // CONTENU PRINCIPAL AVEC SCROLL
	        scrollPane = new ScrollPane();
	        scrollPane.setFitToWidth(true);
	        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

	        VBox mainContent = createMainContent();
	        scrollPane.setContent(mainContent);
	        view.setCenter(scrollPane);
	    }

	    private VBox createMainContent() {
	        VBox content = new VBox(25);
	        content.setPadding(new Insets(30));
	        content.setAlignment(Pos.TOP_CENTER);
	        content.setStyle("-fx-background-color: #f7fafc;");

	        // Titre et barre d'outils
	        HBox titleBar = new HBox();
	        titleBar.setAlignment(Pos.CENTER_LEFT);
	        titleBar.setPadding(new Insets(0, 0, 20, 0));

	        Label title = new Label("👨‍💼 TABLEAU DE BORD ADMINISTRATEUR");
	        title.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");
	        HBox.setHgrow(title, Priority.ALWAYS);

	        Button btnAddCar = new Button("➕ Ajouter une voiture");
	        btnAddCar.setStyle(
	                "-fx-background-color: #3182ce; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-weight: 600; " +
	                        "-fx-padding: 10 20; " +
	                        "-fx-background-radius: 6; " +
	                        "-fx-cursor: hand;");

	        Button btnExport = new Button("📊 Exporter les données");
	        btnExport.setStyle(
	                "-fx-background-color: #38a169; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-weight: 600; " +
	                        "-fx-padding: 10 20; " +
	                        "-fx-background-radius: 6; " +
	                        "-fx-cursor: hand;");

	        titleBar.getChildren().addAll(title, btnAddCar, btnExport);

	        // Cartes de statistiques
	        HBox statsCards = new HBox(20);
	        statsCards.setAlignment(Pos.CENTER);
	        statsCards.setPadding(new Insets(0, 0, 20, 0));

	        statsCards.getChildren().addAll(
	                createStatCard("💰 Revenus totaux", "245,800 DA", "#3182ce", "images/money_icon.png"),
	                createStatCard("🚗 Voitures disponibles", "8/12", "#38a169", "images/voiture_icon.png"),
	                createStatCard("📅 Réservations actives", "5", "#d69e2e", "images/reservation_icon.png"),
	                createStatCard("👥 Clients inscrits", "42", "#805ad5", "images/users_icon.png"));

	        // Graphique et tableau
	        HBox chartsAndTable = new HBox(30);
	        chartsAndTable.setAlignment(Pos.TOP_CENTER);
	        chartsAndTable.setPadding(new Insets(0, 0, 30, 0));

	        VBox leftColumn = new VBox(20);
	        leftColumn.setPrefWidth(600);

	        // Graphique
	        VBox chartContainer = new VBox(15);
	        chartContainer.setPadding(new Insets(20));
	        chartContainer.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 10; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 10; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

	        Label chartTitle = new Label("📈 Réservations par mois");
	        chartTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

	        BarChart<String, Number> barChart = createBarChart();
	        chartContainer.getChildren().addAll(chartTitle, barChart);

	        // Tableau des réservations récentes
	        VBox tableContainer = new VBox(15);
	        tableContainer.setPadding(new Insets(20));
	        tableContainer.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 10; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 10; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

	        Label tableTitle = new Label("📋 Réservations récentes");
	        tableTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

	        reservationTable = createReservationTable();
	        tableContainer.getChildren().addAll(tableTitle, reservationTable);

	        leftColumn.getChildren().addAll(chartContainer, tableContainer);

	        // Colonne droite - Actions rapides
	        VBox rightColumn = new VBox(20);
	        rightColumn.setPrefWidth(350);

	        VBox quickActions = new VBox(15);
	        quickActions.setPadding(new Insets(20));
	        quickActions.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 10; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 10; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

	        Label actionsTitle = new Label("⚡ Actions rapides");
	        actionsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

	        VBox actionButtons = new VBox(10);
	        actionButtons.setPadding(new Insets(10, 0, 0, 0));

	        Button btnManageCars = createActionButton("Gérer le parc auto", "images/voiture_icon.png");
	        Button btnManageReservations = createActionButton("Gérer les réservations", "images/reservation_icon.png");
	        Button btnManageUsers = createActionButton("Gérer les utilisateurs", "images/users_icon.png");
	        Button btnViewReports = createActionButton("Voir les rapports", "images/report_icon.png");
	        Button btnSystemSettings = createActionButton("Paramètres système", "images/settings_icon.png");

	        // Ajout des actions aux boutons
	        btnManageCars.setOnAction(e -> System.out.println("Gestion du parc auto"));
	        btnManageReservations.setOnAction(e -> System.out.println("Gestion des réservations"));
	        btnManageUsers.setOnAction(e -> System.out.println("Gestion des utilisateurs"));
	        btnViewReports.setOnAction(e -> System.out.println("Voir les rapports"));
	        btnSystemSettings.setOnAction(e -> System.out.println("Paramètres système"));

	        actionButtons.getChildren().addAll(btnManageCars, btnManageReservations, btnManageUsers, btnViewReports,
	                btnSystemSettings);
	        quickActions.getChildren().addAll(actionsTitle, actionButtons);

	        // Section notifications
	        VBox notifications = new VBox(15);
	        notifications.setPadding(new Insets(20));
	        notifications.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 10; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 10; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

	        Label notifTitle = new Label("🔔 Notifications récentes");
	        notifTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

	        VBox notificationList = new VBox(10);
	        notificationList.setPadding(new Insets(10, 0, 0, 0));

	        addNotification(notificationList, "⚠️", "Toyota Highlander nécessite révision", "Il y a 2 jours");
	        addNotification(notificationList, "📅", "3 réservations à venir aujourd'hui", "Il y a 1 jour");
	        addNotification(notificationList, "💰", "Paiement en attente - Réservation #4567", "Il y a 3 jours");
	        addNotification(notificationList, "✅", "Nouvel utilisateur inscrit", "Il y a 5 jours");
	        addNotification(notificationList, "🚗", "Nouvelle voiture ajoutée au catalogue", "Il y a 1 semaine");

	        notifications.getChildren().addAll(notifTitle, notificationList);
	        rightColumn.getChildren().addAll(quickActions, notifications);

	        chartsAndTable.getChildren().addAll(leftColumn, rightColumn);
	        content.getChildren().addAll(titleBar, statsCards, chartsAndTable);

	        return content;
	    }

	    private VBox createStatCard(String title, String value, String color, String iconPath) {
	        VBox card = new VBox(10);
	        card.setPrefWidth(200);
	        card.setPadding(new Insets(20));
	        card.setAlignment(Pos.CENTER);
	        card.setStyle(
	                "-fx-background-color: " + color + "; " +
	                        "-fx-background-radius: 10; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4);");

	        HBox header = new HBox(10);
	        header.setAlignment(Pos.CENTER);

	        ImageView icon = loadImage(iconPath, 24, 24);
	        icon.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

	        Label titleLabel = new Label(title);
	        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 600; -fx-text-fill: rgba(255,255,255,0.9);");

	        header.getChildren().addAll(icon, titleLabel);

	        Label valueLabel = new Label(value);
	        valueLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: white;");

	        card.getChildren().addAll(header, valueLabel);
	        return card;
	    }

	    private BarChart<String, Number> createBarChart() {
	        CategoryAxis xAxis = new CategoryAxis();
	        NumberAxis yAxis = new NumberAxis();
	        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

	        barChart.setTitle("Réservations mensuelles - 2024");
	        barChart.setLegendVisible(false);
	        barChart.setPrefHeight(250);

	        // Style
	        barChart.setStyle("-fx-background-color: transparent;");
	        xAxis.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 12px;");
	        yAxis.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 12px;");

	        // Données adaptées pour l'Algérie (exemple)
	        XYChart.Series<String, Number> series = new XYChart.Series<>();
	        series.getData().add(new XYChart.Data<>("Janv", 12));
	        series.getData().add(new XYChart.Data<>("Fév", 18));
	        series.getData().add(new XYChart.Data<>("Mars", 22));
	        series.getData().add(new XYChart.Data<>("Avr", 25));
	        series.getData().add(new XYChart.Data<>("Mai", 28));
	        series.getData().add(new XYChart.Data<>("Juin", 30));
	        series.getData().add(new XYChart.Data<>("Juil", 35));
	        series.getData().add(new XYChart.Data<>("Août", 32));
	        series.getData().add(new XYChart.Data<>("Sept", 27));
	        series.getData().add(new XYChart.Data<>("Oct", 24));
	        series.getData().add(new XYChart.Data<>("Nov", 20));
	        series.getData().add(new XYChart.Data<>("Déc", 15));

	        barChart.getData().add(series);

	        // Colorer les barres
	        for (XYChart.Data<String, Number> data : series.getData()) {
	            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
	                if (newNode != null) {
	                    newNode.setStyle("-fx-bar-fill: #3182ce;");
	                }
	            });
	        }

	        return barChart;
	    }

	    private TableView<Reservation> createReservationTable() {
	        TableView<Reservation> table = new TableView<>();
	        table.setPrefHeight(300);
	        table.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

	        // Colonnes
	        TableColumn<Reservation, String> colId = new TableColumn<>("ID");
	        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
	        colId.setPrefWidth(80);

	        TableColumn<Reservation, String> colClient = new TableColumn<>("Client");
	        colClient.setCellValueFactory(cellData -> cellData.getValue().clientProperty());
	        colClient.setPrefWidth(150);

	        TableColumn<Reservation, String> colVoiture = new TableColumn<>("Voiture");
	        colVoiture.setCellValueFactory(cellData -> cellData.getValue().voitureProperty());
	        colVoiture.setPrefWidth(120);

	        TableColumn<Reservation, String> colDates = new TableColumn<>("Dates");
	        colDates.setCellValueFactory(cellData -> cellData.getValue().datesProperty());
	        colDates.setPrefWidth(120);

	        TableColumn<Reservation, String> colStatut = new TableColumn<>("Statut");
	        colStatut.setCellValueFactory(cellData -> cellData.getValue().statutProperty());
	        colStatut.setPrefWidth(100);

	        TableColumn<Reservation, String> colMontant = new TableColumn<>("Montant (DA)");
	        colMontant.setCellValueFactory(cellData -> cellData.getValue().montantProperty());
	        colMontant.setPrefWidth(120);

	        table.getColumns().addAll(colId, colClient, colVoiture, colDates, colStatut, colMontant);

	        // Données d'exemple adaptées pour l'Algérie
	        table.getItems().addAll(
	                new Reservation("#4567", "Ahmed B.", "Toyota RAV4", "15-22 Nov", "Confirmée", "31,500"),
	                new Reservation("#4566", "Sarah M.", "Peugeot 208", "10-17 Nov", "En cours", "26,600"),
	                new Reservation("#4565", "Karim L.", "BMW Série 5", "05-12 Nov", "Terminée", "91,000"),
	                new Reservation("#4564", "Leila K.", "Hyundai Tucson", "01-08 Nov", "Annulée", "29,400"),
	                new Reservation("#4563", "Omar S.", "Toyota Highlander", "28 Oct-4 Nov", "Terminée", "39,900"),
	                new Reservation("#4562", "Fatima Z.", "Renault Clio", "20-27 Oct", "Terminée", "22,500"),
	                new Reservation("#4561", "Yacine T.", "Mercedes Classe C", "15-20 Oct", "Confirmée", "65,000"),
	                new Reservation("#4560", "Nadia R.", "Dacia Logan", "10-15 Oct", "Terminée", "18,000"));

	        return table;
	    }

	    private Button createActionButton(String text, String iconPath) {
	        Button btn = new Button(text);
	        btn.setStyle(
	                "-fx-background-color: #f7fafc; " +
	                        "-fx-text-fill: #4a5568; " +
	                        "-fx-font-size: 14px; " +
	                        "-fx-font-weight: 600; " +
	                        "-fx-padding: 12 15; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-border-radius: 6; " +
	                        "-fx-background-radius: 6; " +
	                        "-fx-alignment: center-left; " +
	                        "-fx-cursor: hand;");

	        ImageView icon = loadImage(iconPath, 16, 16);
	        btn.setGraphic(icon);

	        btn.setOnMouseEntered(e -> {
	            btn.setStyle(
	                    "-fx-background-color: #edf2f7; " +
	                            "-fx-text-fill: #1a365d; " +
	                            "-fx-font-size: 14px; " +
	                            "-fx-font-weight: 600; " +
	                            "-fx-padding: 12 15; " +
	                            "-fx-border-color: #3182ce; " +
	                            "-fx-border-width: 1; " +
	                            "-fx-border-radius: 6; " +
	                            "-fx-background-radius: 6; " +
	                            "-fx-alignment: center-left;");
	        });

	        btn.setOnMouseExited(e -> {
	            btn.setStyle(
	                    "-fx-background-color: #f7fafc; " +
	                            "-fx-text-fill: #4a5568; " +
	                            "-fx-font-size: 14px; " +
	                            "-fx-font-weight: 600; " +
	                            "-fx-padding: 12 15; " +
	                            "-fx-border-color: #e2e8f0; " +
	                            "-fx-border-width: 1; " +
	                            "-fx-border-radius: 6; " +
	                            "-fx-background-radius: 6; " +
	                            "-fx-alignment: center-left;");
	        });

	        return btn;
	    }

	    private void addNotification(VBox container, String emoji, String text, String time) {
	        HBox notification = new HBox(10);
	        notification.setPadding(new Insets(10));
	        notification.setStyle(
	                "-fx-background-color: #f7fafc; " +
	                        "-fx-background-radius: 6; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 6; " +
	                        "-fx-border-width: 1;");

	        Label emojiLabel = new Label(emoji);
	        emojiLabel.setStyle("-fx-font-size: 16px;");

	        VBox textBox = new VBox(2);
	        Label textLabel = new Label(text);
	        textLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a5568; -fx-font-weight: 500;");
	        textLabel.setWrapText(true);

	        Label timeLabel = new Label(time);
	        timeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #a0aec0;");

	        textBox.getChildren().addAll(textLabel, timeLabel);
	        HBox.setHgrow(textBox, Priority.ALWAYS);

	        notification.getChildren().addAll(emojiLabel, textBox);
	        container.getChildren().add(notification);
	    }

	    private ImageView loadImage(String path, double height, double width) {
	        ImageView imageView = new ImageView();
	        try {
	            Image image = new Image(getClass().getResourceAsStream(path));
	            imageView.setImage(image);
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setPreserveRatio(true);
	        } catch (Exception e) {
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setStyle("-fx-background-color: #e2e8f0; -fx-background-radius: 4;");
	        }
	        return imageView;
	    }

	    // Classe interne pour les réservations (pour le tableau)
	    public static class Reservation {
	        private final String id;
	        private final String client;
	        private final String voiture;
	        private final String dates;
	        private final String statut;
	        private final String montant;

	        public Reservation(String id, String client, String voiture, String dates, String statut, String montant) {
	            this.id = id;
	            this.client = client;
	            this.voiture = voiture;
	            this.dates = dates;
	            this.statut = statut;
	            this.montant = montant;
	        }

	        public StringProperty idProperty() {
	            return new SimpleStringProperty(id);
	        }

	        public StringProperty clientProperty() {
	            return new SimpleStringProperty(client);
	        }

	        public StringProperty voitureProperty() {
	            return new SimpleStringProperty(voiture);
	        }

	        public StringProperty datesProperty() {
	            return new SimpleStringProperty(dates);
	        }

	        public StringProperty statutProperty() {
	            return new SimpleStringProperty(statut);
	        }

	        public StringProperty montantProperty() {
	            return new SimpleStringProperty(montant);
	        }
	    }

	    public BorderPane getView() {
	        return view;
	    }
	}
	public class CatalogueView {
	    private BorderPane view;
	    private Slider prixSlider;
	    private ComboBox<String> marqueCombo;
	    private CheckBox cbSUV, cbCitadine, cbLuxe, cbUtilitaire;
	    private CheckBox cbDispo;
	    private Button btnAppliquer;
	    private VBox categoriesContainer;

	    // Liste de toutes les catégories et voitures pour le filtrage
	    private List<Categorie> toutesCategories = new ArrayList<>();
	    private List<Voiture> toutesVoitures = new ArrayList<>();

	    // Header réutilisable
	    private HeaderView headerView;

	    // ========== COMPOSITION PATTERN ==========
	    public abstract class ComponentCatalogue {
	        protected String nom;
	        protected ImageView icone;

	        public ComponentCatalogue(String nom, String iconPath) {
	            this.nom = nom;
	            this.icone = loadImage(iconPath, 30, 30);
	        }

	        public abstract VBox afficher();
	    }

	    public class Categorie extends ComponentCatalogue {
	        private List<ComponentCatalogue> enfants = new ArrayList<>();

	        public Categorie(String nom, String iconPath) {
	            super(nom, iconPath);
	        }

	        public void ajouterEnfant(ComponentCatalogue enfant) {
	            enfants.add(enfant);
	        }

	        public List<ComponentCatalogue> getEnfants() {
	            return enfants;
	        }

	        @Override
	        public VBox afficher() {
	            VBox categorieBox = new VBox(15);
	            categorieBox.setPadding(new Insets(20));
	            categorieBox.setStyle(
	                    "-fx-background-color: white; " +
	                            "-fx-background-radius: 10; " +
	                            "-fx-border-color: #e2e8f0; " +
	                            "-fx-border-radius: 10; " +
	                            "-fx-border-width: 1; " +
	                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);");

	            HBox header = new HBox(10);
	            header.setAlignment(Pos.CENTER_LEFT);

	            Label nomLabel = new Label(nom);
	            nomLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

	            header.getChildren().addAll(icone, nomLabel);

	            FlowPane enfantsContainer = new FlowPane();
	            enfantsContainer.setHgap(20);
	            enfantsContainer.setVgap(20);
	            enfantsContainer.setPadding(new Insets(15, 0, 0, 0));

	            for (ComponentCatalogue enfant : enfants) {
	                if (enfant instanceof Voiture) {
	                    enfantsContainer.getChildren().add(enfant.afficher());
	                } else if (enfant instanceof Categorie) {
	                    // Pour les sous-catégories, on affiche aussi leurs voitures
	                    Categorie sousCat = (Categorie) enfant;
	                    for (ComponentCatalogue sousEnfant : sousCat.getEnfants()) {
	                        if (sousEnfant instanceof Voiture) {
	                            enfantsContainer.getChildren().add(sousEnfant.afficher());
	                        }
	                    }
	                }
	            }

	            categorieBox.getChildren().addAll(header, enfantsContainer);
	            return categorieBox;
	        }
	    }

	    public class Voiture extends ComponentCatalogue {
	        private String marque;
	        private String modele;
	        private double prix;
	        private boolean disponible;
	        private String type; // Nouveau champ pour le type de véhicule

	        public Voiture(String nom, String marque, String modele, double prix, boolean disponible, String iconPath) {
	            super(nom, iconPath);
	            this.marque = marque;
	            this.modele = modele;
	            this.prix = prix;
	            this.disponible = disponible;
	            this.type = determinerType(nom); // Détermine le type automatiquement
	        }

	        private String determinerType(String nom) {
	            // Logique simple pour déterminer le type basé sur le nom
	            if (nom.toLowerCase().contains("rav4") || nom.toLowerCase().contains("highlander") ||
	                    nom.toLowerCase().contains("tucson")) {
	                return "SUV";
	            } else if (nom.toLowerCase().contains("208")) {
	                return "Citadine";
	            } else if (nom.toLowerCase().contains("série 5")) {
	                return "Luxe";
	            }
	            return "Autre";
	        }

	        @Override
	        public VBox afficher() {
	            VBox carte = new VBox(10);
	            carte.setPrefWidth(250);
	            carte.setPadding(new Insets(15));
	            carte.setStyle(
	                    "-fx-background-color: white; " +
	                            "-fx-background-radius: 8; " +
	                            "-fx-border-color: #e2e8f0; " +
	                            "-fx-border-radius: 8; " +
	                            "-fx-border-width: 1; " +
	                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 3, 0, 0, 1); " +
	                            "-fx-cursor: hand;");

	            // Utiliser l'image spécifique de la voiture
	            ImageView photo = new ImageView(this.icone.getImage());
	            photo.setFitHeight(120);
	            photo.setFitWidth(220);
	            photo.setPreserveRatio(true);
	            photo.setStyle("-fx-background-color: #f7fafc; -fx-background-radius: 5;");

	            Label nomLabel = new Label(nom);
	            nomLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

	            Label detailsLabel = new Label(marque + " - " + modele);
	            detailsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096;");

	            // Format DA
	            Label prixLabel = new Label(String.format("%,.0f DA / jour", prix));
	            prixLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 800; -fx-text-fill: #2d3748;");

	            HBox dispoBox = new HBox(8);
	            dispoBox.setAlignment(Pos.CENTER_LEFT);
	            javafx.scene.shape.Circle dispoCircle = new javafx.scene.shape.Circle(6);
	            dispoCircle.setFill(disponible ? Color.GREEN : Color.RED);

	            Label dispoLabel = new Label(disponible ? "Disponible" : "Non disponible");
	            dispoLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 600;");
	            dispoLabel.setTextFill(disponible ? Color.GREEN : Color.RED);

	            dispoBox.getChildren().addAll(dispoCircle, dispoLabel);

	            Button btnDetails = new Button("Voir détails");
	            btnDetails.setStyle(
	                    "-fx-background-color: transparent; " +
	                            "-fx-text-fill: #3182ce; " +
	                            "-fx-font-weight: 600; " +
	                            "-fx-padding: 8 16; " +
	                            "-fx-border-color: #3182ce; " +
	                            "-fx-border-width: 2; " +
	                            "-fx-border-radius: 5; " +
	                            "-fx-cursor: hand;");
	            btnDetails.setOnAction(e -> app.Main.showVoitureDetailView(this));

	            carte.getChildren().addAll(photo, nomLabel, detailsLabel, prixLabel, dispoBox, btnDetails);
	            return carte;
	        }

	        public double getPrix() {
	            return prix;
	        }

	        public boolean isDisponible() {
	            return disponible;
	        }

	        public String getMarque() {
	            return marque;
	        }

	        public String getType() {
	            return type;
	        }

	        public String getModele() {
	            return modele;
	        }

	        public String getNom() {
	            return nom;
	        }
	    }

	    public CatalogueView() {
	        // Créer le HeaderView avec un callback pour la recherche
	        headerView = new HeaderView("Catalogue", searchText -> {
	            // Quand l'utilisateur recherche depuis le header, appliquer le filtre
	            afficherVoituresFiltrees();
	        });

	        createView();
	        initDonneesCatalogue();
	        setupFiltres();

	        // Vérifier s'il y a une recherche en attente depuis Main
	        String searchQuery = app.Main.getSearchQuery();
	        if (searchQuery != null && !searchQuery.isEmpty()) {
	            // Mettre à jour le texte de recherche
	            headerView.setSearchText(searchQuery);
	            // Appliquer la recherche
	            afficherVoituresFiltrees();
	            // Réinitialiser la requête dans Main
	            app.Main.resetSearchQuery();
	        }
	    }

	    private void createView() {
	        view = new BorderPane();
	        view.setStyle("-fx-background-color: #f7fafc;");

	        // HEADER
	        view.setTop(headerView.getHeader());

	        // CONTENU PRINCIPAL avec sidebar + catalogue
	        HBox mainContent = new HBox();

	        VBox sidebar = createSidebar();
	        sidebar.setPrefWidth(280);

	        VBox catalogueContent = createCatalogueContent();
	        HBox.setHgrow(catalogueContent, Priority.ALWAYS);

	        mainContent.getChildren().addAll(sidebar, catalogueContent);
	        view.setCenter(mainContent);
	    }

	    private VBox createSidebar() {
	        VBox sidebar = new VBox(20);
	        sidebar.setPadding(new Insets(30, 20, 30, 20));
	        sidebar.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-width: 0 1 0 0;");

	        HBox filterTitle = new HBox(10);
	        filterTitle.setAlignment(Pos.CENTER_LEFT);

	        ImageView filterIcon = loadImage("images/filtre_icon.png", 24, 24);
	        Label title = new Label("FILTRES");
	        title.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

	        filterTitle.getChildren().addAll(filterIcon, title);

	        VBox prixFilter = new VBox(10);
	        Label lblPrix = new Label("Prix maximum :");
	        lblPrix.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        // Converti de 20-300€ à 3000-45000 DA (approx 150 DA pour 1€)
	        prixSlider = new Slider(3000, 45000, 22500);
	        prixSlider.setShowTickLabels(true);
	        prixSlider.setShowTickMarks(true);
	        prixSlider.setMajorTickUnit(10000);
	        prixSlider.setMinorTickCount(4);

	        Label prixValue = new Label("22.500 DA");
	        prixValue.setStyle("-fx-font-weight: 600; -fx-text-fill: #1a365d;");

	        prixSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
	            prixValue.setText(String.format("%,.0f DA", newVal.doubleValue()));
	        });

	        prixFilter.getChildren().addAll(lblPrix, prixSlider, prixValue);

	        VBox marqueFilter = new VBox(10);
	        Label lblMarque = new Label("Marque :");
	        lblMarque.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        marqueCombo = new ComboBox<>();
	        marqueCombo.getItems().addAll("Toutes", "Toyota", "BMW", "Mercedes", "Audi", "Peugeot", "Renault", "Hyundai");
	        marqueCombo.setValue("Toutes");
	        marqueCombo.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0;");

	        marqueFilter.getChildren().addAll(lblMarque, marqueCombo);

	        VBox typeFilter = new VBox(10);
	        Label lblType = new Label("Type de véhicule :");
	        lblType.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        VBox typeOptions = new VBox(8);
	        cbSUV = new CheckBox("SUV");
	        cbCitadine = new CheckBox("Citadine");
	        cbLuxe = new CheckBox("Luxe");
	        cbUtilitaire = new CheckBox("Utilitaire");

	        for (CheckBox cb : new CheckBox[] { cbSUV, cbCitadine, cbLuxe, cbUtilitaire }) {
	            cb.setStyle("-fx-text-fill: #4a5568;");
	        }

	        typeOptions.getChildren().addAll(cbSUV, cbCitadine, cbLuxe, cbUtilitaire);
	        typeFilter.getChildren().addAll(lblType, typeOptions);

	        VBox dispoFilter = new VBox(10);
	        cbDispo = new CheckBox("Afficher uniquement les disponibles");
	        cbDispo.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");
	        dispoFilter.getChildren().add(cbDispo);

	        btnAppliquer = new Button("Appliquer les filtres");
	        btnAppliquer.setStyle(
	                "-fx-background-color: #3182ce; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-weight: 700; " +
	                        "-fx-padding: 12; " +
	                        "-fx-background-radius: 6; " +
	                        "-fx-cursor: hand;");
	        btnAppliquer.setOnAction(e -> afficherVoituresFiltrees());

	        sidebar.getChildren().addAll(filterTitle, prixFilter, marqueFilter, typeFilter, dispoFilter, btnAppliquer);
	        return sidebar;
	    }

	    private VBox createCatalogueContent() {
	        VBox content = new VBox(25);
	        content.setPadding(new Insets(30));

	        Label titre = new Label("NOTRE CATALOGUE DE VOITURES");
	        titre.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

	        Label description = new Label(
	                "Découvrez notre sélection de véhicules. Utilisez les filtres pour affiner votre recherche.");
	        description.setStyle("-fx-font-size: 16px; -fx-text-fill: #718096;");

	        categoriesContainer = new VBox(25);

	        ScrollPane scrollPane = new ScrollPane(categoriesContainer);
	        scrollPane.setFitToWidth(true);
	        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

	        content.getChildren().addAll(titre, description, scrollPane);
	        return content;
	    }

	    private void initDonneesCatalogue() {
	        toutesCategories.clear();
	        toutesVoitures.clear();

	        Categorie catSUV = new Categorie("SUV", "images/voiture_icon.png");

	        Categorie sousToyota = new Categorie("Toyota", "images/Toyota rav4.jpg");
	        Voiture rav4 = new Voiture("Toyota RAV4", "Toyota", "RAV4", 4500, true, "images/Toyota rav4.jpg");
	        Voiture highlander = new Voiture("Toyota Highlander", "Toyota", "Highlander", 5700, false,
	                "images/toyota highlander.jpg");

	        sousToyota.ajouterEnfant(rav4);
	        sousToyota.ajouterEnfant(highlander);

	        Categorie sousHyundai = new Categorie("Hyundai", "images/hyundai tucson.jpg");
	        Voiture tucson = new Voiture("Hyundai Tucson", "Hyundai", "Tucson", 4200, true, "images/hyundai tucson.jpg");
	        sousHyundai.ajouterEnfant(tucson);

	        catSUV.ajouterEnfant(sousToyota);
	        catSUV.ajouterEnfant(sousHyundai);

	        Categorie catCitadines = new Categorie("Citadines", "images/peugeot 208.jpg");
	        Categorie sousPeugeot = new Categorie("Peugeot", "images/peugeot 208.jpg");
	        Voiture peugeot208 = new Voiture("Peugeot 208", "Peugeot", "208", 3800, true, "images/peugeot 208.jpg");
	        sousPeugeot.ajouterEnfant(peugeot208);
	        catCitadines.ajouterEnfant(sousPeugeot);

	        Categorie catLuxe = new Categorie("Luxe", "images/BMW serie 5.jpg");
	        Categorie sousBMW = new Categorie("BMW", "images/BMW serie 5.jpg");
	        Voiture bmwSerie5 = new Voiture("BMW Série 5", "BMW", "Série 5", 13000, true, "images/BMW serie 5.jpg");
	        sousBMW.ajouterEnfant(bmwSerie5);
	        catLuxe.ajouterEnfant(sousBMW);

	        Categorie catUtilitaires = new Categorie("Utilitaires", "images/renault kangoo.jpg");
	        Categorie sousRenault = new Categorie("Renault", "images/renault kangoo.jpg");
	        Voiture kangoo = new Voiture("Renault Kangoo", "Renault", "Kangoo", 3500, true, "images/renault kangoo.jpg");
	        sousRenault.ajouterEnfant(kangoo);
	        catUtilitaires.ajouterEnfant(sousRenault);

	        // Ajouter toutes les voitures à la liste
	        toutesVoitures.add(rav4);
	        toutesVoitures.add(highlander);
	        toutesVoitures.add(tucson);
	        toutesVoitures.add(peugeot208);
	        toutesVoitures.add(bmwSerie5);
	        toutesVoitures.add(kangoo);

	        // Ajouter toutes les catégories à la liste
	        toutesCategories.add(catSUV);
	        toutesCategories.add(catCitadines);
	        toutesCategories.add(catLuxe);
	        toutesCategories.add(catUtilitaires);

	        // Afficher toutes les voitures initialement
	        afficherVoituresFiltrees();
	    }

	    private void setupFiltres() {
	        // Ajouter des listeners pour tous les filtres
	        prixSlider.valueProperty().addListener((obs, oldVal, newVal) -> afficherVoituresFiltrees());
	        marqueCombo.setOnAction(e -> afficherVoituresFiltrees());
	        cbDispo.selectedProperty().addListener((obs, oldVal, newVal) -> afficherVoituresFiltrees());
	        cbSUV.selectedProperty().addListener((obs, oldVal, newVal) -> afficherVoituresFiltrees());
	        cbCitadine.selectedProperty().addListener((obs, oldVal, newVal) -> afficherVoituresFiltrees());
	        cbLuxe.selectedProperty().addListener((obs, oldVal, newVal) -> afficherVoituresFiltrees());
	        cbUtilitaire.selectedProperty().addListener((obs, oldVal, newVal) -> afficherVoituresFiltrees());
	    }

	    private void afficherVoituresFiltrees() {
	        // Effacer le contenu actuel
	        categoriesContainer.getChildren().clear();

	        // Récupérer les valeurs des filtres
	        double prixMax = prixSlider.getValue();
	        String marqueSelectionnee = marqueCombo.getValue();
	        boolean filtreDispo = cbDispo.isSelected();

	        // Texte de recherche - Récupérer directement du HeaderView
	        String recherche = headerView.getSearchText().trim().toLowerCase();

	        // Récupérer les types sélectionnés
	        List<String> typesSelectionnes = new ArrayList<>();
	        if (cbSUV.isSelected())
	            typesSelectionnes.add("SUV");
	        if (cbCitadine.isSelected())
	            typesSelectionnes.add("Citadine");
	        if (cbLuxe.isSelected())
	            typesSelectionnes.add("Luxe");
	        if (cbUtilitaire.isSelected())
	            typesSelectionnes.add("Utilitaire");

	        // Filtrer les voitures
	        List<Voiture> voituresFiltrees = new ArrayList<>();

	        for (Voiture voiture : toutesVoitures) {
	            boolean passePrix = voiture.getPrix() <= prixMax;
	            boolean passeMarque = marqueSelectionnee.equals("Toutes") ||
	                    voiture.getMarque().equalsIgnoreCase(marqueSelectionnee);
	            boolean passeDispo = !filtreDispo || voiture.isDisponible();
	            boolean passeType = typesSelectionnes.isEmpty() ||
	                    typesSelectionnes.contains(voiture.getType());
	            boolean passeRecherche = recherche.isEmpty() ||
	                    (voiture.getNom() != null && voiture.getNom().toLowerCase().contains(recherche)) ||
	                    (voiture.getMarque() != null && voiture.getMarque().toLowerCase().contains(recherche)) ||
	                    (voiture.getModele() != null && voiture.getModele().toLowerCase().contains(recherche));

	            if (passePrix && passeMarque && passeDispo && passeType && passeRecherche) {
	                voituresFiltrees.add(voiture);
	            }
	        }

	        // Grouper les voitures filtrées par catégorie
	        afficherParCategories(voituresFiltrees);
	    }

	    private void afficherParCategories(List<Voiture> voitures) {
	        // Créer des catégories basées sur les voitures filtrées
	        List<Categorie> categoriesFiltrees = new ArrayList<>();

	        // Regrouper par type principal
	        for (Voiture voiture : voitures) {
	            String type = voiture.getType();

	            // Trouver ou créer la catégorie principale
	            Categorie categoriePrincipale = null;
	            for (Categorie cat : categoriesFiltrees) {
	                if (cat.nom.equals(type)) {
	                    categoriePrincipale = cat;
	                    break;
	                }
	            }

	            if (categoriePrincipale == null) {
	                categoriePrincipale = new Categorie(type, "images/voiture_icon.png");
	                categoriesFiltrees.add(categoriePrincipale);

	                // Créer une sous-catégorie pour la marque
	                Categorie sousCategorieMarque = new Categorie(voiture.getMarque(), "images/voiture_icon.png");
	                sousCategorieMarque.ajouterEnfant(voiture);
	                categoriePrincipale.ajouterEnfant(sousCategorieMarque);
	            } else {
	                // Trouver ou créer la sous-catégorie de marque
	                boolean marqueTrouvee = false;
	                for (ComponentCatalogue enfant : categoriePrincipale.getEnfants()) {
	                    if (enfant instanceof Categorie && enfant.nom.equals(voiture.getMarque())) {
	                        ((Categorie) enfant).ajouterEnfant(voiture);
	                        marqueTrouvee = true;
	                        break;
	                    }
	                }

	                if (!marqueTrouvee) {
	                    Categorie sousCategorieMarque = new Categorie(voiture.getMarque(), "images/voiture_icon.png");
	                    sousCategorieMarque.ajouterEnfant(voiture);
	                    categoriePrincipale.ajouterEnfant(sousCategorieMarque);
	                }
	            }
	        }

	        // Afficher les catégories filtrées
	        for (Categorie cat : categoriesFiltrees) {
	            categoriesContainer.getChildren().add(cat.afficher());
	        }

	        // Si aucune voiture ne correspond aux filtres
	        if (voitures.isEmpty()) {
	            Label aucunResultat = new Label("Aucune voiture ne correspond à vos critères de recherche.");
	            aucunResultat.setStyle("-fx-font-size: 18px; -fx-text-fill: #718096; -fx-padding: 40px;");
	            categoriesContainer.getChildren().add(aucunResultat);
	        }
	    }

	    private ImageView loadImage(String path, double height, double width) {
	        ImageView imageView = new ImageView();
	        try {
	            // Si le chemin ne commence pas par "images/", l'ajouter
	            if (!path.startsWith("images/") && !path.startsWith("/images/")) {
	                path = "images/" + path;
	            }

	            // Si le chemin commence par "/images/", enlever le "/" initial
	            if (path.startsWith("/images/")) {
	                path = path.substring(1);
	            }

	            // Charger l'image depuis le classloader
	            java.io.InputStream stream = getClass().getResourceAsStream(path);

	            if (stream == null) {
	                // Essayer avec un chemin absolu
	                stream = getClass().getResourceAsStream("/" + path);
	            }

	            if (stream != null) {
	                Image image = new Image(stream);
	                imageView.setImage(image);
	                imageView.setFitHeight(height);
	                imageView.setFitWidth(width);
	                imageView.setPreserveRatio(true);
	            } else {
	                throw new Exception("Image non trouvée: " + path);
	            }
	        } catch (Exception e) {
	            System.err.println("Erreur chargement image: " + path + " - " + e.getMessage());
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setStyle("-fx-background-color: #e2e8f0; -fx-background-radius: 4;");
	        }
	        return imageView;
	    }

	    public BorderPane getView() {
	        return view;
	    }
	}
	public class HeaderView {
	    private VBox header;
	    private TextField searchField;
	    private String activePage;
	    private Consumer<String> onSearchAction; // Callback pour la recherche
	    private HBox rightButtons; // Pour pouvoir le mettre à jour

	    public HeaderView(String activePage) {
	        this.activePage = activePage;
	        createHeader();
	    }

	    public HeaderView(String activePage, Consumer<String> onSearchAction) {
	        this.activePage = activePage;
	        this.onSearchAction = onSearchAction;
	        createHeader();
	    }

	    private void createHeader() {
	        header = new VBox();
	        header.getStyleClass().add("header");
	        header.setPadding(new Insets(0));

	        // Barre supérieure
	        HBox topBar = new HBox(20);
	        topBar.setPadding(new Insets(15, 40, 15, 40));
	        topBar.setAlignment(Pos.CENTER_LEFT);
	        topBar.setStyle("-fx-background-color: #1a365d;");

	        // LOGO
	        HBox logoBox = new HBox();
	        logoBox.setAlignment(Pos.CENTER_LEFT);

	        ImageView logoView = loadImage("images/lotocrini_logo_header.png", 50, 50);
	        logoView.setScaleX(1.6);
	        logoView.setScaleY(1.6);
	        logoBox.getChildren().add(logoView);

	        // BARRE DE RECHERCHE
	        HBox searchBox = new HBox(10);
	        searchBox.getStyleClass().add("search-bar");
	        searchBox.setPadding(new Insets(5, 10, 5, 10));
	        searchBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");
	        searchBox.setAlignment(Pos.CENTER_LEFT);

	        searchField = new TextField();
	        searchField.setPromptText("Rechercher une voiture...");
	        searchField.setStyle(
	                "-fx-background-color: transparent; " +
	                        "-fx-font-size: 14px; " +
	                        "-fx-prompt-text-fill: #a0aec0;");
	        HBox.setHgrow(searchField, Priority.ALWAYS);

	        // Icône recherche
	        ImageView searchIcon = loadImage("images/recherche_icon.png", 20, 20);
	        HBox.setMargin(searchIcon, new Insets(0, 0, 0, 5));

	        // Action de recherche
	        searchIcon.setOnMouseClicked(e -> performSearch());
	        searchField.setOnAction(e -> performSearch());

	        searchBox.getChildren().addAll(searchField, searchIcon);

	        // Boutons droite (dynamiques selon connexion)
	        rightButtons = new HBox(15);
	        rightButtons.setAlignment(Pos.CENTER_RIGHT);
	        
	        updateRightButtons(); // Initialiser les boutons

	        HBox.setHgrow(searchBox, Priority.ALWAYS);

	        // Ajouter au topBar
	        topBar.getChildren().addAll(logoBox, searchBox, rightButtons);

	        // NAVBAR
	        HBox navBar = new HBox(0);
	        navBar.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 0 0 1 0;");

	        String[] menuItems = { "Accueil", "Catalogue", "Réservation", "Mon compte", "Contact", "Admin" };
	        String[] menuIcons = {
	                "images/Accueil_icon.png",
	                "images/voiture_icon.png",
	                "images/reservation_icon.png",
	                "images/contact_icon.png",
	                "images/parametres_icon.png",
	                "images/administrator_icon.png"
	        };

	        for (int i = 0; i < menuItems.length; i++) {
	            Button menuBtn = createMenuButton(menuItems[i], menuIcons[i]);

	            if (menuItems[i].equals(activePage)) {
	                menuBtn.setStyle(menuBtn.getStyle()
	                        + " -fx-background-color: #f7fafc; -fx-text-fill: #1a365d; -fx-border-color: transparent;");
	            }

	            navBar.getChildren().add(menuBtn);
	        }

	        header.getChildren().addAll(topBar, navBar);
	    }

	    // Méthode pour mettre à jour les boutons de droite selon l'état de connexion
	    private void updateRightButtons() {
	        rightButtons.getChildren().clear();
	        
	        User currentUser = Main.getCurrentUser();
	        
	        if (currentUser == null) {
	            // Non connecté : bouton login
	            Button btnLogin = createIconButton("Se connecter", "images/login_icon.png");
	            btnLogin.setOnAction(e -> Main.showLoginView());
	            rightButtons.getChildren().add(btnLogin);
	        } else {
	            // Connecté : profil + déconnexion
	            HBox userInfo = new HBox(10);
	            userInfo.setAlignment(Pos.CENTER);
	            
	            ImageView userIcon = loadImage("images/profil_icon.png", 24, 24);
	            
	            VBox userText = new VBox(2);
	            Label userName = new Label(currentUser.getNom());
	            userName.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: 600;");
	            
	            Label userStatus = new Label("Connecté");
	            userStatus.setStyle("-fx-text-fill: #a0aec0; -fx-font-size: 10px;");
	            
	            userText.getChildren().addAll(userName, userStatus);
	            
	            userInfo.getChildren().addAll(userIcon, userText);
	            userInfo.setOnMouseClicked(e -> Main.showProfileView());
	            userInfo.setStyle("-fx-cursor: hand; -fx-padding: 5; -fx-background-radius: 4;");
	            
	            userInfo.setOnMouseEntered(e -> {
	                userInfo.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-cursor: hand; -fx-padding: 5; -fx-background-radius: 4;");
	            });
	            
	            userInfo.setOnMouseExited(e -> {
	                userInfo.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-padding: 5; -fx-background-radius: 4;");
	            });
	            
	            Button btnDeconnexion = new Button("Déconnexion");
	            btnDeconnexion.setStyle(
	                "-fx-background-color: transparent; " +
	                "-fx-text-fill: #feb2b2; " +
	                "-fx-font-size: 12px; " +
	                "-fx-font-weight: 600; " +
	                "-fx-padding: 5 10;" +
	                "-fx-cursor: hand;");
	            btnDeconnexion.setOnAction(e -> Main.logout());
	            
	            rightButtons.getChildren().addAll(userInfo, btnDeconnexion);
	        }
	    }

	    private void performSearch() {
	        String searchText = searchField.getText().trim();

	        if (onSearchAction != null) {
	            // Si un callback est défini (pour CatalogueView), l'utiliser
	            onSearchAction.accept(searchText);
	        } else {
	            // Sinon, rediriger vers le catalogue
	            Main.showCatalogueViewWithSearch(searchText);
	        }
	    }

	    private Button createMenuButton(String text, String iconPath) {
	        Button btn = new Button(text);
	        btn.setStyle(
	                "-fx-background-color: transparent; " +
	                        "-fx-text-fill: #4a5568; " +
	                        "-fx-font-size: 14px; " +
	                        "-fx-font-weight: 600; " +
	                        "-fx-padding: 15 25; " +
	                        "-fx-border-width: 0 0 3 0; " +
	                        "-fx-border-color: transparent; " +
	                        "-fx-cursor: hand;");

	        ImageView icon = loadImage(iconPath, 16, 16);
	        btn.setGraphic(icon);

	        btn.setOnMouseEntered(e -> {
	            if (!text.equals(activePage)) {
	                btn.setStyle(
	                        "-fx-background-color: #f7fafc; " +
	                                "-fx-text-fill: #1a365d; " +
	                                "-fx-font-size: 14px; " +
	                                "-fx-font-weight: 600; " +
	                                "-fx-padding: 15 25; " +
	                                "-fx-border-width: 0 0 3 0; " +
	                                "-fx-border-color: transparent;");
	            }
	        });

	        btn.setOnMouseExited(e -> {
	            if (!text.equals(activePage)) {
	                btn.setStyle(
	                        "-fx-background-color: transparent; " +
	                                "-fx-text-fill: #4a5568; " +
	                                "-fx-font-size: 14px; " +
	                                "-fx-font-weight: 600; " +
	                                "-fx-padding: 15 25; " +
	                                "-fx-border-width: 0 0 3 0; " +
	                                "-fx-border-color: transparent;");
	            }
	        });

	        btn.setOnAction(e -> {
	            switch (text) {
	                case "Accueil":
	                    Main.showAccueilView();
	                    break;
	                case "Catalogue":
	                    Main.showCatalogueView();
	                    break;
	                case "Réservation":
	                    Main.showReservationView();
	                    break;
	                case "Contact":
	                    Main.showContactView();
	                    break;
	                case "Mon compte":
	                    // Rediriger vers profil si connecté, sinon login
	                    if (Main.getCurrentUser() != null) {
	                        Main.showProfileView();
	                    } else {
	                        Main.showLoginView();
	                    }
	                    break;
	                case "Admin":
	                    Main.showAdminView();
	                    break;
	            }
	        });

	        return btn;
	    }

	    private Button createIconButton(String text, String iconPath) {
	        Button btn = new Button(text);
	        btn.setStyle(
	                "-fx-background-color: transparent; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-size: 14px; " +
	                        "-fx-font-weight: 600; " +
	                        "-fx-padding: 8 15;" +
	                        "-fx-cursor: hand;");

	        ImageView icon = loadImage(iconPath, 20, 20);
	        btn.setGraphic(icon);

	        btn.setOnMouseEntered(e -> {
	            btn.setStyle(
	                    "-fx-background-color: rgba(255,255,255,0.1); " +
	                            "-fx-text-fill: white; " +
	                            "-fx-font-size: 14px; " +
	                            "-fx-font-weight: 600; " +
	                            "-fx-padding: 8 15; " +
	                            "-fx-background-radius: 4;");
	        });

	        btn.setOnMouseExited(e -> {
	            btn.setStyle(
	                    "-fx-background-color: transparent; " +
	                            "-fx-text-fill: white; " +
	                            "-fx-font-size: 14px; " +
	                            "-fx-font-weight: 600; " +
	                            "-fx-padding: 8 15;");
	        });

	        return btn;
	    }

	    private ImageView loadImage(String path, double height, double width) {
	        ImageView imageView = new ImageView();
	        try {
	            Image image = new Image(getClass().getResourceAsStream(path));
	            imageView.setImage(image);
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setPreserveRatio(true);
	        } catch (Exception e) {
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setStyle("-fx-background-color: #e2e8f0; -fx-background-radius: 4;");
	        }
	        return imageView;
	    }

	    public VBox getHeader() {
	        return header;
	    }

	    public String getSearchText() {
	        return searchField.getText();
	    }

	    public void setSearchText(String text) {
	        searchField.setText(text);
	    }

	    // Setter pour le callback de recherche
	    public void setOnSearchAction(Consumer<String> onSearchAction) {
	        this.onSearchAction = onSearchAction;
	    }
	    
	    // Méthode pour rafraîchir le header (après connexion/déconnexion)
	    public void refresh() {
	        updateRightButtons();
	    }
	}
	public class LoginView {
	    private BorderPane view;

	    public LoginView() {
	        createView();
	    }

	    private void createView() {
	        view = new BorderPane();
	        view.setStyle("-fx-background-color: #f7fafc;");

	        // HEADER
	        HeaderView headerView = new HeaderView("Mon compte");
	        view.setTop(headerView.getHeader());

	        // FORMULAIRE DE CONNEXION
	        VBox loginForm = createLoginForm();
	        view.setCenter(loginForm);
	    }

	    private VBox createLoginForm() {
	        VBox form = new VBox(30);
	        form.setMaxWidth(500);
	        form.setPadding(new Insets(50));
	        form.setAlignment(Pos.CENTER);
	        form.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 15; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 15; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

	        ImageView logo = loadImage("images/lotocrini_logo.png", 150, 240);
	        ImageView userIcon = loadImage("images/profil_icon.png", 10, 10);

	        Label title = new Label("CONNEXION");
	        title.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");
	        
	        // Message spécial pour réservation si besoin
	        String currentView = Main.getCurrentView();
	        if (currentView.equals("LoginForReservation")) {
	            Label reservationNote = new Label("Vous devez être connecté pour réserver une voiture.");
	            reservationNote.setStyle("-fx-font-size: 14px; -fx-text-fill: #e53e3e; -fx-font-weight: 500; -fx-padding: 0 0 10 0;");
	            form.getChildren().add(reservationNote);
	        }

	        VBox fields = new VBox(20);
	        TextField txtEmail = new TextField();
	        txtEmail.setPromptText("Email");
	        txtEmail.setStyle(getFieldStyle());

	        PasswordField txtPassword = new PasswordField();
	        txtPassword.setPromptText("Mot de passe");
	        txtPassword.setStyle(getFieldStyle());

	        Button btnLogin = new Button("SE CONNECTER");
	        btnLogin.setStyle(
	                "-fx-background-color: #3182ce; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-size: 16px; " +
	                        "-fx-font-weight: 800; " +
	                        "-fx-padding: 15; " +
	                        "-fx-background-radius: 8; " +
	                        "-fx-cursor: hand; " +
	                        "-fx-max-width: infinity;");
	        
	        btnLogin.setOnAction(e -> {
	            if (validateLogin(txtEmail, txtPassword)) {
	                // Simuler une connexion (en vrai, vérifier avec base de données)
	                // Pour l'exemple, on accepte n'importe quelle combinaison non vide
	                User user = new User(
	                    "U001",
	                    "Utilisateur Test",
	                    txtEmail.getText().trim(),
	                    txtPassword.getText().trim(),
	                    "123456789",
	                    "0551-23-45-67",
	                    "Alger Centre"
	                );
	                
	                Main.setCurrentUser(user);
	                
	                // Rediriger selon la vue précédente
	                if (currentView.equals("LoginForReservation")) {
	                    Main.showReservationView();
	                } else {
	                    Main.showCatalogueView();
	                }
	            }
	        });

	        Hyperlink linkRegister = new Hyperlink("Créer un compte");
	        linkRegister.setStyle("-fx-text-fill: #3182ce; -fx-font-weight: 600; -fx-font-size: 14px;");
	        linkRegister.setOnAction(e -> Main.showRegisterView());

	        fields.getChildren().addAll(txtEmail, txtPassword, btnLogin, linkRegister);
	        
	        if (currentView.equals("LoginForReservation")) {
	            Button btnSkip = new Button("Continuer sans compte (remplir manuellement)");
	            btnSkip.setStyle(
	                "-fx-background-color: transparent; " +
	                "-fx-text-fill: #718096; " +
	                "-fx-font-weight: 600; " +
	                "-fx-padding: 10; " +
	                "-fx-border-color: #e2e8f0; " +
	                "-fx-border-width: 1; " +
	                "-fx-border-radius: 6; " +
	                "-fx-cursor: hand;");
	            btnSkip.setOnAction(e -> Main.showReservationView());
	            fields.getChildren().add(btnSkip);
	        }
	        
	        form.getChildren().addAll(logo, userIcon, title, fields);

	        return form;
	    }
	    
	    private boolean validateLogin(TextField email, PasswordField password) {
	        if (email.getText().trim().isEmpty()) {
	            showAlert("Email manquant", "Veuillez saisir votre email.");
	            email.requestFocus();
	            return false;
	        }
	        
	        if (password.getText().trim().isEmpty()) {
	            showAlert("Mot de passe manquant", "Veuillez saisir votre mot de passe.");
	            password.requestFocus();
	            return false;
	        }
	        
	        // En réalité, vérifier dans la base de données
	        // Pour l'exemple, on accepte tout
	        return true;
	    }
	    
	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

	    private String getFieldStyle() {
	        return "-fx-background-color: #f7fafc; " +
	                "-fx-background-radius: 8; " +
	                "-fx-border-color: #e2e8f0; " +
	                "-fx-border-radius: 8; " +
	                "-fx-border-width: 1; " +
	                "-fx-padding: 15; " +
	                "-fx-font-size: 15px;";
	    }

	    private ImageView loadImage(String path, double height, double width) {
	        ImageView imageView = new ImageView();
	        try {
	            javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResourceAsStream(path));
	            imageView.setImage(image);
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setPreserveRatio(true);
	        } catch (Exception e) {
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setStyle("-fx-background-color: #e2e8f0; -fx-background-radius: 4;");
	        }
	        return imageView;
	    }

	    public BorderPane getView() {
	        return view;
	    }
	}
	public class ProfileView {
	    private BorderPane view;
	    private User currentUser;

	    public ProfileView() {
	        this.currentUser = Main.getCurrentUser();
	        createView();
	    }

	    private void createView() {
	        view = new BorderPane();
	        view.setStyle("-fx-background-color: #f7fafc;");

	        // HEADER avec "Mon Compte" comme page active
	        HeaderView headerView = new HeaderView("Mon compte");
	        view.setTop(headerView.getHeader());

	        // CONTENU PRINCIPAL AVEC SCROLLPANE
	        ScrollPane scrollPane = new ScrollPane();
	        scrollPane.setFitToWidth(true);
	        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

	        VBox mainContent = createMainContent();
	        scrollPane.setContent(mainContent);

	        view.setCenter(scrollPane);
	    }

	    private VBox createMainContent() {
	        VBox content = new VBox(30);
	        content.setPadding(new Insets(40));
	        content.setAlignment(Pos.TOP_CENTER);
	        content.setStyle("-fx-background-color: #f7fafc;");

	        Label title = new Label("👤 MON PROFIL");
	        title.setStyle("-fx-font-size: 32px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

	        if (currentUser == null) {
	            // Message si pas connecté (normalement pas possible d'accéder ici)
	            Label errorLabel = new Label("Veuillez vous connecter pour accéder à votre profil.");
	            errorLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #e53e3e; -fx-padding: 20;");

	            Button loginBtn = new Button("Se connecter");
	            loginBtn.setStyle(
	                    "-fx-background-color: #3182ce; " +
	                            "-fx-text-fill: white; " +
	                            "-fx-font-weight: 600; " +
	                            "-fx-padding: 12 30; " +
	                            "-fx-background-radius: 6; " +
	                            "-fx-cursor: hand;");
	            loginBtn.setOnAction(e -> Main.showLoginView());

	            content.getChildren().addAll(errorLabel, loginBtn);
	            return content;
	        }

	        // Carte profil
	        VBox profileCard = new VBox(25);
	        profileCard.setPadding(new Insets(30));
	        profileCard.setMaxWidth(600);
	        profileCard.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 15; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 15; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

	        // Avatar et nom
	        HBox headerBox = new HBox(20);
	        headerBox.setAlignment(Pos.CENTER_LEFT);

	        ImageView avatar = loadImage("images/profil_icon.png", 80, 80);

	        VBox nameBox = new VBox(5);
	        Label nameLabel = new Label(currentUser.getNom());
	        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

	        Label emailLabel = new Label(currentUser.getEmail());
	        emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096;");

	        nameBox.getChildren().addAll(nameLabel, emailLabel);
	        headerBox.getChildren().addAll(avatar, nameBox);

	        // Informations personnelles
	        VBox infoSection = new VBox(15);
	        Label infoTitle = new Label("INFORMATIONS PERSONNELLES");
	        infoTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

	        GridPane infoGrid = new GridPane();
	        infoGrid.setHgap(30);
	        infoGrid.setVgap(15);
	        infoGrid.setPadding(new Insets(10, 0, 0, 0));

	        // Numéro de permis
	        Label permisLabel = new Label("Numéro de permis:");
	        permisLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        Label permisValue = new Label(currentUser.getPermis());
	        permisValue.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d3748;");

	        infoGrid.add(permisLabel, 0, 0);
	        infoGrid.add(permisValue, 1, 0);

	        // Téléphone
	        Label phoneLabel = new Label("Téléphone:");
	        phoneLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        Label phoneValue = new Label(currentUser.getTelephone());
	        phoneValue.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d3748;");

	        infoGrid.add(phoneLabel, 0, 1);
	        infoGrid.add(phoneValue, 1, 1);

	        // Adresse
	        Label adresseLabel = new Label("Adresse:");
	        adresseLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        Label adresseValue = new Label(currentUser.getAdresse());
	        adresseValue.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d3748;");

	        infoGrid.add(adresseLabel, 0, 2);
	        infoGrid.add(adresseValue, 1, 2);

	        infoSection.getChildren().addAll(infoTitle, infoGrid);

	        // Boutons d'actions
	        HBox buttonBox = new HBox(15);
	        buttonBox.setAlignment(Pos.CENTER);
	        buttonBox.setPadding(new Insets(20, 0, 0, 0));

	        Button btnModifier = new Button("Modifier le profil");
	        btnModifier.setStyle(
	                "-fx-background-color: #3182ce; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-weight: 600; " +
	                        "-fx-padding: 12 25; " +
	                        "-fx-background-radius: 6; " +
	                        "-fx-cursor: hand;");
	        btnModifier.setOnAction(e -> {
	            // Ici on pourrait ouvrir un formulaire de modification
	            System.out.println("Modification du profil");
	        });

	        Button btnDeconnexion = new Button("Déconnexion");
	        btnDeconnexion.setStyle(
	                "-fx-background-color: #e53e3e; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-weight: 600; " +
	                        "-fx-padding: 12 25; " +
	                        "-fx-background-radius: 6; " +
	                        "-fx-cursor: hand;");
	        btnDeconnexion.setOnAction(e -> {
	            Main.logout();
	        });

	        buttonBox.getChildren().addAll(btnModifier, btnDeconnexion);

	        // Section réservations récentes
	        VBox reservationsSection = new VBox(15);
	        reservationsSection.setPadding(new Insets(20, 0, 0, 0));

	        Label resTitle = new Label("📅 RÉSERVATIONS RÉCENTES");
	        resTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #2d3748;");

	        VBox reservationsList = new VBox(10);

	        // Exemple de réservation
	        VBox reservationItem = createReservationItem("Toyota RAV4", "15-22 Nov 2024", "31,500 DA", "Confirmée");
	        VBox reservationItem2 = createReservationItem("Peugeot 208", "01-08 Nov 2024", "26,600 DA", "Terminée");

	        Button btnVoirToutes = new Button("Voir toutes mes réservations");
	        btnVoirToutes.setStyle(
	                "-fx-background-color: transparent; " +
	                        "-fx-text-fill: #3182ce; " +
	                        "-fx-font-weight: 600; " +
	                        "-fx-padding: 10; " +
	                        "-fx-border-color: #3182ce; " +
	                        "-fx-border-width: 2; " +
	                        "-fx-border-radius: 5; " +
	                        "-fx-cursor: hand;");

	        reservationsList.getChildren().addAll(reservationItem, reservationItem2, btnVoirToutes);
	        reservationsSection.getChildren().addAll(resTitle, reservationsList);

	        // Ajouter un espace en bas pour le scroll
	        Pane bottomSpacer = new Pane();
	        bottomSpacer.setPrefHeight(50);

	        profileCard.getChildren().addAll(
	                headerBox,
	                infoSection,
	                buttonBox,
	                reservationsSection,
	                bottomSpacer);

	        content.getChildren().addAll(title, profileCard);

	        return content;
	    }

	    private VBox createReservationItem(String voiture, String dates, String prix, String statut) {
	        VBox item = new VBox(10);
	        item.setPadding(new Insets(15));
	        item.setStyle(
	                "-fx-background-color: #f7fafc; " +
	                        "-fx-background-radius: 8; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 8; " +
	                        "-fx-border-width: 1;");

	        HBox header = new HBox();
	        header.setAlignment(Pos.CENTER_LEFT);

	        Label voitureLabel = new Label(voiture);
	        voitureLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600; -fx-text-fill: #2d3748;");

	        HBox.setHgrow(voitureLabel, Priority.ALWAYS);

	        Label statutLabel = new Label(statut);
	        if (statut.equals("Confirmée")) {
	            statutLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: 600; -fx-text-fill: #38a169; " +
	                    "-fx-background-color: #c6f6d5; -fx-padding: 4 8; -fx-background-radius: 4;");
	        } else {
	            statutLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: 600; -fx-text-fill: #718096; " +
	                    "-fx-background-color: #e2e8f0; -fx-padding: 4 8; -fx-background-radius: 4;");
	        }

	        header.getChildren().addAll(voitureLabel, statutLabel);

	        HBox details = new HBox(20);
	        details.setAlignment(Pos.CENTER_LEFT);

	        Label datesLabel = new Label("📅 " + dates);
	        datesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a5568;");

	        Label prixLabel = new Label("💰 " + prix);
	        prixLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a5568;");

	        details.getChildren().addAll(datesLabel, prixLabel);

	        item.getChildren().addAll(header, details);
	        return item;
	    }

	    private ImageView loadImage(String path, double height, double width) {
	        ImageView imageView = new ImageView();
	        try {
	            javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResourceAsStream(path));
	            imageView.setImage(image);
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setPreserveRatio(true);
	        } catch (Exception e) {
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setStyle("-fx-background-color: #e2e8f0; -fx-background-radius: 4;");
	        }
	        return imageView;
	    }

	    public BorderPane getView() {
	        return view;
	    }
	}
	public class RegisterView {
	    private BorderPane view;

	    public RegisterView() {
	        createView();
	    }

	    private void createView() {
	        view = new BorderPane();
	        view.setStyle("-fx-background-color: #f7fafc;");

	        // HEADER
	        HeaderView headerView = new HeaderView("Mon compte");
	        view.setTop(headerView.getHeader());

	        // FORMULAIRE D'INSCRIPTION
	        VBox registerForm = createRegisterForm();
	        view.setCenter(registerForm);
	    }

	    private VBox createRegisterForm() {
	        VBox form = new VBox(25);
	        form.setMaxWidth(600);
	        form.setPadding(new Insets(40));
	        form.setAlignment(Pos.CENTER);
	        form.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 15; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 15; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

	        ImageView formIcon = loadImage("images/profil_icon.png", 60, 60);

	        Label title = new Label("INSCRIPTION");
	        title.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");
	        
	        Label subtitle = new Label("Tous les champs sont obligatoires");
	        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #e53e3e; -fx-font-weight: 500;");

	        GridPane grid = new GridPane();
	        grid.setHgap(20);
	        grid.setVgap(20);
	        grid.setPadding(new Insets(20, 0, 30, 0));

	        // Champs avec validation
	        TextField txtNom = new TextField();
	        txtNom.setPromptText("Nom complet *");
	        txtNom.setStyle(getFieldStyle());
	        txtNom.setPrefWidth(250);

	        TextField txtEmail = new TextField();
	        txtEmail.setPromptText("Email *");
	        txtEmail.setStyle(getFieldStyle());
	        txtEmail.setPrefWidth(250);

	        PasswordField txtPassword = new PasswordField();
	        txtPassword.setPromptText("Mot de passe *");
	        txtPassword.setStyle(getFieldStyle());
	        txtPassword.setPrefWidth(250);

	        TextField txtPermis = new TextField();
	        txtPermis.setPromptText("Numéro de permis *");
	        txtPermis.setStyle(getFieldStyle());
	        txtPermis.setPrefWidth(250);

	        TextField txtPhone = new TextField();
	        txtPhone.setPromptText("Téléphone * (ex: 0551-23-45-67)");
	        txtPhone.setStyle(getFieldStyle());
	        txtPhone.setPrefWidth(250);

	        TextField txtAdresse = new TextField();
	        txtAdresse.setPromptText("Adresse *");
	        txtAdresse.setStyle(getFieldStyle());
	        txtAdresse.setPrefWidth(250);

	        // Ajouter les champs au grid
	        grid.add(txtNom, 0, 0, 2, 1);
	        grid.add(txtEmail, 0, 1, 1, 1);
	        grid.add(txtPassword, 1, 1, 1, 1);
	        grid.add(txtPermis, 0, 2, 2, 1);
	        grid.add(txtPhone, 0, 3, 1, 1);
	        grid.add(txtAdresse, 1, 3, 1, 1);

	        Button btnRegister = new Button("S'INSCRIRE");
	        btnRegister.setStyle(
	                "-fx-background-color: #38a169; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-size: 16px; " +
	                        "-fx-font-weight: 800; " +
	                        "-fx-padding: 15; " +
	                        "-fx-background-radius: 8; " +
	                        "-fx-cursor: hand; " +
	                        "-fx-max-width: infinity;");
	        GridPane.setColumnSpan(btnRegister, 2);
	        
	        btnRegister.setOnAction(e -> {
	            if (validateRegistration(txtNom, txtEmail, txtPassword, txtPermis, txtPhone, txtAdresse)) {
	                // Créer l'utilisateur
	                User newUser = new User(
	                    "U" + System.currentTimeMillis(),
	                    txtNom.getText().trim(),
	                    txtEmail.getText().trim(),
	                    txtPassword.getText().trim(),
	                    txtPermis.getText().trim(),
	                    txtPhone.getText().trim(),
	                    txtAdresse.getText().trim()
	                );
	                
	                // Connecter l'utilisateur
	                Main.setCurrentUser(newUser);
	                
	                // Afficher message de succès
	                showSuccessMessage("Inscription réussie ! Bienvenue " + newUser.getNom());
	                
	                // Rediriger vers le catalogue
	                Main.showCatalogueView();
	            }
	        });

	        grid.add(btnRegister, 0, 4);

	        Hyperlink linkLogin = new Hyperlink("Déjà un compte ? Se connecter");
	        linkLogin.setStyle("-fx-text-fill: #3182ce; -fx-font-weight: 600; -fx-font-size: 14px;");
	        linkLogin.setOnAction(e -> Main.showLoginView());
	        GridPane.setColumnSpan(linkLogin, 2);
	        grid.add(linkLogin, 0, 5);

	        form.getChildren().addAll(formIcon, title, subtitle, grid);
	        return form;
	    }

	    private boolean validateRegistration(TextField nom, TextField email, PasswordField password,
	                                        TextField permis, TextField phone, TextField adresse) {
	        // Vérifier tous les champs
	        if (nom.getText().trim().isEmpty()) {
	            showAlert("Champ manquant", "Le nom complet est obligatoire.");
	            nom.requestFocus();
	            return false;
	        }
	        
	        if (email.getText().trim().isEmpty()) {
	            showAlert("Champ manquant", "L'email est obligatoire.");
	            email.requestFocus();
	            return false;
	        }
	        
	        // Vérifier format email
	        if (!email.getText().trim().contains("@")) {
	            showAlert("Email invalide", "Veuillez saisir une adresse email valide.");
	            email.requestFocus();
	            return false;
	        }
	        
	        if (password.getText().trim().isEmpty()) {
	            showAlert("Champ manquant", "Le mot de passe est obligatoire.");
	            password.requestFocus();
	            return false;
	        }
	        
	        if (password.getText().trim().length() < 6) {
	            showAlert("Mot de passe trop court", "Le mot de passe doit contenir au moins 6 caractères.");
	            password.requestFocus();
	            return false;
	        }
	        
	        if (permis.getText().trim().isEmpty()) {
	            showAlert("Champ manquant", "Le numéro de permis est obligatoire.");
	            permis.requestFocus();
	            return false;
	        }
	        
	        if (phone.getText().trim().isEmpty()) {
	            showAlert("Champ manquant", "Le téléphone est obligatoire.");
	            phone.requestFocus();
	            return false;
	        }
	        
	        // Vérifier format téléphone (algérien)
	        String phoneText = phone.getText().trim();
	        if (!phoneText.matches("\\d{10}|\\d{4}-\\d{2}-\\d{2}-\\d{2}")) {
	            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	            alert.setTitle("Format de téléphone");
	            alert.setHeaderText("Format de téléphone non standard");
	            alert.setContentText("Le format recommandé est 0551-23-45-67.\n" +
	                    "Voulez-vous continuer avec ce numéro ?");
	            
	            ButtonType ouiButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
	            ButtonType nonButton = new ButtonType("Non", ButtonBar.ButtonData.NO);
	            alert.getButtonTypes().setAll(ouiButton, nonButton);
	            
	            java.util.Optional<ButtonType> result = alert.showAndWait();
	            if (result.isPresent() && result.get() == nonButton) {
	                phone.requestFocus();
	                return false;
	            }
	        }
	        
	        if (adresse.getText().trim().isEmpty()) {
	            showAlert("Champ manquant", "L'adresse est obligatoire.");
	            adresse.requestFocus();
	            return false;
	        }
	        
	        return true;
	    }

	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
	    private void showSuccessMessage(String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Succès");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

	    private String getFieldStyle() {
	        return "-fx-background-color: #f7fafc; " +
	                "-fx-background-radius: 8; " +
	                "-fx-border-color: #e2e8f0; " +
	                "-fx-border-radius: 8; " +
	                "-fx-border-width: 1; " +
	                "-fx-padding: 12; " +
	                "-fx-font-size: 15px;";
	    }

	    private ImageView loadImage(String path, double height, double width) {
	        ImageView imageView = new ImageView();
	        try {
	            javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResourceAsStream(path));
	            imageView.setImage(image);
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setPreserveRatio(true);
	        } catch (Exception e) {
	            imageView.setFitHeight(height);
	            imageView.setFitWidth(width);
	            imageView.setStyle("-fx-background-color: #e2e8f0; -fx-background-radius: 4;");
	        }
	        return imageView;
	    }

	    public BorderPane getView() {
	        return view;
	    }
	}
	public class ReservationView {
	    private BorderPane view;
	    private ComboBox<String> voitureCombo;
	    private DatePicker dateDebutPicker;
	    private DatePicker dateFinPicker;
	    private CheckBox cbGPS, cbAssurance, cbSiegeEnfant;

	    // Champs d'information client
	    private TextField txtNom;
	    private TextField txtPermis;
	    private TextField txtPhone;

	    // Bouton de réservation (référence directe)
	    private Button btnReserver;

	    // Composants du récapitulatif
	    private Label recapVoiture, recapPrix, recapDuree, recapDateDebut, recapDateFin;
	    private Label recapSousTotal, recapOptions, recapGrandTotal;
	    private ImageView carImage; // Référence à l'image de la voiture

	    // Map pour associer les voitures à leurs images
	    private Map<String, String> voitureImages;

	    public ReservationView() {
	        // Initialiser la map des images
	        initVoitureImages();
	        createView();
	        prefillClientInfo(); // Pré-remplir si connecté
	    }

	    private void initVoitureImages() {
	        voitureImages = new HashMap<>();
	        // Ajouter les images correspondant aux voitures du ComboBox avec les noms EXACTS
	        voitureImages.put("Toyota RAV4", "images/Toyota rav4.jpg");
	        voitureImages.put("Hyundai Tucson", "images/hyundai tucson.jpg");
	        voitureImages.put("Peugeot 208", "images/peugeot 208.jpg");
	        voitureImages.put("BMW Série 5", "images/BMW serie 5.jpg");
	        voitureImages.put("Toyota Highlander", "images/toyota highlander.jpg");

	        // Image par défaut si les spécifiques n'existent pas
	        voitureImages.put("DEFAULT", "images/voiture_icon.png");
	    }

	    // Méthode pour pré-remplir les informations client si connecté
	    private void prefillClientInfo() {
	        User currentUser = Main.getCurrentUser();
	        if (currentUser != null) {
	            // Pré-remplir les champs avec les infos du profil
	            txtNom.setText(currentUser.getNom());
	            txtPermis.setText(currentUser.getPermis());
	            txtPhone.setText(currentUser.getTelephone());
	            
	            // Ajouter une note indiquant que les infos viennent du profil
	            txtNom.setTooltip(new Tooltip("Pré-rempli depuis votre profil"));
	            txtPermis.setTooltip(new Tooltip("Pré-rempli depuis votre profil"));
	            txtPhone.setTooltip(new Tooltip("Pré-rempli depuis votre profil"));
	            
	            // Style pour indiquer que c'est pré-rempli
	            String prefilledStyle = "-fx-background-color: #f0fff4; " +
	                                   "-fx-border-color: #9ae6b4; " +
	                                   "-fx-border-width: 1; " +
	                                   "-fx-border-radius: 8; " +
	                                   "-fx-background-radius: 8; " +
	                                   "-fx-padding: 12; " +
	                                   "-fx-font-size: 15px;";
	            
	            txtNom.setStyle(prefilledStyle);
	            txtPermis.setStyle(prefilledStyle);
	            txtPhone.setStyle(prefilledStyle);
	            
	            // Les champs sont pré-remplis, mettre à jour l'état du bouton
	            updateBoutonReservation();
	            
	            System.out.println("Informations client pré-remplies depuis le profil");
	        }
	    }

	    private void createView() {
	        view = new BorderPane();
	        view.setStyle("-fx-background-color: #f7fafc;");

	        // HEADER
	        HeaderView headerView = new HeaderView("Réservation");
	        view.setTop(headerView.getHeader());

	        // CONTENU PRINCIPAL avec ScrollPane pour tout voir
	        ScrollPane scrollPane = new ScrollPane();
	        scrollPane.setFitToWidth(true);
	        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

	        VBox mainContent = createMainContent();
	        scrollPane.setContent(mainContent);
	        view.setCenter(scrollPane);
	    }

	    private VBox createMainContent() {
	        VBox content = new VBox(20);
	        content.setPadding(new Insets(30, 40, 50, 40));
	        content.setAlignment(Pos.TOP_CENTER);

	        // Titre
	        Label title = new Label("📅 RÉSERVATION DE VOITURE");
	        title.setStyle("-fx-font-size: 32px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

	        Label subtitle = new Label("Réservez votre voiture en quelques étapes");
	        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #718096;");
	        
	        // Afficher le statut de connexion
	        User currentUser = Main.getCurrentUser();
	        if (currentUser != null) {
	            Label statusLabel = new Label("✅ Connecté en tant que " + currentUser.getNom());
	            statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #38a169; -fx-font-weight: 600; -fx-padding: 5 0;");
	            content.getChildren().add(statusLabel);
	        } else {
	            Label statusLabel = new Label("⚠️ Non connecté - Les informations ne seront pas sauvegardées");
	            statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #d69e2e; -fx-font-weight: 600; -fx-padding: 5 0;");
	            content.getChildren().add(statusLabel);
	        }

	        // Conteneur principal
	        HBox mainContainer = new HBox(40);
	        mainContainer.setAlignment(Pos.TOP_CENTER);
	        mainContainer.setPadding(new Insets(20, 0, 0, 0));

	        // Colonne gauche - Formulaire
	        VBox formColumn = createFormColumn();
	        formColumn.setPrefWidth(550);

	        // Colonne droite - Récapitulatif
	        VBox recapColumn = createRecapColumn();
	        recapColumn.setPrefWidth(500);

	        mainContainer.getChildren().addAll(formColumn, recapColumn);
	        content.getChildren().addAll(title, subtitle, mainContainer);

	        return content;
	    }

	    private VBox createFormColumn() {
	        VBox formColumn = new VBox(25);
	        formColumn.setPadding(new Insets(30));
	        formColumn.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 15; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 15; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

	        Label formTitle = new Label("DÉTAILS DE LA RÉSERVATION");
	        formTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 800; -fx-text-fill: #2d3748;");

	        // Sélection de voiture
	        VBox voitureSection = new VBox(10);
	        Label voitureLabel = new Label("Voiture sélectionnée");
	        voitureLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        voitureCombo = new ComboBox<>();
	        voitureCombo.getItems().addAll(
	                "Toyota RAV4 - 4,500 DA/jour",
	                "Hyundai Tucson - 4,200 DA/jour",
	                "Peugeot 208 - 3,800 DA/jour",
	                "BMW Série 5 - 13,000 DA/jour",
	                "Toyota Highlander - 5,700 DA/jour");
	        voitureCombo.setValue("Toyota RAV4 - 4,500 DA/jour");
	        voitureCombo.setStyle(getFieldStyle() + " -fx-padding: 10;");
	        voitureCombo.setOnAction(e -> {
	            updateRecap();
	            updateCarImage(); // Mettre à jour l'image quand la voiture change
	        });

	        voitureSection.getChildren().addAll(voitureLabel, voitureCombo);

	        // Dates
	        GridPane datesGrid = new GridPane();
	        datesGrid.setHgap(20);
	        datesGrid.setVgap(15);

	        Label dateDebutLabel = new Label("Date de début");
	        dateDebutLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        dateDebutPicker = new DatePicker(LocalDate.now().plusDays(1));
	        dateDebutPicker.setStyle(getFieldStyle());
	        dateDebutPicker.setOnAction(e -> updateRecap());

	        Label dateFinLabel = new Label("Date de fin");
	        dateFinLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        dateFinPicker = new DatePicker(LocalDate.now().plusDays(7));
	        dateFinPicker.setStyle(getFieldStyle());
	        dateFinPicker.setOnAction(e -> updateRecap());

	        datesGrid.add(dateDebutLabel, 0, 0);
	        datesGrid.add(dateDebutPicker, 0, 1);
	        datesGrid.add(dateFinLabel, 1, 0);
	        datesGrid.add(dateFinPicker, 1, 1);

	        // Informations client (OBLIGATOIRE)
	        VBox clientSection = new VBox(10);
	        Label clientLabel = new Label("Informations client *");
	        clientLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568; -fx-font-size: 16px;");
	        
	        // Bouton pour se connecter si non connecté
	        User currentUser = Main.getCurrentUser();
	        if (currentUser == null) {
	            HBox loginPrompt = new HBox(10);
	            loginPrompt.setAlignment(Pos.CENTER_LEFT);
	            loginPrompt.setPadding(new Insets(0, 0, 10, 0));
	            
	            Label loginText = new Label("Pour pré-remplir automatiquement :");
	            loginText.setStyle("-fx-font-size: 14px; -fx-text-fill: #718096;");
	            
	            Button btnConnect = new Button("Se connecter");
	            btnConnect.setStyle(
	                "-fx-background-color: #3182ce; " +
	                "-fx-text-fill: white; " +
	                "-fx-font-size: 12px; " +
	                "-fx-font-weight: 600; " +
	                "-fx-padding: 6 12; " +
	                "-fx-background-radius: 4; " +
	                "-fx-cursor: hand;");
	            btnConnect.setOnAction(e -> Main.showLoginViewForReservation());
	            
	            loginPrompt.getChildren().addAll(loginText, btnConnect);
	            clientSection.getChildren().add(loginPrompt);
	        } else {
	            // Message indiquant que les infos viennent du profil
	            HBox profileInfo = new HBox(10);
	            profileInfo.setAlignment(Pos.CENTER_LEFT);
	            profileInfo.setPadding(new Insets(0, 0, 10, 0));
	            
	            Label profileText = new Label("✓ Informations pré-remplies depuis votre profil");
	            profileText.setStyle("-fx-font-size: 14px; -fx-text-fill: #38a169; -fx-font-weight: 500;");
	            
	            Button btnEditProfile = new Button("Modifier profil");
	            btnEditProfile.setStyle(
	                "-fx-background-color: transparent; " +
	                "-fx-text-fill: #3182ce; " +
	                "-fx-font-size: 12px; " +
	                "-fx-font-weight: 600; " +
	                "-fx-padding: 4 8; " +
	                "-fx-border-color: #3182ce; " +
	                "-fx-border-width: 1; " +
	                "-fx-border-radius: 3; " +
	                "-fx-cursor: hand;");
	            btnEditProfile.setOnAction(e -> Main.showProfileView());
	            
	            profileInfo.getChildren().addAll(profileText, btnEditProfile);
	            clientSection.getChildren().add(profileInfo);
	        }

	        txtNom = new TextField();
	        txtNom.setPromptText("Nom complet *");
	        txtNom.setStyle(getFieldStyle());

	        txtPermis = new TextField();
	        txtPermis.setPromptText("Numéro de permis *");
	        txtPermis.setStyle(getFieldStyle());

	        txtPhone = new TextField();
	        txtPhone.setPromptText("Téléphone * (ex: 0551-23-45-67)");
	        txtPhone.setStyle(getFieldStyle());

	        // Si non connecté, ajouter un lien vers la création de compte
	        if (currentUser == null) {
	            Hyperlink linkCreateAccount = new Hyperlink("Créer un compte pour sauvegarder mes informations");
	            linkCreateAccount.setStyle("-fx-text-fill: #3182ce; -fx-font-weight: 500; -fx-font-size: 13px;");
	            linkCreateAccount.setOnAction(e -> Main.showRegisterView());
	            clientSection.getChildren().add(linkCreateAccount);
	        }

	        clientSection.getChildren().addAll(txtNom, txtPermis, txtPhone);

	        // Options supplémentaires
	        VBox optionsSection = new VBox(15);
	        Label optionsLabel = new Label("Options supplémentaires");
	        optionsLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: #4a5568;");

	        VBox optionsList = new VBox(10);
	        cbGPS = new CheckBox("GPS (+ 500 DA/jour)");
	        cbAssurance = new CheckBox("Assurance tout risque (+ 1,000 DA/jour)");
	        cbSiegeEnfant = new CheckBox("Siège enfant (+ 300 DA/jour)");

	        // Ajouter les listeners pour mettre à jour le récap
	        cbGPS.selectedProperty().addListener((obs, oldVal, newVal) -> updateRecap());
	        cbAssurance.selectedProperty().addListener((obs, oldVal, newVal) -> updateRecap());
	        cbSiegeEnfant.selectedProperty().addListener((obs, oldVal, newVal) -> updateRecap());

	        for (CheckBox cb : new CheckBox[] { cbGPS, cbAssurance, cbSiegeEnfant }) {
	            cb.setStyle("-fx-text-fill: #4a5568; -fx-font-size: 14px;");
	        }

	        optionsList.getChildren().addAll(cbGPS, cbAssurance, cbSiegeEnfant);
	        optionsSection.getChildren().addAll(optionsLabel, optionsList);

	        // Bouton de réservation
	        btnReserver = new Button("💳 CONFIRMER LA RÉSERVATION");
	        btnReserver.setStyle(
	                "-fx-background-color: #cbd5e0; " +
	                        "-fx-text-fill: #718096; " +
	                        "-fx-font-size: 18px; " +
	                        "-fx-font-weight: 800; " +
	                        "-fx-padding: 20; " +
	                        "-fx-background-radius: 10; " +
	                        "-fx-cursor: default; " +
	                        "-fx-max-width: infinity; " +
	                        "-fx-effect: none;");
	        btnReserver.setOnAction(e -> {
	            if (validateReservation()) {
	                showConfirmationPopup();
	            }
	        });

	        // Désactiver le bouton initialement
	        btnReserver.setDisable(true);

	        // Ajouter les listeners pour la validation en temps réel
	        txtNom.textProperty().addListener((obs, oldVal, newVal) -> updateBoutonReservation());
	        txtPermis.textProperty().addListener((obs, oldVal, newVal) -> updateBoutonReservation());
	        txtPhone.textProperty().addListener((obs, oldVal, newVal) -> updateBoutonReservation());

	        formColumn.getChildren().addAll(formTitle, voitureSection, datesGrid, clientSection, optionsSection,
	                btnReserver);

	        return formColumn;
	    }

	    // Méthode pour mettre à jour l'image de la voiture
	    private void updateCarImage() {
	        try {
	            String selectedVoiture = voitureCombo.getValue();
	            if (selectedVoiture != null && !selectedVoiture.isEmpty()) {
	                // Extraire le nom de la voiture (avant le "-")
	                String voitureName = selectedVoiture.split(" - ")[0];

	                // Récupérer le chemin de l'image correspondante
	                String imagePath = voitureImages.get(voitureName);
	                if (imagePath == null) {
	                    imagePath = voitureImages.get("DEFAULT");
	                }

	                System.out.println("Tentative de chargement de l'image: " + imagePath + " pour " + voitureName);

	                // Essayer de charger l'image
	                Image image = null;
	                try {
	                    image = new Image(getClass().getResourceAsStream(imagePath));
	                    if (image.isError()) {
	                        throw new Exception("Erreur lors du chargement de l'image");
	                    }
	                } catch (Exception e) {
	                    System.err.println("Échec du chargement de " + imagePath + ", essai avec autres variations...");

	                    // Essayer différentes variations
	                    String[] variations = {
	                            imagePath.toLowerCase(),
	                            imagePath.replace(" ", "_"),
	                            imagePath.replace(" ", ""),
	                            "images/" + voitureName.toLowerCase().replace(" ", "") + ".jpg",
	                            "images/" + voitureName.toLowerCase().replace(" ", "_") + ".jpg"
	                    };

	                    for (String variation : variations) {
	                        try {
	                            System.out.println("Essai avec: " + variation);
	                            image = new Image(getClass().getResourceAsStream(variation));
	                            if (!image.isError()) {
	                                System.out.println("Succès avec: " + variation);
	                                break;
	                            }
	                        } catch (Exception ex) {
	                            // Continuer avec la prochaine variation
	                        }
	                    }

	                    // Si toujours pas d'image, utiliser l'image par défaut
	                    if (image == null || image.isError()) {
	                        System.out.println("Utilisation de l'image par défaut");
	                        image = new Image(getClass().getResourceAsStream(voitureImages.get("DEFAULT")));
	                    }
	                }

	                // Mettre à jour l'ImageView
	                if (carImage != null && image != null) {
	                    carImage.setImage(image);
	                    carImage.setFitHeight(120);
	                    carImage.setFitWidth(200);
	                    carImage.setPreserveRatio(true);
	                    carImage.setStyle("-fx-background-color: #f7fafc; -fx-background-radius: 10; -fx-padding: 10;");
	                }
	            }
	        } catch (Exception e) {
	            System.err.println("Erreur lors du chargement de l'image: " + e.getMessage());
	            e.printStackTrace();
	            // Charger l'image par défaut en cas d'erreur
	            try {
	                Image defaultImage = new Image(getClass().getResourceAsStream(voitureImages.get("DEFAULT")));
	                if (carImage != null) {
	                    carImage.setImage(defaultImage);
	                }
	            } catch (Exception ex) {
	                System.err.println("Impossible de charger l'image par défaut: " + ex.getMessage());
	            }
	        }
	    }

	    // Méthode pour mettre à jour l'état du bouton de réservation
	    private void updateBoutonReservation() {
	        boolean tousRemplis = !txtNom.getText().trim().isEmpty() &&
	                !txtPermis.getText().trim().isEmpty() &&
	                !txtPhone.getText().trim().isEmpty();

	        btnReserver.setDisable(!tousRemplis);

	        if (tousRemplis) {
	            btnReserver.setStyle(
	                    "-fx-background-color: #38a169; " +
	                            "-fx-text-fill: white; " +
	                            "-fx-font-size: 18px; " +
	                            "-fx-font-weight: 800; " +
	                            "-fx-padding: 20; " +
	                            "-fx-background-radius: 10; " +
	                            "-fx-cursor: hand; " +
	                            "-fx-max-width: infinity; " +
	                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");
	        } else {
	            btnReserver.setStyle(
	                    "-fx-background-color: #cbd5e0; " +
	                            "-fx-text-fill: #718096; " +
	                            "-fx-font-size: 18px; " +
	                            "-fx-font-weight: 800; " +
	                            "-fx-padding: 20; " +
	                            "-fx-background-radius: 10; " +
	                            "-fx-cursor: default; " +
	                            "-fx-max-width: infinity; " +
	                            "-fx-effect: none;");
	        }
	    }

	    private VBox createRecapColumn() {
	        VBox recapColumn = new VBox(25);
	        recapColumn.setPadding(new Insets(30));
	        recapColumn.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 15; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 15; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 5);");

	        Label recapTitle = new Label("📋 RÉCAPITULATIF");
	        recapTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 800; -fx-text-fill: #2d3748;");

	        // Image voiture - Créer l'ImageView et charger l'image initiale
	        carImage = new ImageView();
	        carImage.setFitHeight(120);
	        carImage.setFitWidth(200);
	        carImage.setPreserveRatio(true);
	        carImage.setStyle("-fx-background-color: #f7fafc; -fx-background-radius: 10; -fx-padding: 10;");

	        // Charger l'image initiale
	        updateCarImage();

	        // Détails de la réservation
	        VBox recapDetails = new VBox(15);
	        recapDetails.setPadding(new Insets(20, 0, 0, 0));
	        recapDetails.setStyle("-fx-background-color: #f7fafc; -fx-background-radius: 10; -fx-padding: 15;");

	        // Initialiser les labels du récap
	        recapVoiture = new Label("Voiture: Toyota RAV4");
	        recapPrix = new Label("Prix journalier: 4,500 DA");
	        recapDuree = new Label("Durée: 7 jours");
	        recapDateDebut = new Label("Date de début: " + LocalDate.now().plusDays(1));
	        recapDateFin = new Label("Date de fin: " + LocalDate.now().plusDays(7));
	        recapSousTotal = new Label("Sous-total: 31,500 DA");
	        recapOptions = new Label("Options supplémentaires: 0 DA");
	        recapGrandTotal = new Label("TOTAL: 31,500 DA");

	        // Style des labels
	        for (Label lbl : new Label[] { recapVoiture, recapPrix, recapDuree, recapDateDebut, recapDateFin,
	                recapSousTotal, recapOptions }) {
	            lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a5568; -fx-padding: 3 0;");
	        }

	        recapGrandTotal
	                .setStyle("-fx-font-size: 20px; -fx-font-weight: 900; -fx-text-fill: #38a169; -fx-padding: 10 0 0 0;");

	        Separator separator = new Separator();
	        separator.setPadding(new Insets(10, 0, 10, 0));

	        recapDetails.getChildren().addAll(
	                recapVoiture, recapPrix, recapDuree, recapDateDebut, recapDateFin,
	                separator, recapSousTotal, recapOptions, recapGrandTotal);

	        // Informations importantes
	        VBox infoBox = new VBox(10);
	        infoBox.setPadding(new Insets(20, 0, 0, 0));
	        infoBox.setStyle(
	                "-fx-background-color: #fffaf0; -fx-background-radius: 10; -fx-padding: 15; -fx-border-color: #f6ad55; -fx-border-width: 1; -fx-border-radius: 10;");

	        Label infoTitle = new Label("INFORMATIONS IMPORTANTES");
	        infoTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: 700; -fx-text-fill: #d69e2e;");

	        Label info1 = new Label("• Paiement à la prise en charge");
	        Label info2 = new Label("• Pièces à fournir: permis + CNI");
	        Label info3 = new Label("• Caution: 50,000 DA (remboursable)");
	        Label info4 = new Label("• Contact: 0552-88-45-67");

	        // Avantages de la connexion
	        User currentUser = Main.getCurrentUser();
	        if (currentUser == null) {
	            Label info5 = new Label("• Avantage connexion: Infos sauvegardées + historique");
	            info5.setStyle("-fx-font-size: 13px; -fx-text-fill: #3182ce; -fx-font-weight: bold;");
	            infoBox.getChildren().add(info5);
	        } else {
	            Label info5 = new Label("✓ Connecté: Vos infos sont sauvegardées");
	            info5.setStyle("-fx-font-size: 13px; -fx-text-fill: #38a169; -fx-font-weight: bold;");
	            infoBox.getChildren().add(info5);
	        }

	        for (Label info : new Label[] { info1, info2, info3, info4 }) {
	            info.setStyle("-fx-font-size: 13px; -fx-text-fill: #744210; -fx-padding: 2 0;");
	        }

	        infoBox.getChildren().addAll(infoTitle, info1, info2, info3, info4);

	        recapColumn.getChildren().addAll(recapTitle, carImage, recapDetails, infoBox);

	        return recapColumn;
	    }

	    // Méthode pour valider toute la réservation
	    private boolean validateReservation() {
	        // Valider les dates
	        LocalDate debut = dateDebutPicker.getValue();
	        LocalDate fin = dateFinPicker.getValue();

	        if (debut == null || fin == null) {
	            showAlert("Erreur", "Veuillez sélectionner les dates de début et de fin.");
	            return false;
	        }

	        if (debut.isAfter(fin)) {
	            showAlert("Erreur", "La date de début doit être avant la date de fin.");
	            dateDebutPicker.requestFocus();
	            return false;
	        }

	        if (debut.isBefore(LocalDate.now())) {
	            showAlert("Erreur", "La date de début ne peut pas être dans le passé.");
	            dateDebutPicker.requestFocus();
	            return false;
	        }

	        // Valider les champs client
	        if (txtNom.getText().trim().isEmpty()) {
	            showAlert("Champ manquant", "Veuillez saisir votre nom complet.");
	            txtNom.requestFocus();
	            return false;
	        }

	        if (txtPermis.getText().trim().isEmpty()) {
	            showAlert("Champ manquant", "Veuillez saisir votre numéro de permis.");
	            txtPermis.requestFocus();
	            return false;
	        }

	        if (txtPhone.getText().trim().isEmpty()) {
	            showAlert("Champ manquant", "Veuillez saisir votre numéro de téléphone.");
	            txtPhone.requestFocus();
	            return false;
	        }

	        // Valider le format du téléphone (optionnel mais recommandé)
	        String phone = txtPhone.getText().trim();
	        if (!phone.matches("\\d{10}|\\d{4}-\\d{2}-\\d{2}-\\d{2}")) {
	            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	            alert.setTitle("Format de téléphone");
	            alert.setHeaderText("Format de téléphone non standard");
	            alert.setContentText("Le format recommandé est 0551-23-45-67.\n" +
	                    "Voulez-vous continuer avec ce numéro ?");

	            ButtonType ouiButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
	            ButtonType nonButton = new ButtonType("Non", ButtonBar.ButtonData.NO);
	            alert.getButtonTypes().setAll(ouiButton, nonButton);

	            java.util.Optional<ButtonType> result = alert.showAndWait();
	            if (result.isPresent() && result.get() == nonButton) {
	                txtPhone.requestFocus();
	                return false;
	            }
	        }

	        return true;
	    }

	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

	    private void updateRecap() {
	        try {
	            // Récupérer les valeurs
	            String selectedVoiture = voitureCombo.getValue();
	            LocalDate debut = dateDebutPicker.getValue();
	            LocalDate fin = dateFinPicker.getValue();

	            if (debut == null || fin == null)
	                return;

	            // Calculer la durée
	            long duree = ChronoUnit.DAYS.between(debut, fin);
	            if (duree < 1)
	                duree = 1;

	            // Extraire le prix de la voiture
	            String voitureName = selectedVoiture.split(" - ")[0];
	            String prixStr = selectedVoiture.split(" - ")[1].replace(" DA/jour", "").replace(",", "");
	            double prixJournalier = Double.parseDouble(prixStr);

	            // Calcul du sous-total
	            double sousTotal = prixJournalier * duree;

	            // Calcul des options
	            double optionsTotal = 0;
	            if (cbGPS.isSelected())
	                optionsTotal += 500 * duree;
	            if (cbAssurance.isSelected())
	                optionsTotal += 1000 * duree;
	            if (cbSiegeEnfant.isSelected())
	                optionsTotal += 300 * duree;

	            // TOTAL (pas de taxes en Algérie)
	            double total = sousTotal + optionsTotal;

	            // Mettre à jour les labels
	            recapVoiture.setText("Voiture: " + voitureName);
	            recapPrix.setText("Prix journalier: " + formatPrix(prixJournalier) + " DA");
	            recapDuree.setText("Durée: " + duree + " jour" + (duree > 1 ? "s" : ""));
	            recapDateDebut.setText("Date de début: " + debut.toString());
	            recapDateFin.setText("Date de fin: " + fin.toString());
	            recapSousTotal.setText("Sous-total: " + formatPrix(sousTotal) + " DA");
	            recapOptions.setText("Options: " + formatPrix(optionsTotal) + " DA");
	            recapGrandTotal.setText("TOTAL: " + formatPrix(total) + " DA");

	        } catch (Exception e) {
	            System.out.println("Erreur dans updateRecap: " + e.getMessage());
	        }
	    }

	    private String formatPrix(double prix) {
	        return String.format("%,.0f", prix).replace(",", " ");
	    }

	    private void showConfirmationPopup() {
	        User currentUser = Main.getCurrentUser();
	        String userInfo = "";
	        
	        if (currentUser != null) {
	            userInfo = "Compte: " + currentUser.getEmail() + "\n" +
	                      "Les informations ont été sauvegardées dans votre profil.\n\n";
	        } else {
	            userInfo = "⚠️ Non connecté - Les informations ne seront pas sauvegardées.\n" +
	                      "Créez un compte pour garder un historique de vos réservations.\n\n";
	        }
	        
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Réservation confirmée");
	        alert.setHeaderText("✅ Réservation confirmée avec succès !");
	        alert.setContentText(
	                userInfo +
	                "Nom client: " + txtNom.getText() + "\n" +
	                "Permis: " + txtPermis.getText() + "\n" +
	                "Téléphone: " + txtPhone.getText() + "\n" +
	                "Voiture: " + voitureCombo.getValue().split(" - ")[0] + "\n\n" +
	                "Votre réservation a été enregistrée.\n" +
	                "Vous recevrez un SMS de confirmation.\n" +
	                "Présentez-vous à l'agence avec votre permis et CNI.\n\n" +
	                "Référence: RES-" + System.currentTimeMillis());

	        // Ajouter un bouton OK personnalisé
	        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
	        alert.getButtonTypes().setAll(okButton);

	        alert.showAndWait();

	        // Optionnel: Réinitialiser le formulaire
	        // resetForm();
	    }

	    // Méthode optionnelle pour réinitialiser le formulaire
	    private void resetForm() {
	        User currentUser = Main.getCurrentUser();
	        
	        // Ne pas effacer si pré-rempli depuis le profil
	        if (currentUser == null) {
	            txtNom.clear();
	            txtPermis.clear();
	            txtPhone.clear();
	        }
	        
	        dateDebutPicker.setValue(LocalDate.now().plusDays(1));
	        dateFinPicker.setValue(LocalDate.now().plusDays(7));
	        cbGPS.setSelected(false);
	        cbAssurance.setSelected(false);
	        cbSiegeEnfant.setSelected(false);
	        updateRecap();
	        updateCarImage(); // Mettre à jour l'image aussi
	        updateBoutonReservation(); // Mettre à jour l'état du bouton
	    }

	    private String getFieldStyle() {
	        return "-fx-background-color: #f7fafc; " +
	                "-fx-background-radius: 8; " +
	                "-fx-border-color: #e2e8f0; " +
	                "-fx-border-radius: 8; " +
	                "-fx-border-width: 1; " +
	                "-fx-padding: 12; " +
	                "-fx-font-size: 15px;";
	    }

	    public BorderPane getView() {
	        return view;
	    }
	}
	public class VoitureDetailView {
	    private BorderPane view;
	    private CatalogueView.Voiture voiture;

	    public VoitureDetailView(CatalogueView.Voiture voiture) {
	        this.voiture = voiture;
	        createView();
	    }

	    private void createView() {
	        view = new BorderPane();
	        view.setStyle("-fx-background-color: #f7fafc;");

	        // HEADER
	        HeaderView headerView = new HeaderView("Accueil");
	        view.setTop(headerView.getHeader());

	        // CONTENU PRINCIPAL avec ScrollPane
	        ScrollPane scrollContent = createScrollContent();
	        view.setCenter(scrollContent);
	    }

	    private ScrollPane createScrollContent() {
	        ScrollPane scrollPane = new ScrollPane();
	        scrollPane.setFitToWidth(true);
	        scrollPane.setFitToHeight(true);
	        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
	        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

	        VBox mainContent = createMainContent();
	        scrollPane.setContent(mainContent);

	        return scrollPane;
	    }

	    private VBox createMainContent() {
	        VBox content = new VBox(30);
	        content.setPadding(new Insets(30, 40, 60, 40)); // Ajout de padding en bas pour l'espace

	        // ========== FLÈCHE DE RETOUR ==========
	        HBox topBar = new HBox();
	        topBar.setAlignment(Pos.TOP_LEFT);

	        Button btnRetour = new Button("← Retour au catalogue");
	        btnRetour.setStyle(
	                "-fx-background-color: transparent; " +
	                        "-fx-text-fill: #3182ce; " +
	                        "-fx-font-size: 16px; " +
	                        "-fx-font-weight: 600; " +
	                        "-fx-padding: 10 20; " +
	                        "-fx-border-color: #3182ce; " +
	                        "-fx-border-width: 2; " +
	                        "-fx-border-radius: 5; " +
	                        "-fx-cursor: hand;");

	        btnRetour.setOnAction(e -> app.Main.showCatalogueView());
	        topBar.getChildren().add(btnRetour);

	        HBox mainRow = new HBox(40);
	        mainRow.setAlignment(Pos.TOP_CENTER);

	        // Colonne gauche - Image grande
	        VBox imageColumn = new VBox(20);
	        imageColumn.setPrefWidth(500);

	        // Utiliser l'image spécifique de la voiture
	        ImageView mainImage = new ImageView(voiture.icone.getImage());
	        mainImage.setFitHeight(300);
	        mainImage.setFitWidth(500);
	        mainImage.setPreserveRatio(true);
	        mainImage.setStyle(
	                "-fx-background-color: white; " +
	                        "-fx-background-radius: 10; " +
	                        "-fx-border-color: #e2e8f0; " +
	                        "-fx-border-radius: 10; " +
	                        "-fx-border-width: 1; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

	        imageColumn.getChildren().add(mainImage);

	        // Colonne droite - Détails
	        VBox detailsColumn = new VBox(25);
	        detailsColumn.setPrefWidth(600);

	        Label title = new Label(voiture.nom);
	        title.setStyle("-fx-font-size: 32px; -fx-font-weight: 900; -fx-text-fill: #1a365d;");

	        HBox brandModel = new HBox(20);
	        brandModel.setAlignment(Pos.CENTER_LEFT);

	        // Utiliser les getters de la voiture
	        Label marque = new Label("Marque: " + voiture.getMarque());
	        Label modele = new Label("Modèle: " + voiture.getModele());

	        for (Label lbl : new Label[] { marque, modele }) {
	            lbl.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568; -fx-font-weight: 500;");
	        }

	        brandModel.getChildren().addAll(marque, modele);

	        Label annee = new Label("Année: 2025");
	        annee.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568; -fx-font-weight: 500;");

	        HBox priceBox = new HBox(10);
	        priceBox.setAlignment(Pos.CENTER_LEFT);

	        // Afficher DA
	        Label price = new Label(String.format("%,.0f DA", voiture.getPrix()));
	        price.setStyle("-fx-font-size: 36px; -fx-font-weight: 900; -fx-text-fill: #2d3748;");

	        Label perDay = new Label("/ jour");
	        perDay.setStyle("-fx-font-size: 18px; -fx-text-fill: #718096; -fx-font-weight: 500;");

	        priceBox.getChildren().addAll(price, perDay);

	        HBox dispoBox = new HBox(10);
	        dispoBox.setAlignment(Pos.CENTER_LEFT);

	        javafx.scene.shape.Circle dispoCircle = new javafx.scene.shape.Circle(8);
	        dispoCircle.setFill(voiture.isDisponible() ? Color.GREEN : Color.RED);

	        Label dispoLabel = new Label(voiture.isDisponible() ? "Disponible" : "Non disponible");
	        dispoLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 700;");
	        dispoLabel.setTextFill(voiture.isDisponible() ? Color.GREEN : Color.RED);

	        dispoBox.getChildren().addAll(dispoCircle, dispoLabel);

	        VBox optionsBox = new VBox(15);
	        Label optionsTitle = new Label("OPTIONS INCLUSES");
	        optionsTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

	        String[] options = {
	                "• Assurance tout risque",
	                "• Assistance 24h/24",
	                "• Kilométrage illimité",
	                "• Sièges enfants (sur demande)",
	                "• GPS intégré"
	        };

	        VBox optionsList = new VBox(8);
	        for (String opt : options) {
	            Label optLabel = new Label(opt);
	            optLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568;");
	            optionsList.getChildren().add(optLabel);
	        }

	        optionsBox.getChildren().addAll(optionsTitle, optionsList);

	        // ========== SECTION CARACTÉRISTIQUES TECHNIQUES ==========
	        VBox specsBox = new VBox(15);
	        Label specsTitle = new Label("CARACTÉRISTIQUES TECHNIQUES");
	        specsTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 700; -fx-text-fill: #1a365d;");

	        GridPane specsGrid = new GridPane();
	        specsGrid.setHgap(40);
	        specsGrid.setVgap(15);
	        specsGrid.setPadding(new Insets(10, 0, 0, 0));

	        // Ajouter des caractéristiques
	        String[][] specifications = {
	                { "Moteur", "2.0L Turbo" },
	                { "Puissance", "245 ch" },
	                { "Transmission", "Automatique 8 vitesses" },
	                { "Consommation", "7.2L/100km" },
	                { "Nombre de places", "5" },
	                { "Coffre", "480 L" },
	                { "Climatisation", "Automatique bi-zone" },
	                { "Écran tactile", "10.25 pouces" }
	        };

	        for (int i = 0; i < specifications.length; i++) {
	            Label specLabel = new Label(specifications[i][0] + ":");
	            specLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568; -fx-font-weight: 500;");

	            Label valueLabel = new Label(specifications[i][1]);
	            valueLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d3748; -fx-font-weight: 600;");

	            specsGrid.add(specLabel, 0, i);
	            specsGrid.add(valueLabel, 1, i);
	        }

	        specsBox.getChildren().addAll(specsTitle, specsGrid);

	        // ========== BOUTON RÉSERVER ==========
	        Button btnReserver = new Button("📅 RÉSERVER MAINTENANT");
	        btnReserver.setStyle(
	                "-fx-background-color: #38a169; " +
	                        "-fx-text-fill: white; " +
	                        "-fx-font-size: 20px; " +
	                        "-fx-font-weight: 800; " +
	                        "-fx-padding: 20 40; " +
	                        "-fx-background-radius: 10; " +
	                        "-fx-cursor: hand; " +
	                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 5);");

	        // Redirige vers la vue de réservation
	        btnReserver.setOnAction(e -> {
	            // On passe la voiture sélectionnée à la réservation
	            app.Main.showReservationView();
	        });

	        // Ajouter un espace avant le bouton
	        Pane spacer = new Pane();
	        spacer.setPrefHeight(20);

	        detailsColumn.getChildren().addAll(
	                title, brandModel, annee, priceBox, dispoBox,
	                optionsBox, specsBox, spacer, btnReserver);

	        mainRow.getChildren().addAll(imageColumn, detailsColumn);
	        content.getChildren().addAll(topBar, mainRow);

	        return content;
	    }

	    public BorderPane getView() {
	        return view;
	    }
	}
}
