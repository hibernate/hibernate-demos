WITH themanager AS (
  INSERT INTO businessmanager(id, name, email, phone) VALUES
  (nextval('hibernate_sequence'), 'Dwight Schrute', 'dschrute@dundermifflin.net', '+1-202-555-0151')
  RETURNING id
)
INSERT INTO client(assignedmanager_id, id, name)
    SELECT themanager.id, theclients.*
    FROM themanager, (VALUES
        (nextval('hibernate_sequence'), 'Aperture Science Laboratories'),
        (nextval('hibernate_sequence'), 'ACME Corp.')
      ) AS theclients;

WITH themanager AS (
  INSERT INTO businessmanager(id, name, email, phone) VALUES
  (nextval('hibernate_sequence'), 'Jim Halpert', 'jhalpert@dundermifflin.net', '+1-202-555-0152')
  RETURNING id
)
INSERT INTO client(assignedmanager_id, id, name)
    SELECT themanager.id, theclients.*
    FROM themanager, (VALUES
        (nextval('hibernate_sequence'), 'Oscorp')
      ) AS theclients;


WITH themanager AS (
  INSERT INTO businessmanager(id, name, email, phone) VALUES
  (nextval('hibernate_sequence'), 'Phyllis Lapin', 'plapin@dundermifflin.net', '+1-202-555-0153')
  RETURNING id
)
INSERT INTO client(assignedmanager_id, id, name)
    SELECT themanager.id, theclients.*
    FROM themanager, (VALUES
        (nextval('hibernate_sequence'), 'Stark Industries'),
        (nextval('hibernate_sequence'), 'Parker Industries')
      ) AS theclients;
