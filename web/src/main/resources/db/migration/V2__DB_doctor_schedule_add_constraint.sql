alter table doctor_schedule
add constraint doctor_id_week_number_unique
        unique (doctor_id, week_number);
