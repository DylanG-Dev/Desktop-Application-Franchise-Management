-- 1. Insertion des Utilisateurs (Gérants potentiels)
INSERT INTO
	utilisateur (nom, prenom, login, mdp)
VALUES
	(
		'Dupont',
		'Jean',
		'jean.dupont@email.com',
		'$2a$12$lqk6wdJxleiAEXdsEXL5q.sU6nqlwDqwBSoKnkASPvLGzVRw0kS5e'
	),
	(
		'Martin',
		'Alice',
		'alice.martin@email.com',
		'$2a$12$Pi6qMntuMg6DZJxSQgFWIOfgXOPnPYOktyN48GymWb23.KkESzTcm'
	),
	(
		'Bernard',
		'Lucas',
		'lucas.bernard@email.com',
		'$2a$12$3ysXPAb2I9cy8wTDJdBWqOQEepWqXM1egNRyXU9LJx.azE4MonmPK'
	),
    (
        'Leroy',
        'Sophie',
        'sophie.leroy@email.com',
        '$2a$12$s9y8TdbCgNb5SkQ/3Wb.VuAQKubCfUeRsPJMGqhzTTesmbyI8ulxO'
    ),
    (
        'Moreau',
        'Thomas',
        'thomas.moreau@email.com',
        '$2a$12$Yr9ar1gBKrMv3AYRSejVauuVVVI5RiwRAR0v81.zJJs7508rIWQbS'
    );

-- 2. Insertion des Franchises
-- On lie ici les franchises aux utilisateurs créés précédemment
INSERT INTO
	franchise (nom_franchise, siege_social, id_gerant)
VALUES
	('CinéMax', '12 rue de la Paix, Paris', 1),
	('Écran Total', '45 avenue des Arts, Lyon', 2),
    ('Cinépolis', '22 boulevard Haussmann, Bordeaux', 3);

-- 3. Insertion des Cinémas
-- Chaque cinéma est rattaché à une franchise via son ID
INSERT INTO
	cinema (denomination, adresse, ville, id_franchise)
VALUES
	(
		'CinéMax Étoile',
		'5 Place de l''Étoile',
		'Paris',
		1
	),
	(
		'CinéMax Rivoli',
		'100 rue de Rivoli',
		'Paris',
		1
	),
	(
		'Le Grand Écran',
		'8 rue de la République',
		'Lyon',
		2
	),
    (
        'CinéMax Montparnasse',
        '30 boulevard du Montparnasse',
        'Paris',
        1
    ),
    (
        'Écran Total Prestige',
        '17 rue de la Barre',
        'Lyon',
        2
    ),
    (
        'Cinépolis Garonne',
        '3 quai des Chartrons',
        'Bordeaux',
        3
    ),
    (
        'Cinépolis Atlantic',
        '58 cours de l''Intendance',
        'Bordeaux',
        3
    );

-- 4. Insertion des Salles
-- On crée plusieurs salles pour chaque cinéma
INSERT INTO
	salle (numero, description, nb_places, id_cinema)
VALUES
	-- Salles pour CinéMax Étoile (ID 1)
	(1, 'Salle Prestige', 150, 1),
	(2, 'Salle 2', 80, 1),
	(3, 'Salle 3', 80, 1),
	-- Salles pour CinéMax Rivoli (ID 2)
	(4, 'Grande Salle', 300, 2),
	(5, 'Petite Salle', 50, 2),
	-- Salles pour Le Grand Écran (ID 3)
	(6, 'Salle IMAX', 450, 3),
	(7, 'Salle Horizon', 120, 3),
	-- Salles pour CinéMax Montparnasse
    (1, 'Salle Dolby Atmos',  200, 4),
    (2, 'Salle 2',            100, 4),
    (3, 'Salle 3',             60, 4),
    -- Écran Total Prestige (ID 5)
    (1, 'Salle VIP',           80, 5),
    (2, 'Salle Panoramique',  180, 5),
    -- Cinépolis Garonne (ID 6)
    (1, 'Grande Salle 4K',    350, 6),
    (2, 'Salle Duo',           40, 6),
    (3, 'Salle 3D',           130, 6),
    -- Cinépolis Atlantic (ID 7)
    (1, 'Salle Premium',      220, 7),
    (2, 'Salle 2',             90, 7);