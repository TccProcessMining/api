CREATE TABLE users (
    id varchar(7255) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    e_mail varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    created_date TIMESTAMP DEFAULT now() NOT NULL,
    modified_date TIMESTAMP DEFAULT now() NOT NULL
)