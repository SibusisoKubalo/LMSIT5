-- CRITICAL FIX: Run this SQL script to fix the customers table structure
-- This resolves the "Unknown column 'c1_0.customer_id'" error

USE library_db;

-- Step 1: Backup existing customers data
CREATE TABLE IF NOT EXISTS customers_temp AS SELECT * FROM customers;

-- Step 2: Drop and recreate customers table with correct structure
DROP TABLE customers;
CREATE TABLE customers (
  customer_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL DEFAULT 'CUSTOMER',
  PRIMARY KEY (customer_id),
  INDEX idx_username (username)
);

-- Step 3: Restore data (if any existed)
INSERT IGNORE INTO customers (username, password, role)
SELECT username, COALESCE(password, 'temp'), 'CUSTOMER' FROM customers_temp;

-- Step 4: Add test customers
INSERT IGNORE INTO customers (username, password, role) VALUES
('john_doe', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER'),
('jane_smith', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER'),
('test_user', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER');

-- Step 5: Verify the fix
SELECT 'Fixed customers table:' as status;
DESCRIBE customers;
SELECT * FROM customers LIMIT 5;

-- Clean up
DROP TABLE IF EXISTS customers_temp;
