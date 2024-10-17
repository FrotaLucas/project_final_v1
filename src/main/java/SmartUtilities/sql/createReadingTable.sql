-- create_customer_table.sql
CREATE TABLE IF NOT EXISTS reading (
    id INT PRIMARY KEY AUTO_INCREMENT,
    comment VARCHAR(50),
    lastName VARCHAR(50),
    birthDate DATE,
    gender VARCHAR(10),
    uui VARCHAR(255)
);
