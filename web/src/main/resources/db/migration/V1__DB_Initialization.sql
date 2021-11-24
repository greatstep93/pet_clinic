create table if not exists role
(
    name varchar(25) not null,
    primary key (name)
);

create table if not exists user_entities
(
    user_type varchar(31)  not null,
    id        int8 generated by default as identity,
    avatar    varchar(255),
    email     varchar(255) not null,
    enabled   boolean,
    firstname varchar(255) not null,
    lastname  varchar(255) not null,
    password  varchar(255) not null,
    primary key (id),
    constraint uk_user_entities
        unique (email)
);

create table if not exists user_role
(
    role_name varchar(25),
    user_id   int8 not null,
    primary key (user_id),
    constraint fk_user_role_role
        foreign key (role_name)
            references role,
    constraint fk_user_role_user_entities
        foreign key (user_id)
            references user_entities
);

create table if not exists pet_entities
(
    pet_type    varchar(31)  not null,
    id          int8 generated by default as identity,
    avatar      varchar(255),
    birth_day   date         not null,
    breed       varchar(255) not null,
    color       varchar(255),
    description varchar(255),
    gender      varchar(255) not null,
    name        varchar(255) not null,
    pet_size    varchar(255),
    weight      float8,
    client_id   int8,
    primary key (id),
    constraint fk_pet_entities_user_entities
        foreign key (client_id)
            references user_entities
);

create table if not exists verification_token
(
    id      int8 not null,
    user_id int8 not null,
    primary key (id),
    constraint uk_verification_token
        unique (user_id),
    constraint fk_verification_token_user_entities
        foreign key (user_id)
            references user_entities
);

create table if not exists appointment
(
    id              int8 generated by default as identity,
    description     varchar(255) not null,
    start_date_time timestamp    not null,
    doctor_id       int8,
    pet_id          int8,
    primary key (id),
    constraint fk_appointment_user_entities
        foreign key (doctor_id)
            references user_entities(id),
    constraint fk_appointment_pet_entities
        foreign key (pet_id)
            references pet_entities(id)
);

create table if not exists clinical_examination
(
    id          int8 generated by default as identity,
    date        date,
    is_can_move boolean,
    text        varchar(255),
    weight      float8,
    doctor_id   int8,
    pet_id      int8,
    primary key (id),
    constraint fk_clinical_examination_user_entities
        foreign key (doctor_id)
            references user_entities,
    constraint fk_clinical_examination_pet_entities
        foreign key (pet_id)
            references pet_entities
);

create table if not exists comment
(
    id        int8 generated by default as identity,
    content   varchar(255) not null,
    date_time timestamp    not null,
    user_id   int8,
    primary key (id),
    constraint fk_comment_user_entities
        foreign key (user_id)
            references user_entities on delete cascade
);

create table if not exists comment_reaction
(
    positive   boolean not null,
    comment_id int8    not null,
    user_id    int8    not null,
    primary key (comment_id, user_id),
    constraint fk_comment_reaction_comment
        foreign key (comment_id)
            references comment,
    constraint fk_comment_reaction_user_entities
        foreign key (user_id)
            references user_entities
);

create table if not exists diagnosis
(
    id          int8 generated by default as identity,
    description varchar(255) not null,
    doctor_id   int8,
    pet_id      int8,
    primary key (id),
    constraint fk_diagnosis_user_entities
        foreign key (doctor_id)
            references user_entities,
    constraint fk_diagnosis_pet_entities
        foreign key (pet_id)
            references pet_entities
);

create table if not exists doctor_non_working
(
    id        int8 generated by default as identity,
    date      date,
    type      varchar(255),
    doctor_id int8,
    primary key (id),
    constraint fk_doctor_non_working_user_entities
        foreign key (doctor_id)
            references user_entities
);

create table if not exists doctor_review
(
    id         int8 generated by default as identity,
    comment_id int8,
    doctor_id  int8,
    primary key (id),
    constraint fk_doctor_review_comment
        foreign key (comment_id)
            references comment,
    constraint fk_doctor_review_user_entities
        foreign key (doctor_id)
            references user_entities
);

