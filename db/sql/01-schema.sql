DROP TABLE IF EXISTS product;

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
	price INT CHECK (price >= 0),
    enabled BOOLEAN DEFAULT TRUE
);