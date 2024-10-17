-- create_customer_table.sql
CREATE TABLE IF NOT EXISTS data_reading (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    kind_of_meter VARCHAR(10),
    comment VARCHAR(255),
    meter_id VARCHAR(20),
    meter_count DOUBLE,
    substitute BOOLEAN,
    date_of_reading DATE,
    uui_id VARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
);
