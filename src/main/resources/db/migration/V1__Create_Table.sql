-- Create Table
CREATE TABLE IF NOT EXISTS spaceship  (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Insert Data
INSERT INTO spaceship  (name) VALUES ('Falcon 9');
INSERT INTO spaceship  (name) VALUES ('Enterprise');
INSERT INTO spaceship  (name) VALUES ('Falcon-X');
INSERT INTO spaceship  (name) VALUES ('Enterprise-X');

INSERT INTO spaceship  (name) VALUES ('Falcon West');
INSERT INTO spaceship  (name) VALUES ('Enterprise West');
INSERT INTO spaceship  (name) VALUES ('Falcon-East');
INSERT INTO spaceship  (name) VALUES ('Enterprise-Y');

INSERT INTO spaceship  (name) VALUES ('Falcon 8');
INSERT INTO spaceship  (name) VALUES ('EnterpriseXX');
INSERT INTO spaceship  (name) VALUES ('Falcon-XY');
INSERT INTO spaceship  (name) VALUES ('Enterprise-XZ');

