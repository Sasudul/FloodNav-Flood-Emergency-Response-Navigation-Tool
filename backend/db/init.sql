CREATE DATABASE floodnav;
USE floodnav;

CREATE TABLE clusters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    center_latitude DOUBLE NOT NULL,
    center_longitude DOUBLE NOT NULL,
    total_affected_people INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sos_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    affected_people INT NOT NULL,
    severity_level VARCHAR(20) NOT NULL,
    notes VARCHAR(255),
    priority_score INT NOT NULL,
    reported_at DATETIME NOT NULL,
    cluster_id BIGINT,
    CONSTRAINT fk_sos_cluster
        FOREIGN KEY (cluster_id)
        REFERENCES clusters(id)
        ON DELETE SET NULL
);

CREATE TABLE road_blocks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_node VARCHAR(100) NOT NULL,
    end_node VARCHAR(100) NOT NULL,
    blocked BOOLEAN NOT NULL,
    reason VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE rescue_bases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    available_teams INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE severity_levels (
    level VARCHAR(20) PRIMARY KEY,
    weight INT NOT NULL
);

INSERT INTO severity_levels VALUES
('LOW', 1),
('MEDIUM', 2),
('HIGH', 3),
('CRITICAL', 4);

INSERT INTO rescue_bases (name, latitude, longitude, available_teams) 
VALUES ('Colombo Central Base', 6.9147, 79.8776, 5); 