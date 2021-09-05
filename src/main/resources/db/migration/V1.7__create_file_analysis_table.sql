CREATE TABLE file_analysis (
    id varchar(255) NOT NULL PRIMARY KEY,
    file_id varchar(255),
    project_id varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    analysis JSONB NOT NULL,
    created_date TIMESTAMP DEFAULT now(),
    modified_date TIMESTAMP DEFAULT now()
);

ALTER TABLE file_analysis
ADD CONSTRAINT file_analysis_project_id_fk
FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE SET NULL;