-- ============================================================
--  TABLE LOG
-- ============================================================
CREATE TABLE log (
                     id_log SERIAL PRIMARY KEY,
                     table_name VARCHAR(50),
                     operation VARCHAR(10),
                     date_action TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     ancien_contenu TEXT,
                     nouveau_contenu TEXT,
                     id_utilisateur INTEGER REFERENCES utilisateur(id_utilisateur) ON DELETE SET NULL
);

-- ============================================================
--  FRANCHISE
-- ============================================================

-- INSERT
CREATE OR REPLACE FUNCTION trigger_franchise_create()
RETURNS TRIGGER
AS $$
DECLARE
v_user_id INTEGER;
BEGIN
BEGIN
        v_user_id := current_setting('app.current_user_id', true)::INTEGER;
EXCEPTION WHEN OTHERS THEN
        v_user_id := NULL;
END;

INSERT INTO log (table_name, operation, ancien_contenu, nouveau_contenu, id_utilisateur)
VALUES (
           'franchise',
           'INSERT',
           '',
           'ID: '          || NEW.id_franchise ||
           ', Nom: '       || NEW.nom_franchise ||
           ', Siege: '     || COALESCE(NEW.siege_social, 'N/A') ||
           ', ID Gerant: ' || COALESCE(NEW.id_gerant::TEXT, 'N/A'),
           v_user_id
       );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_create
    AFTER INSERT ON franchise
    FOR EACH ROW EXECUTE FUNCTION trigger_franchise_create();

-- UPDATE
CREATE OR REPLACE FUNCTION trigger_franchise_update() RETURNS TRIGGER AS $$
DECLARE
v_user_id INTEGER;
BEGIN
BEGIN
        v_user_id := current_setting('app.current_user_id', true)::INTEGER;
EXCEPTION WHEN OTHERS THEN
        v_user_id := NULL;
END;

INSERT INTO log (table_name, operation, ancien_contenu, nouveau_contenu, id_utilisateur)
VALUES (
           'franchise',
           'UPDATE',
           'ID: '          || OLD.id_franchise ||
           ', Nom: '       || OLD.nom_franchise ||
           ', Siege: '     || COALESCE(OLD.siege_social, 'N/A') ||
           ', ID Gerant: ' || COALESCE(OLD.id_gerant::TEXT, 'N/A'),

           'ID: '          || NEW.id_franchise ||
           ', Nom: '       || NEW.nom_franchise ||
           ', Siege: '     || COALESCE(NEW.siege_social, 'N/A') ||
           ', ID Gerant: ' || COALESCE(NEW.id_gerant::TEXT, 'N/A'),
           v_user_id
       );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_update
    AFTER UPDATE ON franchise
    FOR EACH ROW EXECUTE FUNCTION trigger_franchise_update();

-- DELETE
CREATE OR REPLACE FUNCTION trigger_franchise_delete() RETURNS TRIGGER AS $$
DECLARE
v_user_id INTEGER;
BEGIN
BEGIN
        v_user_id := current_setting('app.current_user_id', true)::INTEGER;
EXCEPTION WHEN OTHERS THEN
        v_user_id := NULL;
END;

INSERT INTO log (table_name, operation, ancien_contenu, nouveau_contenu, id_utilisateur)
VALUES (
           'franchise',
           'DELETE',
           'ID: '          || OLD.id_franchise ||
           ', Nom: '       || OLD.nom_franchise ||
           ', Siege: '     || COALESCE(OLD.siege_social, 'N/A') ||
           ', ID Gerant: ' || COALESCE(OLD.id_gerant::TEXT, 'N/A'),
           '',
           v_user_id
       );
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_delete
    AFTER DELETE ON franchise
    FOR EACH ROW EXECUTE FUNCTION trigger_franchise_delete();


-- ============================================================
--  CINEMA
-- ============================================================

-- INSERT
CREATE OR REPLACE FUNCTION trigger_cinema_create() RETURNS TRIGGER AS $$
DECLARE
v_user_id INTEGER;
BEGIN
BEGIN
        v_user_id := current_setting('app.current_user_id', true)::INTEGER;
EXCEPTION WHEN OTHERS THEN
        v_user_id := NULL;
END;

INSERT INTO log (table_name, operation, ancien_contenu, nouveau_contenu, id_utilisateur)
VALUES (
           'cinema',
           'INSERT',
           '',
           'ID: '             || NEW.id_cinema ||
           ', Nom: '          || NEW.denomination ||
           ', Adresse: '      || COALESCE(NEW.adresse, 'N/A') ||
           ', Ville: '        || COALESCE(NEW.ville, 'N/A') ||
           ', ID Franchise: ' || NEW.id_franchise,
           v_user_id
       );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_create
    AFTER INSERT ON cinema
    FOR EACH ROW EXECUTE FUNCTION trigger_cinema_create();

-- UPDATE
CREATE OR REPLACE FUNCTION trigger_cinema_update() RETURNS TRIGGER AS $$
DECLARE
v_user_id INTEGER;
BEGIN
BEGIN
        v_user_id := current_setting('app.current_user_id', true)::INTEGER;
EXCEPTION WHEN OTHERS THEN
        v_user_id := NULL;
END;

INSERT INTO log (table_name, operation, ancien_contenu, nouveau_contenu, id_utilisateur)
VALUES (
           'cinema',
           'UPDATE',
           'ID: '             || OLD.id_cinema ||
           ', Nom: '          || OLD.denomination ||
           ', Adresse: '      || COALESCE(OLD.adresse, 'N/A') ||
           ', Ville: '        || COALESCE(OLD.ville, 'N/A') ||
           ', ID Franchise: ' || OLD.id_franchise,

           'ID: '             || NEW.id_cinema ||
           ', Nom: '          || NEW.denomination ||
           ', Adresse: '      || COALESCE(NEW.adresse, 'N/A') ||
           ', Ville: '        || COALESCE(NEW.ville, 'N/A') ||
           ', ID Franchise: ' || NEW.id_franchise,
           v_user_id
       );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_update
    AFTER UPDATE ON cinema
    FOR EACH ROW EXECUTE FUNCTION trigger_cinema_update();

-- DELETE
CREATE OR REPLACE FUNCTION trigger_cinema_delete() RETURNS TRIGGER AS $$
DECLARE
v_user_id INTEGER;
BEGIN
BEGIN
        v_user_id := current_setting('app.current_user_id', true)::INTEGER;
EXCEPTION WHEN OTHERS THEN
        v_user_id := NULL;
END;

INSERT INTO log (table_name, operation, ancien_contenu, nouveau_contenu, id_utilisateur)
VALUES (
           'cinema',
           'DELETE',
           'ID: '             || OLD.id_cinema ||
           ', Nom: '          || OLD.denomination ||
           ', Adresse: '      || COALESCE(OLD.adresse, 'N/A') ||
           ', Ville: '        || COALESCE(OLD.ville, 'N/A') ||
           ', ID Franchise: ' || OLD.id_franchise,
           '',
           v_user_id
       );
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_delete
    AFTER DELETE ON cinema
    FOR EACH ROW EXECUTE FUNCTION trigger_cinema_delete();

select * from log;