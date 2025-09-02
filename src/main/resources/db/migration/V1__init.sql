CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE tasks (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255),
    description TEXT,
    priority    VARCHAR(50),
    due_date    TIMESTAMP,
    status      VARCHAR(50),
    assignee_id BIGINT,

    CONSTRAINT fk_assignee_id FOREIGN KEY (assignee_id) REFERENCES users(id)
);