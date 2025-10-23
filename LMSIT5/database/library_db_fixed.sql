-- Updated Library Management System Database Schema
-- This schema matches the backend Spring Boot entities

-- Drop existing tables to avoid conflicts
DROP TABLE IF EXISTS `borrow_transaction`;
DROP TABLE IF EXISTS `books`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `customer`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `librarians`;
DROP TABLE IF EXISTS `librarian`;
DROP TABLE IF EXISTS `notifications`;
DROP TABLE IF EXISTS `notification`;
DROP TABLE IF EXISTS `libraries`;
DROP TABLE IF EXISTS `library`;
DROP TABLE IF EXISTS `library_database`;
DROP TABLE IF EXISTS `reservation`;

-- Create users table (matches User.java entity)
CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255) NOT NULL UNIQUE,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(50) NOT NULL DEFAULT 'CUSTOMER',
  `staff_number` VARCHAR(100) UNIQUE,
  PRIMARY KEY (`id`),
  INDEX `idx_email` (`email`),
  INDEX `idx_username` (`username`),
  INDEX `idx_staff_number` (`staff_number`)
);

-- Create book table (matches Book.java entity)
CREATE TABLE `book` (
  `book_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `subject` VARCHAR(255),
  `author` VARCHAR(255),
  `genre` VARCHAR(255),
  `total_copies` INT NOT NULL DEFAULT 1,
  `available_copies` INT NOT NULL DEFAULT 1,
  `location` VARCHAR(255),
  `status` VARCHAR(50) DEFAULT 'AVAILABLE',
  `image_url` VARCHAR(500),
  `pdf_url` VARCHAR(500),
  `price` DECIMAL(10,2) DEFAULT 0.00,
  PRIMARY KEY (`book_id`)
);

-- Create customers table (matches Customer.java entity)
CREATE TABLE `customers` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(50) NOT NULL DEFAULT 'CUSTOMER',
  PRIMARY KEY (`customer_id`)
);

-- Create borrow_transaction table (matches BorrowTransaction.java entity)
CREATE TABLE `borrow_transaction` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  `borrow_date` DATE NOT NULL,
  `due_date` DATE NOT NULL,
  `return_date` DATE,
  `fine` DECIMAL(10,2) DEFAULT 0.00,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`customer_id`) REFERENCES `customers`(`customer_id`) ON DELETE CASCADE,
  FOREIGN KEY (`book_id`) REFERENCES `book`(`book_id`) ON DELETE CASCADE
);

-- Create notification table
CREATE TABLE `notification` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(1000),
  `date_created` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notification_id`)
);

-- Insert sample data
INSERT INTO `users` (`name`, `surname`, `username`, `email`, `password`, `role`, `staff_number`)
VALUES
('Admin', 'User', 'admin', 'admin@library.com', '$2a$10$dummy.hashed.password', 'LIBRARIAN', 'LIB001'),
('John', 'Doe', 'john', 'john.doe@email.com', '$2a$10$dummy.hashed.password', 'CUSTOMER', NULL);

INSERT INTO `customers` (`username`, `password`, `role`)
VALUES
('john_customer', 'password123', 'CUSTOMER'),
('jane_customer', 'password456', 'CUSTOMER');

INSERT INTO `book` (`title`, `author`, `subject`, `genre`, `total_copies`, `available_copies`, `location`, `status`, `price`)
VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Literature', 'Fiction', 5, 5, 'A-001', 'AVAILABLE', 25.99),
('To Kill a Mockingbird', 'Harper Lee', 'Literature', 'Fiction', 3, 2, 'A-002', 'AVAILABLE', 22.50),
('1984', 'George Orwell', 'Literature', 'Dystopian Fiction', 4, 3, 'B-001', 'AVAILABLE', 24.99),
('Pride and Prejudice', 'Jane Austen', 'Literature', 'Romance', 2, 1, 'A-003', 'AVAILABLE', 20.99);

INSERT INTO `notification` (`content`)
VALUES
('Welcome to the Library Management System!'),
('New books have been added to the collection.'),
('Library hours have been extended for exam period.');
