-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library_db
-- ------------------------------------------------------
-- Server version	8.0.41

-- Ayrton Williams 220086168

-------------------------------------------------------------------------------------------------
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `book_id` int NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `library_database_id` int DEFAULT NULL,
  PRIMARY KEY (`book_id`),
);

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
UNLOCK TABLES;

---------------------------------------------------------------------------------------------------
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
  `bookId` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `total_copies` INT NOT NULL DEFAULT 1,
  `available_copies` INT NOT NULL DEFAULT 1,
  `location` varchar(255) NOT NULL ,
  `status` ENUM('AVAILABLE','ON_LOAN') DEFAULT 'AVAILABLE';

PRIMARY KEY (`bookId`)
);

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
UNLOCK TABLES;



-------------------------------------------------------------------------------------------------
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
);

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
UNLOCK TABLES;

------------------------------------------------------------------------------------------------
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
);

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
UNLOCK TABLES;

------------------------------------------------------------------------------------------------
-- Table structure for table `librarian`
--

DROP TABLE IF EXISTS `librarian`;

CREATE TABLE `librarian` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

--
-- Dumping data for table `librarian`
--

LOCK TABLES `librarian` WRITE;
UNLOCK TABLES;

------------------------------------------------------------------------------------------------
-- Table structure for table `librarians`
--

DROP TABLE IF EXISTS `librarians`;

CREATE TABLE `librarians` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

--
-- Dumping data for table `librarians`
--

LOCK TABLES `librarians` WRITE;
UNLOCK TABLES;

------------------------------------------------------------------------------------------------
-- Table structure for table `libraries`
--

DROP TABLE IF EXISTS `libraries`;

CREATE TABLE `libraries` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `num` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

--
-- Dumping data for table `libraries`
--

LOCK TABLES `libraries` WRITE;
UNLOCK TABLES;

------------------------------------------------------------------------------------------------
-- Table structure for table `library`
--

DROP TABLE IF EXISTS `library`;

CREATE TABLE `library` (
  `id` int NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `num` int NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
UNLOCK TABLES;

--------------------------------------------------------------------------------------------------
-- Table structure for table `library_database`
--

DROP TABLE IF EXISTS `library_database`;

CREATE TABLE `library_database` (
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
);

--
-- Dumping data for table `library_database`
--

LOCK TABLES `library_database` WRITE;
UNLOCK TABLES;

--------------------------------------------------------------------------------------------------
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `date_created` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`notification_id`)
);

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
UNLOCK TABLES;

--------------------------------------------------------------------------------------------------
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;

CREATE TABLE `notifications` (
  `notificationId` int NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`notificationId`)
);

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
UNLOCK TABLES;


--------------------------------------------------------------------------------------------------
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;

-- Updated users table to match Java User entity
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(50) NOT NULL DEFAULT 'CUSTOMER',
  `staff_number` VARCHAR(100) UNIQUE,
  PRIMARY KEY (`id`),
  INDEX `idx_email` (`email`),
  INDEX `idx_staff_number` (`staff_number`)
);

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
-- Insert default admin user
INSERT INTO `users` (`name`, `surname`, `email`, `password`, `role`, `staff_number`)
VALUES ('Admin', 'User', 'admin@library.com', '$2a$10$dummy.hashed.password', 'LIBRARIAN', 'ADMIN001');
UNLOCK TABLES;

-------------------------------------------------------------------------------------------------
-- Table structure for table `borrow_transaction`
--

DROP TABLE IF EXISTS `borrow_transaction`;

CREATE TABLE borrow_transaction (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `book_id` INT NOT NULL,
    `customer_id` INT NOT NULL,
    `borrow_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `due_date` DATETIME NOT NULL,
    `return_date` DATETIME,
    `fine` DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (book_id) REFERENCES book(book_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

--
-- Dumping data for table `borrow_transaction`
--

LOCK TABLES `borrow_transaction` WRITE;
UNLOCK TABLES;

--------------------------------------------------------------------------------------------------
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;

CREATE TABLE reservation (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `book_id` INT NOT NULL,
    `customer_id` INT NOT NULL,
    `reservation_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `notification_sent` BOOLEAN DEFAULT FALSE,
    `fulfilled` BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (book_id) REFERENCES book(book_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);


--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
UNLOCK TABLES;


-- Dump completed on 2025-09-04 16:07:21
