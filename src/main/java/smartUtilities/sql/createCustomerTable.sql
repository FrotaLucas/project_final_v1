-- create_customer_table.sql
CREATE TABLE IF NOT EXISTS customers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    birthDate DATE,
    gender VARCHAR(10),
    uui_id VARCHAR(255)
);


