INSERT INTO author(id, firstname, lastname) VALUES (1, 'John', 'Irving');
INSERT INTO author(id, firstname, lastname) VALUES (2, 'Paul', 'Auster');
INSERT INTO author(id, firstname, lastname) VALUES (3, 'Daniel', 'Abraham');
INSERT INTO author(id, firstname, lastname) VALUES (4, 'Ty', 'Frank');
INSERT INTO author(id, firstname, lastname) VALUES (5, 'Isaac', 'Asimov');
ALTER SEQUENCE author_seq RESTART WITH 6;

INSERT INTO book(id, title, pageCount) VALUES (1, 'The World According to Garp', 610);
INSERT INTO book_genres(book_id, genres)
  SELECT 1, genres.*
  FROM (VALUES ('Fiction'), ('Contemporary'), ('Humor')) genres;
INSERT INTO book(id, title, pageCount) VALUES (2, 'The Hotel New Hampshire', 520);
INSERT INTO book_genres(book_id, genres)
  SELECT 2, genres.*
  FROM (VALUES ('Fiction')) genres;
INSERT INTO book(id, title, pageCount) VALUES (3, 'The Cider House Rules', 1064);
INSERT INTO book_genres(book_id, genres)
  SELECT 3, genres.*
  FROM (VALUES ('Fiction'), ('Contemporary')) genres;
INSERT INTO book(id, title, pageCount) VALUES (4, 'A Prayer for Owen Meany', 637);
INSERT INTO book_genres(book_id, genres)
  SELECT 4, genres.*
  FROM (VALUES ('Fiction'), ('Historical Fiction')) genres;
INSERT INTO book(id, title, pageCount) VALUES (5, 'Last Night in Twisted River', 554);
INSERT INTO book_genres(book_id, genres)
  SELECT 5, genres.*
  FROM (VALUES ('Fiction'), ('Contemporary')) genres;
INSERT INTO book(id, title, pageCount) VALUES (6, 'In One Person', 425);
INSERT INTO book_genres(book_id, genres)
  SELECT 6, genres.*
  FROM (VALUES ('Fiction'), ('Contemporary'), ('Queer')) genres;
INSERT INTO book(id, title, pageCount) VALUES (7, 'Avenue of Mysteries', 460);
INSERT INTO book_genres(book_id, genres)
  SELECT 7, genres.*
  FROM (VALUES ('Fiction'), ('Contemporary'), ('Religion')) genres;
INSERT INTO book(id, title, pageCount) VALUES (8, 'The New York Trilogy', 308);
INSERT INTO book_genres(book_id, genres)
  SELECT 8, genres.*
  FROM (VALUES ('Fiction'), ('Contemporary'), ('Crime')) genres;
INSERT INTO book(id, title, pageCount) VALUES (9, 'Mr. Vertigo', 318);
INSERT INTO book_genres(book_id, genres)
  SELECT 9, genres.*
  FROM (VALUES ('Fiction'), ('Fantasy')) genres;
INSERT INTO book(id, title, pageCount) VALUES (10, 'The Brooklyn Follies', 306);
INSERT INTO book_genres(book_id, genres)
  SELECT 10, genres.*
  FROM (VALUES ('Fiction'), ('Contemporary')) genres;
INSERT INTO book(id, title, pageCount) VALUES (11, 'Invisible', 308);
INSERT INTO book_genres(book_id, genres)
  SELECT 11, genres.*
  FROM (VALUES ('Fiction'), ('Contemporary')) genres;
INSERT INTO book(id, title, pageCount) VALUES (12, 'Sunset Park', 309);
INSERT INTO book_genres(book_id, genres)
  SELECT 12, genres.*
  FROM (VALUES ('Fiction'), ('Contemporary')) genres;
INSERT INTO book(id, title, pageCount) VALUES (13, '4 3 2 1', 866);
INSERT INTO book_genres(book_id, genres)
  SELECT 13, genres.*
  FROM (VALUES ('Fiction'), ('Historical Fiction')) genres;
INSERT INTO book(id, title, pageCount) VALUES (14, 'Leviathan Wakes', 592);
INSERT INTO book_genres(book_id, genres)
  SELECT 14, genres.*
  FROM (VALUES ('Fiction'), ('Science Fiction'), ('Space Opera')) genres;
INSERT INTO book(id, title, pageCount) VALUES (15, 'I, Robot', 224);
INSERT INTO book_genres(book_id, genres)
  SELECT 15, genres.*
  FROM (VALUES ('Fiction'), ('Science Fiction')) genres;
INSERT INTO book(id, title, pageCount) VALUES (16, 'The Caves of Steel', 206);
INSERT INTO book_genres(book_id, genres)
  SELECT 16, genres.*
  FROM (VALUES ('Fiction'), ('Science Fiction'), ('Crime')) genres;
INSERT INTO book(id, title, pageCount) VALUES (17, 'The Robots of Dawn', 438);
INSERT INTO book_genres(book_id, genres)
  SELECT 17, genres.*
  FROM (VALUES ('Fiction'), ('Science Fiction'), ('Crime')) genres;
ALTER SEQUENCE book_seq RESTART WITH 18;

INSERT into book_author (books_id, authors_id)
  SELECT book_ids.*, 1
  FROM (VALUES (1), (2), (3), (4), (5), (6), (7)) book_ids;
INSERT into book_author (books_id, authors_id)
  SELECT book_ids.*, 2
  FROM (VALUES (8), (9), (10), (11), (12), (13)) book_ids;
INSERT into book_author (books_id, authors_id)
  SELECT book_ids.*, author_ids.*
  FROM (VALUES (14)) book_ids,
       (VALUES(3), (4)) author_ids;
INSERT into book_author (books_id, authors_id)
  SELECT book_ids.*, 5
  FROM (VALUES (15), (16), (17)) book_ids;