drop table if exists users cascade;
drop table if exists item_requests cascade;
drop table if exists items cascade;
drop table if exists bookings cascade;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255)                            NOT NULL,
    email VARCHAR(512)                            NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS item_requests
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description       VARCHAR(255),
    requestor_user_id BIGINT,
    created           TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_item_request PRIMARY KEY (id),
    CONSTRAINT fk_requestor FOREIGN KEY (requestor_user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS items
(
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                    VARCHAR(255),
    description             VARCHAR(255),
    available               BOOLEAN,
    owner_user_id           BIGINT,
    request_item_request_id BIGINT,
    CONSTRAINT pk_item PRIMARY KEY (id),
    CONSTRAINT fk_owner FOREIGN KEY (owner_user_id) REFERENCES users (id),
    CONSTRAINT fk_request FOREIGN KEY (request_item_request_id) REFERENCES item_requests (id)
);

CREATE TABLE IF NOT EXISTS bookings
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_time_booking TIMESTAMP WITHOUT TIME ZONE,
    end_time_booking   TIMESTAMP WITHOUT TIME ZONE,
    item_id            BIGINT,
    booker_user_id     BIGINT,
    status             INTEGER,
    CONSTRAINT pk_booking PRIMARY KEY (id),
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT fk_booker FOREIGN KEY (booker_user_id) REFERENCES users (id)
);