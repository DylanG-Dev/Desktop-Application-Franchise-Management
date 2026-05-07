-- Triggers franchise
DROP TRIGGER IF EXISTS franchise_create ON franchise;
DROP TRIGGER IF EXISTS franchise_update ON franchise;
DROP TRIGGER IF EXISTS franchise_delete ON franchise;

-- Triggers cinema
DROP TRIGGER IF EXISTS cinema_create ON cinema;
DROP TRIGGER IF EXISTS cinema_update ON cinema;
DROP TRIGGER IF EXISTS cinema_delete ON cinema;

-- Fonctions franchise
DROP FUNCTION IF EXISTS trigger_franchise_create();
DROP FUNCTION IF EXISTS trigger_franchise_update();
DROP FUNCTION IF EXISTS trigger_franchise_delete();

-- Fonctions cinema
DROP FUNCTION IF EXISTS trigger_cinema_create();
DROP FUNCTION IF EXISTS trigger_cinema_update();
DROP FUNCTION IF EXISTS trigger_cinema_delete();

-- Table log
DROP TABLE IF EXISTS log;

-- Création table log
CREATE TABLE log (
    id_log SERIAL PRIMARY KEY,
    table_name VARCHAR(50),
    operation VARCHAR(50),
    date_action TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ancien_contenu TEXT,
    nouveau_contenu TEXT,
    id_utilisateur INTEGER
);

-- Fonction d'insertion
CREATE OR REPLACE FUNCTION insert_log_function(
    p_table_name VARCHAR,
    p_operation VARCHAR,
    p_ancien_contenu TEXT,
    p_nouveau_contenu TEXT
) RETURNS void
LANGUAGE plpgsql
AS $$
DECLARE
    v_id_utilisateur INTEGER;
BEGIN
BEGIN
    -- récupère l'id utilisateur stocké en variable de session
    -- paramètre 'true' évitant une erreur si la variable n'existe pas
    v_id_utilisateur := current_setting('app.current_id_utilisateur', true)::INTEGER;
        EXCEPTION WHEN OTHERS THEN
    v_id_utilisateur := NULL;
END;
INSERT INTO log (
        table_name,
        operation,
        ancien_contenu,
        nouveau_contenu,
        id_utilisateur
    ) VALUES (
        p_table_name,
        p_operation,
        p_ancien_contenu,
        p_nouveau_contenu,
        v_id_utilisateur
    );
END;
$$;

-- FRANCHISE --
-- INSERT
CREATE OR REPLACE FUNCTION trigger_franchise_create()
RETURNS TRIGGER
AS $$
BEGIN PERFORM insert_log_function(
    'franchise',
    'INSERT',
    '',
    'ID: ' || new.id_franchise || ', Nom franchise: ' || new.nom_franchise || ', Siège social: ' || new.siege_social || ', ID Gérant: ' || new.id_gerant
);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_create
AFTER INSERT ON Franchise
FOR EACH ROW EXECUTE FUNCTION trigger_franchise_create();

-- UPDATE
CREATE OR REPLACE FUNCTION trigger_franchise_update()
RETURNS TRIGGER
AS $$
BEGIN PERFORM insert_log_function(
    'franchise',
    'UPDATE',
    'ID: ' || old.id_franchise || ', Nom: ' || old.nom_franchise  || ', Siège social: ' || old.siege_social || ', ID Gérant: ' || old.id_gerant,
    'ID: ' || new.id_franchise || ', Nom: ' || new.nom_franchise  || ', Siège social: ' || new.siege_social || ', ID Gérant: ' || new.id_gerant
);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_update
AFTER UPDATE ON Franchise
FOR EACH ROW EXECUTE FUNCTION trigger_franchise_update();

-- DELETE
CREATE OR REPLACE FUNCTION trigger_franchise_delete()
RETURNS TRIGGER
AS $$
BEGIN PERFORM insert_log_function(
    'franchise',
    'DELETE',
    'ID: ' || old.id_franchise || ', Nom: ' || old.nom_franchise  || ', Siège social: ' || OLD.siege_social || ', ID Gérant: ' || old.id_gerant,
    ''
);
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_delete
AFTER DELETE ON Franchise
FOR EACH ROW EXECUTE FUNCTION trigger_franchise_delete();

-- CINEMA --
-- INSERT
CREATE OR REPLACE FUNCTION trigger_cinema_create()
RETURNS TRIGGER
AS $$
BEGIN PERFORM insert_log_function(
    'cinema',
    'INSERT',
    '',
    'ID: ' || new.id_cinema || ', Dénomination: ' || new.denomination || ', Adresse: ' || new.adresse || ', Ville: ' || new.ville || ', ID Franchise:' || new.id_franchise
);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_create
AFTER INSERT ON cinema
FOR EACH ROW EXECUTE FUNCTION trigger_cinema_create();

-- UPDATE
CREATE OR REPLACE FUNCTION trigger_cinema_update()
RETURNS TRIGGER
AS $$ BEGIN PERFORM insert_log_function(
    'cinema',
    'UPDATE',
    'ID: ' || old.id_cinema || ', Dénomination: ' || old.denomination || ', Adresse: ' || old.adresse || ', Ville: ' || old.ville || ', ID Franchise:' || old.id_franchise,
    'ID: ' || new.id_cinema || ', Dénomination: ' || new.denomination || ', Adresse: ' || new.adresse || ', Ville: ' || new.ville || ', ID Franchise:' || new.id_franchise
);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_update
AFTER UPDATE ON cinema
FOR EACH ROW EXECUTE FUNCTION trigger_cinema_update();

-- DELETE
CREATE OR REPLACE FUNCTION trigger_cinema_delete()
RETURNS TRIGGER
AS $$
BEGIN PERFORM insert_log_function(
    'cinema',
    'DELETE',
    'ID: ' || old.id_cinema || ', Dénomination: ' || old.denomination || ', Adresse: ' || old.adresse || ', Ville: ' || old.ville || ', ID Franchise:' || old.id_franchise,
    ''
);
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_delete
AFTER DELETE ON cinema
FOR EACH ROW EXECUTE FUNCTION trigger_cinema_delete();