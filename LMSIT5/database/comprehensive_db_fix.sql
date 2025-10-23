-- Comprehensive database fix for Library Management System
-- This fixes both customers and book table structure issues

USE library_db;

-- Fix 1: Customers table structure
-- Backup existing customers data
DROP TABLE IF EXISTS customers_backup;
CREATE TABLE customers_backup AS SELECT * FROM customers WHERE 1=1;

-- Drop and recreate customers table with correct structure
DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
  customer_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL DEFAULT 'CUSTOMER',
  PRIMARY KEY (customer_id),
  INDEX idx_username (username)
);

-- Restore data from backup
INSERT IGNORE INTO customers (username, password, role)
SELECT
  COALESCE(username, 'unknown') as username,
  COALESCE(password, 'temp') as password,
  'CUSTOMER' as role
FROM customers_backup;

-- Add sample customers for testing
INSERT IGNORE INTO customers (username, password, role) VALUES
('john_doe', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER'),
('jane_smith', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER'),
('test_user', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER');

-- Fix 2: Book table structure (ensure it matches Book.java entity)
-- Backup existing book data
DROP TABLE IF EXISTS book_backup;
CREATE TABLE book_backup AS SELECT * FROM book WHERE 1=1;

-- Also backup from books table if it exists
INSERT IGNORE INTO book_backup
SELECT
  bookId as book_id,
  title,
  subject,
  author,
  NULL as genre,
  total_copies,
  available_copies,
  location,
  status,
  NULL as image_url,
  NULL as pdf_url,
  0.0 as price
FROM books WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'books');

-- Drop existing book tables
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS books;

-- Create book table matching Book.java entity
CREATE TABLE book (
  book_id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  subject VARCHAR(255),
  author VARCHAR(255),
  genre VARCHAR(255),
  total_copies INT NOT NULL DEFAULT 1,
  available_copies INT NOT NULL DEFAULT 1,
  location VARCHAR(255),
  status VARCHAR(50) DEFAULT 'AVAILABLE',
  image_url VARCHAR(500),
  pdf_url VARCHAR(500),
  price DECIMAL(10,2) DEFAULT 0.00,
  PRIMARY KEY (book_id)
);

-- Restore book data
INSERT IGNORE INTO book (book_id, title, subject, author, genre, total_copies, available_copies, location, status, image_url, pdf_url, price)
SELECT
  book_id,
  COALESCE(title, 'Unknown Title') as title,
  subject,
  author,
  genre,
  COALESCE(total_copies, 1) as total_copies,
  COALESCE(available_copies, 1) as available_copies,
  location,
  COALESCE(status, 'AVAILABLE') as status,
  image_url,
  pdf_url,
  COALESCE(price, 0.0) as price
FROM book_backup;

-- Add sample books for testing
INSERT IGNORE INTO book (title, author, subject, genre, total_copies, available_copies, location, status, price) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Literature', 'Fiction', 5, 5, 'A-001', 'AVAILABLE', 25.99),
('To Kill a Mockingbird', 'Harper Lee', 'Literature', 'Fiction', 3, 3, 'A-002', 'AVAILABLE', 22.50),
('1984', 'George Orwell', 'Literature', 'Dystopian Fiction', 4, 4, 'B-001', 'AVAILABLE', 24.99),
('Pride and Prejudice', 'Jane Austen', 'Literature', 'Romance', 2, 2, 'A-003', 'AVAILABLE', 20.99),
('Java Programming', 'Oracle Press', 'Computer Science', 'Programming', 3, 3, 'C-001', 'AVAILABLE', 45.99);

-- Fix 3: Update/Create borrow_transaction table
DROP TABLE IF EXISTS borrow_transaction;
CREATE TABLE borrow_transaction (
  id BIGINT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  book_id INT NOT NULL,
  borrow_date DATE NOT NULL,
  due_date DATE NOT NULL,
  return_date DATE NULL,
  fine DECIMAL(10,2) DEFAULT 0.00,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
  FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE,
  INDEX idx_customer (customer_id),
  INDEX idx_book (book_id)
);

-- Clean up backup tables
DROP TABLE IF EXISTS customers_backup;
DROP TABLE IF EXISTS book_backup;

-- Verify the structure
SELECT 'Customers table structure:' as info;
DESCRIBE customers;

SELECT 'Book table structure:' as info;
DESCRIBE book;

SELECT 'Sample data verification:' as info;
SELECT COUNT(*) as customer_count FROM customers;
SELECT COUNT(*) as book_count FROM book;
