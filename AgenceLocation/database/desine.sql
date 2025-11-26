-- ============================================
-- AgenceLocation Database Schema (SQLite Version)
-- Car Rental Agency Database
-- ============================================

-- ============================================
-- Table: voitures (Cars)
-- ============================================
CREATE TABLE IF NOT EXISTS voitures (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    marque VARCHAR(50) NOT NULL,
    modele VARCHAR(50) NOT NULL,
    prix DECIMAL(10, 2) NOT NULL,
    disponible BOOLEAN DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_voitures_disponible ON voitures(disponible);
CREATE INDEX IF NOT EXISTS idx_voitures_marque_modele ON voitures(marque, modele);

-- ============================================
-- Table: clients (Clients)
-- ============================================
CREATE TABLE IF NOT EXISTS clients (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    f_name VARCHAR(50) NOT NULL,
    l_name VARCHAR(50) NOT NULL,
    adress VARCHAR(255),
    num_tell VARCHAR(20),
    num_permis INTEGER UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_clients_name ON clients(f_name, l_name);
CREATE INDEX IF NOT EXISTS idx_clients_num_permis ON clients(num_permis);

-- ============================================
-- Table: reservations (Reservations)
-- ============================================
CREATE TABLE IF NOT EXISTS reservations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    client_id INTEGER NOT NULL,
    voiture_id INTEGER NOT NULL,
    date_reservation DATE NOT NULL,
    date_affectation DATE,
    date_retour DATE NOT NULL,
    prix DECIMAL(10, 2) NOT NULL,
    statut VARCHAR(20) DEFAULT 'en_attente',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (voiture_id) REFERENCES voitures(id) ON DELETE CASCADE,
    CHECK (date_retour >= date_reservation)
);

CREATE INDEX IF NOT EXISTS idx_reservations_client ON reservations(client_id);
CREATE INDEX IF NOT EXISTS idx_reservations_voiture ON reservations(voiture_id);
CREATE INDEX IF NOT EXISTS idx_reservations_date ON reservations(date_reservation);
CREATE INDEX IF NOT EXISTS idx_reservations_statut ON reservations(statut);

-- ============================================
-- Table: historique (Rental History)
-- ============================================
CREATE TABLE IF NOT EXISTS historique (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    client_id INTEGER NOT NULL,
    voiture_id INTEGER NOT NULL,
    date_allocation DATE NOT NULL,
    date_retour DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (voiture_id) REFERENCES voitures(id) ON DELETE CASCADE,
    CHECK (date_retour >= date_allocation)
);

CREATE INDEX IF NOT EXISTS idx_historique_client ON historique(client_id);
CREATE INDEX IF NOT EXISTS idx_historique_voiture ON historique(voiture_id);
CREATE INDEX IF NOT EXISTS idx_historique_date_allocation ON historique(date_allocation);

-- ============================================
-- Sample Data - Accurate and Comprehensive
-- ============================================

-- Insert cars (voitures)
INSERT INTO voitures (marque, modele, prix, disponible) VALUES
-- Economy cars
('Renault', 'Clio', 120.00, 1),
('Peugeot', '208', 130.00, 1),
('Dacia', 'Sandero', 100.00, 1),
('Fiat', 'Punto', 110.00, 0),
('Opel', 'Corsa', 125.00, 1),
-- Sedan cars
('Toyota', 'Corolla', 180.00, 1),
('Honda', 'Civic', 190.00, 0),
('Volkswagen', 'Jetta', 200.00, 1),
('Hyundai', 'Elantra', 175.00, 1),
('Kia', 'Cerato', 170.00, 1),
-- Luxury cars
('BMW', '320i', 350.00, 1),
('Mercedes-Benz', 'C-Class', 380.00, 0),
('Audi', 'A4', 370.00, 1),
('Lexus', 'IS 250', 400.00, 1),
-- SUVs
('Toyota', 'RAV4', 280.00, 1),
('Nissan', 'X-Trail', 270.00, 1),
('Hyundai', 'Tucson', 260.00, 0),
('Peugeot', '3008', 290.00, 1);

-- Insert clients
INSERT INTO clients (f_name, l_name, adress, num_tell, num_permis) VALUES
('Ahmed', 'Benali', '123 Rue Mohammed V, Casablanca 20000', '0612345678', 1234501),
('Fatima', 'Alaoui', '456 Avenue Hassan II, Rabat 10000', '0623456789', 1234602),
('Mohammed', 'Idrissi', '789 Boulevard Zerktouni, Marrakech 40000', '0634567890', 1234703),
('Aicha', 'Bennani', '12 Rue Ibn Battuta, Tanger 90000', '0645678901', 1234804),
('Youssef', 'Amrani', '45 Avenue Allal Ben Abdellah, Fès 30000', '0656789012', 1234905),
('Sanae', 'El Fassi', '78 Rue de la Liberté, Agadir 80000', '0667890123', 1235006),
('Omar', 'Tazi', '34 Boulevard Mohammed VI, Meknès 50000', '0678901234', 1235107),
('Laila', 'Cherkaoui', '56 Avenue Mohammed V, Oujda 60000', '0689012345', 1235208),
('Karim', 'Bensaid', '89 Rue Ahmed Chaouki, Tétouan 93000', '0690123456', 1235309),
('Nadia', 'Mansouri', '23 Avenue des FAR, Salé 11000', '0601234567', 1235410),
('Hassan', 'Lahlou', '67 Rue Ibn Sina, Kenitra 14000', '0613456789', 1235511),
('Souad', 'Rahmouni', '90 Boulevard Zerktouni, Casablanca 20000', '0624567890', 1235612);

-- Insert reservations
INSERT INTO reservations (client_id, voiture_id, date_reservation, date_affectation, date_retour, prix, statut) VALUES
-- Completed reservations (with affectation dates)
(1, 1, '2024-01-15', '2024-01-20', '2024-01-25', 600.00, 'confirme'),
(2, 6, '2024-02-01', '2024-02-05', '2024-02-10', 900.00, 'confirme'),
(3, 11, '2024-02-10', '2024-02-15', '2024-02-20', 1750.00, 'confirme'),
(4, 3, '2024-02-20', '2024-02-25', '2024-03-01', 500.00, 'confirme'),
(5, 8, '2024-03-05', '2024-03-10', '2024-03-15', 1000.00, 'confirme'),
-- Pending reservations (no affectation date yet)
(6, 2, '2024-12-20', NULL, '2024-12-27', 910.00, 'en_attente'),
(7, 9, '2024-12-22', NULL, '2024-12-29', 1190.00, 'en_attente'),
(8, 4, '2024-12-25', NULL, '2025-01-02', 880.00, 'en_attente'),
-- Active reservations (currently rented)
(9, 5, '2024-12-15', '2024-12-18', '2024-12-28', 1250.00, 'confirme'),
(10, 10, '2024-12-10', '2024-12-12', '2024-12-22', 1700.00, 'confirme'),
(11, 13, '2024-12-05', '2024-12-08', '2024-12-18', 3700.00, 'confirme'),
(12, 15, '2024-12-01', '2024-12-03', '2024-12-13', 2800.00, 'confirme'),
-- More past reservations
(1, 7, '2024-03-20', '2024-03-25', '2024-03-30', 950.00, 'confirme'),
(2, 12, '2024-04-10', '2024-04-15', '2024-04-20', 1850.00, 'confirme'),
(3, 16, '2024-05-01', '2024-05-05', '2024-05-10', 1400.00, 'confirme'),
(4, 14, '2024-05-15', '2024-05-20', '2024-05-25', 2000.00, 'confirme'),
(5, 1, '2024-06-01', '2024-06-05', '2024-06-10', 600.00, 'confirme'),
-- Cancelled reservation
(6, 17, '2024-11-10', NULL, '2024-11-17', 2030.00, 'annule');

-- Insert historique (rental history - completed rentals)
INSERT INTO historique (client_id, voiture_id, date_allocation, date_retour) VALUES
-- Historical rentals from completed reservations
(1, 1, '2024-01-20', '2024-01-25'),
(2, 6, '2024-02-05', '2024-02-10'),
(3, 11, '2024-02-15', '2024-02-20'),
(4, 3, '2024-02-25', '2024-03-01'),
(5, 8, '2024-03-10', '2024-03-15'),
(1, 7, '2024-03-25', '2024-03-30'),
(2, 12, '2024-04-15', '2024-04-20'),
(3, 16, '2024-05-05', '2024-05-10'),
(4, 14, '2024-05-20', '2024-05-25'),
(5, 1, '2024-06-05', '2024-06-10'),
-- Additional historical rentals (not from reservations table)
(6, 2, '2024-07-01', '2024-07-08'),
(7, 9, '2024-07-15', '2024-07-22'),
(8, 4, '2024-08-01', '2024-08-08'),
(9, 5, '2024-08-15', '2024-08-22'),
(10, 10, '2024-09-01', '2024-09-08'),
(11, 13, '2024-09-15', '2024-09-22'),
(12, 15, '2024-10-01', '2024-10-08'),
(1, 6, '2024-10-15', '2024-10-22'),
(2, 8, '2024-11-01', '2024-11-08'),
(3, 11, '2024-11-15', '2024-11-22');

