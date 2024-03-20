WITH themanager AS (
  INSERT INTO businessmanager(id, name, email, phone) VALUES
  (1, 'Dwight Schrute', 'dschrute@dundermifflin.net', '+1-202-555-0151')
  RETURNING id
)
INSERT INTO client(assignedmanager_id, id, name)
    SELECT themanager.id, theclients.*
    FROM themanager, (VALUES
        (1, 'Aperture Science Laboratories'),
        (2, 'ACME Corp.')
      ) AS theclients;

WITH themanager AS (
  INSERT INTO businessmanager(id, name, email, phone) VALUES
  (2, 'Jim Halpert', 'jhalpert@dundermifflin.net', '+1-202-555-0152')
  RETURNING id
)
INSERT INTO client(assignedmanager_id, id, name)
    SELECT themanager.id, theclients.*
    FROM themanager, (VALUES
        (3, 'Oscorp')
      ) AS theclients;

WITH themanager AS (
  INSERT INTO businessmanager(id, name, email, phone) VALUES
  (3, 'Phyllis Lapin', 'plapin@dundermifflin.net', '+1-202-555-0153')
  RETURNING id
)
INSERT INTO client(assignedmanager_id, id, name)
    SELECT themanager.id, theclients.*
    FROM themanager, (VALUES
        (4, 'Stark Industries'),
        (5, 'Parker Industries')
      ) AS theclients;

ALTER SEQUENCE businessmanager_seq RESTART WITH 4;
ALTER SEQUENCE client_seq RESTART WITH 6;
