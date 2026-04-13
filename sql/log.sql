CREATE TABLE log (
    idlog SERIAL PRIMARY KEY,
    tableName VARCHAR(50),
    operation VARCHAR(50),
    dateAction TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ancienContenu TEXT,
    nouveauContenu TEXT
);

CREATE OR REPLACE FUNCTION insert_log_function(
    p_tableName VARCHAR,
    p_operation VARCHAR,
    p_ancienContenu TEXT,
    p_nouveauContenu TEXT
) RETURNS void
LANGUAGE plpgsql
AS $$
BEGIN
INSERT INTO log (
        tableName,
        operation,
        ancienContenu,
        nouveauContenu
    ) VALUES (
        p_tableName,
        p_operation,
        p_ancienContenu,
        p_nouveauContenu
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