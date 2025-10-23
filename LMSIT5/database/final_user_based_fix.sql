-- FINAL DATABASE FIX: User-Based Borrowing System
-- This creates the correct schema for the updated backend entities

USE library_db;

-- Drop old tables that might cause conflicts
DROP TABLE IF EXISTS borrow_transaction;
DROP TABLE IF EXISTS customers;

-- Ensure users table has the username field (critical fix)
ALTER TABLE users ADD COLUMN IF NOT EXISTS username VARCHAR(255) UNIQUE AFTER surname;

-- Update users table to ensure all required fields exist
UPDATE users SET username = SUBSTRING_INDEX(email, '@', 1) WHERE username IS NULL OR username = '';

-- Create the book table with all required fields
DROP TABLE IF EXISTS book;
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

-- Create borrow_transaction table using user_id (not customer_id)
CREATE TABLE borrow_transaction (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  book_id INT NOT NULL,
  borrow_date DATE NOT NULL,
  due_date DATE NOT NULL,
  return_date DATE NULL,
  fine DECIMAL(10,2) DEFAULT 0.00,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE,
  INDEX idx_user (user_id),
  INDEX idx_book (book_id)
);

-- Insert sample books for testing
INSERT INTO book (title, author, subject, genre, total_copies, available_copies, location, status, price) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Literature', 'Fiction', 5, 5, 'A-001', 'AVAILABLE', 25.99),
('To Kill a Mockingbird', 'Harper Lee', 'Literature', 'Fiction', 3, 3, 'A-002', 'AVAILABLE', 22.50),
('1984', 'George Orwell', 'Literature', 'Dystopian Fiction', 4, 4, 'B-001', 'AVAILABLE', 24.99),
('Pride and Prejudice', 'Jane Austen', 'Literature', 'Romance', 2, 2, 'A-003', 'AVAILABLE', 20.99),
('Java Programming', 'Oracle Press', 'Computer Science', 'Programming', 3, 3, 'C-001', 'AVAILABLE', 45.99),
('Spring Boot Guide', 'Tech Publisher', 'Computer Science', 'Programming', 2, 2, 'C-002', 'AVAILABLE', 55.99);

-- Ensure we have test users with usernames
INSERT IGNORE INTO users (name, surname, username, email, password, role, staff_number) VALUES
('John', 'Doe', 'john_doe', 'john.doe@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER', NULL),
('Jane', 'Smith', 'jane_smith', 'jane.smith@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER', NULL),
('Test', 'User', 'test_user', 'test@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER', NULL);

-- Verify the fix
SELECT 'Database structure verification:' as status;
DESCRIBE users;
DESCRIBE book;
DESCRIBE borrow_transaction;

SELECT 'Sample data verification:' as status;
SELECT COUNT(*) as user_count FROM users;
SELECT COUNT(*) as book_count FROM book;
