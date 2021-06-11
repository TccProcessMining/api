CREATE TABLE log_project (
    log_id varchar(255) NOT NULL PRIMARY KEY,
    project_id varchar(255) NOT NULL,
    created_date TIMESTAMP DEFAULT now() NOT NULL,
    modified_date TIMESTAMP DEFAULT now() NOT NULL
);

ALTER TABLE log_project
ADD CONSTRAINT log_project_project_id_fk
FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE SET NULL;

ALTER TABLE log_project
ADD CONSTRAINT log_project_log_id_fk
FOREIGN KEY (log_id) REFERENCES log(id) ON DELETE CASCADE;