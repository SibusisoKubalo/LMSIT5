-- Quick fix for the customers table to match the backend Customer entity
-- This adds the missing customer_id column and restructures the table

-- First, backup any existing data
CREATE TABLE IF NOT EXISTS customers_backup AS SELECT * FROM customers;

-- Drop the existing customers table
DROP TABLE IF EXISTS customers;

-- Recreate the customers table with the correct structure to match Customer.java
CREATE TABLE customers (
  customer_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL DEFAULT 'CUSTOMER',
  PRIMARY KEY (customer_id),
  INDEX idx_username (username)
);

-- Insert back any data from backup (if exists)
INSERT IGNORE INTO customers (username, password, role)
SELECT username, password, 'CUSTOMER'
FROM customers_backup
WHERE EXISTS (SELECT 1 FROM customers_backup LIMIT 1);

-- Add some sample customers for testing
INSERT INTO customers (username, password, role) VALUES
('john_doe', '$2a$10$dummy.hashed.password', 'CUSTOMER'),
('jane_smith', '$2a$10$dummy.hashed.password', 'CUSTOMER'),
('test_user', '$2a$10$dummy.hashed.password', 'CUSTOMER');

-- Clean up backup table
DROP TABLE IF EXISTS customers_backup;
