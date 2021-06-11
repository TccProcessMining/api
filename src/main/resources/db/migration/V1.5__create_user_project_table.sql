CREATE TABLE user_project (
    project_id varchar(255) NOT NULL PRIMARY KEY,
    user_id varchar(255) NOT NULL,
    created_date TIMESTAMP DEFAULT now() NOT NULL,
    modified_date TIMESTAMP DEFAULT now() NOT NULL
);

ALTER TABLE user_project
ADD CONSTRAINT user_project_project_id_fk
FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE SET NULL;

ALTER TABLE user_project
ADD CONSTRAINT user_project_user_id_fk
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;