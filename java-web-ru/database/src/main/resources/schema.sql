-- BEGIN
DROP TABLE IF EXISTS products;

CREATE TABLE products(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title TEXT,
    price INT
);
-- END
