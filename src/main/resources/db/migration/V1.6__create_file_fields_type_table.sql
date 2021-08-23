CREATE TABLE file_fields_type (
    id varchar(255) NOT NULL PRIMARY KEY,
    file_id varchar(255) NOT NULL,
    project_id varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    field varchar(255) NOT NULL,
    created_date TIMESTAMP DEFAULT now() NOT NULL,
    modified_date TIMESTAMP DEFAULT now() NOT NULL
);

ALTER TABLE file_fields_type
ADD CONSTRAINT file_fields_type_project_id_fk
FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL;