create table ns_user (
     id bigint generated by default as identity,
     user_id varchar(20) not null,
     password varchar(20) not null,
     name varchar(20) not null,
     email varchar(50),
     created_at timestamp not null,
     updated_at timestamp,
     primary key (id)
);

create table course (
    id bigint generated by default as identity,
    title varchar(255) not null,
    creator_id bigint not null,
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id)
);

create table image (
   id bigint generated by default as identity,
   size bigint not null,
   width int not null,
   height int not null,
   type varchar(10) not null,
   created_at timestamp not null,
   updated_at timestamp,
   primary key (id)
);

create table session (
    id bigint generated by default as identity,
    title varchar(255) not null,
    start_data_time DATETIME not null,
    end_date_time DATETIME not null,
    status varchar(10) not null,
    course_id bigint not null,
    image_id bigint not null,
    amount bigint null,
    max_enrollment_count bigint null,
    remain_enrollment_count bigint null,
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id),
    foreign key (image_id) references image (id),
    foreign key (course_id) references course (id)
);

create table enrollment (
     id bigint generated by default as identity,
     user_id bigint not null,
     session_id bigint not null,
     created_at timestamp not null,
     updated_at timestamp,
     primary key (id),
     foreign key (session_id) references session (id),
     foreign key (user_id) references ns_user (id)
);

create table question (
    id bigint generated by default as identity,
    created_at timestamp not null,
    updated_at timestamp,
    contents clob,
    deleted boolean not null,
    title varchar(100) not null,
    writer_id bigint,
    primary key (id)
);

create table answer (
    id bigint generated by default as identity,
    created_at timestamp not null,
    updated_at timestamp,
    contents clob,
    deleted boolean not null,
    question_id bigint,
    writer_id bigint,
    primary key (id)
);

create table delete_history (
    id bigint not null,
    content_id bigint,
    content_type varchar(255),
    created_date timestamp,
    deleted_by_id bigint,
    primary key (id)
);
