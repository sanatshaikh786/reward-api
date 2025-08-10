
CREATE TABLE transactionss (
                              id VARCHAR(255) PRIMARY KEY,
                              customer_id VARCHAR(255),
                              date DATE,
                              amount DECIMAL
);

INSERT INTO transactionss (id, customer_id, date, amount) VALUES ('T1', 'C1', '2025-05-10', 120);
INSERT INTO transactionss (id, customer_id, date, amount) VALUES ('T2', 'C1', '2025-04-15', 75);
INSERT INTO transactionss (id, customer_id, date, amount) VALUES ('T3', 'C2', '2025-06-20', 200);
INSERT INTO transactionss (id, customer_id, date, amount) VALUES ('T4', 'C1', '2025-07-01', 50);
