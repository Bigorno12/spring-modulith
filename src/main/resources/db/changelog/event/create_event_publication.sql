CREATE TABLE event_publication
(
    id                     UUID           NOT NULL,
    event_type             VARCHAR(255)   NOT NULL,
    listener_id            VARCHAR(255)   NOT NULL,
    serialized_event       VARCHAR(255)   NOT NULL,
    status                 VARCHAR(50)    NOT NULL,
    completion_attempts    INTEGER        NOT NULL,
    publication_date       TIMESTAMPTZ(6) NOT NULL,
    completion_date        TIMESTAMPTZ(6),
    last_resubmission_date TIMESTAMPTZ(6),

    PRIMARY KEY (id)
);