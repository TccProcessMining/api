CREATE TABLE file_fields_type (
    id varchar(255) NOT NULL PRIMARY KEY,
    file_id varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    field varchar(255) NOT NULL,
    created_date TIMESTAMP DEFAULT now() NOT NULL,
    modified_date TIMESTAMP DEFAULT now() NOT NULL
);