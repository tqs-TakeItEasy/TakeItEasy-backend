-- COMPANY

INSERT INTO company (name, email) VALUES ('TakeItEasy', 'takeiteasy@gmail.com');

-- STORE

INSERT INTO store (name, email, company_id) VALUES ('Store Play', 'storeplay@example.com', 1);
INSERT INTO store (name, email, company_id) VALUES ('Quick Buy', 'quickbuy@example.com', 1);
INSERT INTO store (name, email, company_id) VALUES ('Fresh Grocers', 'freshgrocers@example.com', 1);
INSERT INTO store (name, email, company_id) VALUES ('Discount Deals', 'discountdeals@example.com', 1);
INSERT INTO store (name, email, company_id) VALUES ('Convenience Corner', 'conveniencecorner@example.com', 1);

-- PICKUP POINT

INSERT INTO pickup_point (name, address, email, status, company_id) VALUES ('Coffee Maria', 'St. Santiago OPORTO', 'donamaria@example.com', 'AVAILABLE' , 1);

INSERT INTO pickup_point (name, address, email, status, company_id) VALUES ('Java Junction', '123 Main Street, Anytown', 'javajunction@example.com', 'AVAILABLE', 1);

INSERT INTO pickup_point (name, address, email, status, company_id) VALUES ('Espresso Express', '456 Elm Avenue, Cityville', 'espressoexpress@example.com', 'AVAILABLE', 1);

INSERT INTO pickup_point (name, address, email, status, company_id) VALUES ('Caffeine Corner', '789 Oak Drive, Townsville', 'caffeinecorner@example.com', 'AVAILABLE', 1);

INSERT INTO pickup_point (name, address, email, status, company_id) VALUES ('Mocha Mart', '321 Pine Lane, Villageton', 'mochamart@example.com', 'AVAILABLE', 1);

INSERT INTO pickup_point (name, address, email, status, company_id) VALUES ('Bean Bazaar', '654 Walnut Road, Boroughville', 'beanbazaar@example.com', 'AVAILABLE', 1);

-- DELIVERIES

INSERT INTO delivery (userN, userE, packageI, pickup_point_id, store_id, status, registeryD, deliveryD, pickupD) VALUES ('Anthony Conho', 'anthonytheconho@gmail.com', 1, 1, 1, 'DISPATCHED', '2023-05-29', NULL, NULL);
INSERT INTO delivery (userN, userE, packageI, pickup_point_id, store_id, status, registeryD, deliveryD, pickupD) VALUES ('John Doe', 'johndoe@example.com', 2, 2, 1, 'DISPATCHED', '2023-05-30', NULL, NULL);
INSERT INTO delivery (userN, userE, packageI, pickup_point_id, store_id, status, registeryD, deliveryD, pickupD) VALUES ('Jane Smith', 'janesmith@example.com', 3, 1, 1, 'RECIEVED', '2023-05-29', '2023-05-31', NULL);
INSERT INTO delivery (userN, userE, packageI, pickup_point_id, store_id, status, registeryD, deliveryD, pickupD) VALUES ('Mike Johnson', 'mikejohnson@example.com', 4, 2, 1, 'DISPATCHED', '2023-05-31', NULL, NULL);
INSERT INTO delivery (userN, userE, packageI, pickup_point_id, store_id, status, registeryD, deliveryD, pickupD) VALUES ('Emily Brown', 'emilybrown@example.com', 5, 3, 1, 'DISPATCHED', '2023-05-30', NULL, NULL);
INSERT INTO delivery (userN, userE, packageI, pickup_point_id, store_id, status, registeryD, deliveryD, pickupD) VALUES ('David Wilson', 'davidwilson@example.com', 6, 1, 1, 'DISPATCHED', '2023-05-31', NULL, NULL);

-- ACP

INSERT INTO acp (name, email, pwd, company_id, pickup_point_id) VALUES ('Maria Rita Esteves', 'aritinha@gmail.com', 'pwddarita', 1, 1);
INSERT INTO acp (name, email, pwd, company_id, pickup_point_id) VALUES ('Kim Kardashian', 'kimkardi@gmail.com', 'pwddakim', 1, 1);
INSERT INTO acp (name, email, pwd, company_id, pickup_point_id) VALUES ('Emma Thompson', 'emma@example.com', 'pdwword123', 1, 1);
INSERT INTO acp (name, email, pwd, company_id, pickup_point_id) VALUES ('Alex Turner', 'alex@example.com', 'secepwd456', 1, 2);
INSERT INTO acp (name, email, pwd, company_id, pickup_point_id) VALUES ('Olivia Harris', 'olivia@example.com', 'pa1234', 1, 3);
INSERT INTO acp (name, email, pwd, company_id, pickup_point_id) VALUES ('Noah Roberts', 'noah@example.com', 'pdwword789', 1, 1);
INSERT INTO acp (name, email, pwd, company_id, pickup_point_id) VALUES ('Sophia Clark', 'sophia@example.com', 'secepdwword', 1, 2);

-- ADMIN

INSERT INTO admin (name, email, pwd, company_id) VALUES ('Miguel Manco', 'miguelamatos@ua.pt', 'mancosmico', 1);