create table if not exists doctor_schedule
(
    id          int8 generated by default as identity,
    week_number int4,
    work_shift  varchar(255) not null,
    doctor_id   int8,
    primary key (id),
    constraint fk_doctor_schedule_user_entities
        foreign key (doctor_id)
            references user_entities
);

create table if not exists medicine
(
    id               int8 generated by default as identity,
    description      varchar(255) not null,
    icon             varchar(255),
    manufacture_name varchar(255) not null,
    name             varchar(255) not null,
    primary key (id),
    constraint uk_medicine
        unique (manufacture_name, name)
);

create table if not exists notification
(
    id          int8 generated by default as identity,
    description varchar(255),
    end_date    timestamp,
    event_id    varchar(255),
    location    varchar(255),
    start_date  timestamp,
    summary     varchar(255),
    pet_id      int8,
    primary key (id),
    constraint fk_notification_pet_entities
        foreign key (pet_id)
            references pet_entities
);

create table if not exists pet_contact
(
    id         int8 not null,
    address    varchar(255),
    owner_name varchar(255),
    pet_code   varchar(255),
    phone      int8,
    primary key (id),
    constraint uk_pet_contact
        unique (pet_code),
    constraint fk_pet_contact_pet_entities
        foreign key (id)
            references pet_entities
);

create table if not exists pet_found
(
    id        int8 generated by default as identity,
    latitude  varchar(255),
    longitude varchar(255),
    text      varchar(255),
    pet_id    int8,
    primary key (id),
    constraint fk_pet_found_pet_entities
        foreign key (pet_id)
            references pet_entities
);

create table if not exists procedure
(
    type                  varchar(31) not null,
    id                    int8 generated by default as identity,
    date                  date,
    is_periodical         boolean,
    medicine_batch_number varchar(255),
    period_days           int4,
    medicine_id           int8,
    notification_id       int8,
    pet_id                int8,
    primary key (id),
    constraint fk_procedure_medicine
        foreign key (medicine_id)
            references medicine,
    constraint fk_procedure_notification
        foreign key (notification_id)
            references notification,
    constraint fk_procedure_pet_entities
        foreign key (pet_id)
            references pet_entities
);

create table if not exists reproduction
(
    id           int8 generated by default as identity,
    child_count  int4,
    due_date     date,
    estrus_start date,
    mating       date,
    pet_id       int8,
    primary key (id),
    constraint fk_reproduction_pet_entities
        foreign key (pet_id) references pet_entities
);



create table if not exists topic
(
    id               int8 generated by default as identity,
    content          varchar(255),
    creation_date    timestamp not null,
    is_closed        boolean,
    last_update_date timestamp not null,
    title            varchar(255),
    topic_starter_id int8,
    primary key (id),
    constraint fk_topic_user_entities
        foreign key (topic_starter_id)
            references user_entities
);

create table if not exists topic_comments
(
    topic_id    int8 not null,
    comments_id int8 not null,
    constraint uk_topic_comments
        unique (comments_id),
    constraint fk_topic_comments_comment
        foreign key (comments_id)
            references comment,
    constraint fk_topic_comments_topic
        foreign key (topic_id)
            references topic
);

create table if not exists treatment
(
    id           int8 generated by default as identity,
    diagnosis_id int8,
    primary key (id),
    constraint fk_treatment_diagnosis
        foreign key (diagnosis_id)
            references diagnosis
);

create table if not exists treatment_procedure_list
(
    treatment_id      int8 not null,
    procedure_list_id int8 not null,
    constraint uk_treatment_procedure_list
        unique (procedure_list_id),
    constraint fk_treatment_procedure_list_procedure
        foreign key (procedure_list_id)
            references procedure,
    constraint fk_treatment_procedure_list_treatment
        foreign key (treatment_id)
            references treatment
);