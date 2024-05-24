create table t_users
(
    id          SERIAL NOT NULL PRIMARY KEY,
    login       TEXT    NOT NULL,
    email       TEXT    NOT NULL,
    name        TEXT    NOT NULL,
    surname     TEXT    NOT NULL
);

create table t_postboxes
(
    id      SERIAL  not null PRIMARY KEY,
    name    TEXT    not null,
    address TEXT    not null,
    notes   TEXT    not null
);

create table t_users_postboxes
(
    user_id         INT NOT NULL
        constraint fk_user_id_postbox
            references t_postboxes (id),
    postbox_id      INT
        CONSTRAINT fk_postbox_id_postbox
            REFERENCES t_postboxes(id),

    PRIMARY KEY (user_id, postbox_id)
);

create table t_door_states
(
    id         serial primary key,
    postbox_id int                   not null
        constraint fk_postbox_id_postbox
            references t_postboxes (id),
    timestamp  DATE                  not null,
    is_open    BOOLEAN default false not null
);

create table t_lid_states
(
    id         serial primary key,
    postbox_id int                   not null
        constraint fk_postbox_id_postbox
            references t_postboxes (id),
    timestamp  DATE                  not null,
    is_open    BOOLEAN default false not null
);

create table t_battery_states
(
    id         serial primary key,
    postbox_id int                   not null
        constraint fk_postbox_id_postbox
            references t_postboxes (id),
    timestamp  DATE                  not null,
    charge    int not null
);

create table t_weight_sensor_state
(
    id         serial primary key,
    postbox_id int                   not null
        constraint fk_postbox_id_postbox
            references t_postboxes (id),
    timestamp  DATE                  not null,
    weight    NUMERIC not null
);