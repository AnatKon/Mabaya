DROP TABLE IF EXISTS mabaya.product_campaign;

DROP TABLE IF EXISTS mabaya.product;
CREATE TABLE mabaya.product (
    serial_number INT NOT NULL,
    title VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price FLOAT NOT NULL,

    PRIMARY KEY (serial_number));

DROP TABLE IF EXISTS mabaya.campaign;

CREATE TABLE mabaya.campaign (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    bid FLOAT NOT NULL,
    start_time TIMESTAMP NOT NULL,

    PRIMARY KEY (id));

CREATE TABLE mabaya.product_campaign (
    product_serial_number INT NOT NULL,
    campaign_id  INT NOT NULL,

    FOREIGN KEY (product_serial_number) REFERENCES product(serial_number),
    FOREIGN KEY (campaign_id) REFERENCES campaign(id));


insert into mabaya.product values(0, 'iPhone', 'Phones', 22.1);
insert into mabaya.product values(1, 'Samsung', 'Phones', 11.5);
insert into mabaya.product values(2, 'Pixel', 'Phones', 23.2);
insert into mabaya.product values(3, 'Funko Pop', 'Toys', 1.9);
insert into mabaya.product values(4, 'Barbie', 'Toys', 3.5);
insert into mabaya.product values(5, 'Mario Doll', 'Toys', 5.3);
insert into mabaya.product values(6, 'Dress', 'Cloths', 77.9);
insert into mabaya.product values(7, 'Pants', 'Cloths', 2.1);
insert into mabaya.product values(8, 'Shirt', 'Cloths', 36.4);
insert into mabaya.product values(9, 'Piano', 'Musical instrument', 100.0);