DROP TABLE IF EXISTS categories CASCADE; -- для тестов/многочисленных запусков
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS borrowings CASCADE;
DROP TABLE IF EXISTS readers CASCADE;

CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    author VARCHAR(100) NOT NULL,
    category_id INT REFERENCES categories(id) ON DELETE SET NULL,
    title VARCHAR(100) NOT NULL UNIQUE,
    publication_year INT NOT NULL
);

CREATE TABLE IF NOT EXISTS readers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS borrowings (
    id SERIAL PRIMARY KEY,
    reader_id INT REFERENCES readers(id) ON DELETE SET NULL,
    book_id INT REFERENCES books(id) ON DELETE SET NULL,
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    borrowed BOOLEAN NOT NULL
);

DELETE FROM books; -- для тестов/многочисленных запусков
DELETE FROM readers;
DELETE FROM categories;


INSERT INTO categories(name) VALUES
    ('Horror'),
    ('Thriller'),
    ('Mystery'),
    ('Romance novel'),
    ('Adventure'),
    ('Children Literature'),
    ('Documental'),
    ('Biographies'),
    ('Classics');


INSERT INTO readers (first_name, last_name, phone_number, email) VALUES
    ('Андрей', 'Скуратов', '+79806661281', 'andrewskrtw@gmail.com'),
    ('Алексей', 'Попов', '+79993239090', 'alexpopov@mail.ru');

INSERT INTO books (author, category_id, title, publication_year) VALUES
    ('Steven King', 2, 'Woman with a knife', 2004),
    ('Steve Jobs', 8, 'Life of Steve Jobs', 2010);