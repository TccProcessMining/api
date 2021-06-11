CREATE TABLE project (
    id varchar(255) NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    created_date TIMESTAMP DEFAULT now() NOT NULL,
    modified_date TIMESTAMP DEFAULT now() NOT NULL
);