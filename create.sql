
    create table department (
       id bigint not null,
        department_name varchar(20) not null,
        primary key (id)
    ) engine=MyISAM

    create table employee (
       id bigint not null,
        company_email varchar(320) not null,
        employee_extension integer not null,
        employee_name varchar(30) not null,
        employee_number varchar(20) not null,
        department_id bigint,
        role_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table floor (
       id bigint not null,
        floor_name varchar(30) not null,
        image_path longtext,
        primary key (id)
    ) engine=MyISAM

    create table hibernate_sequence (
       next_val bigint
    ) engine=MyISAM

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    create table place (
       id bigint not null,
        place_lat decimal(10,8) not null,
        place_lng decimal(10,8) not null,
        place_name varchar(20) not null,
        floor_id bigint,
        place_type_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table place_type (
       id bigint not null,
        type_name varchar(20) not null,
        primary key (id)
    ) engine=MyISAM

    create table reservation (
       id bigint not null,
        end_time time not null,
        start_time time not null,
        employee_id bigint not null,
        place_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    create table role (
       id bigint not null,
        role_type varchar(10) not null,
        primary key (id)
    ) engine=MyISAM

    create table seat (
       id bigint not null,
        seat_lat decimal(10,8) not null,
        seat_lng decimal(10,8) not null,
        employee_id bigint not null,
        floor_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table seat_arrangement_history (
       id bigint not null,
        employee_email varchar(255) not null,
        employee_extension integer not null,
        employee_name varchar(255) not null,
        end_date date,
        start_date date not null,
        employee_id bigint not null,
        seat_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    create table seat_change_request (
       id bigint not null,
        request_status varchar(255) not null,
        current_seat_id bigint not null,
        employee_id bigint not null,
        request_seat_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    create table social_login_email (
       id bigint not null,
        email_address varchar(10) not null,
        employee_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    alter table employee 
       add constraint FKbejtwvg9bxus2mffsm3swj3u9 
       foreign key (department_id) 
       references department (id)

    alter table employee 
       add constraint FK3046kvjyysq288vy3lsbtc9nw 
       foreign key (role_id) 
       references role (id)

    alter table place 
       add constraint FKpd108lech4yosearvwgpk5pc5 
       foreign key (floor_id) 
       references floor (id)

    alter table place 
       add constraint FK82jpk5fbcg0bh3njfbfscdpea 
       foreign key (place_type_id) 
       references place_type (id)

    alter table reservation 
       add constraint FKoq2iacdgt8val8v26jn0iw83q 
       foreign key (employee_id) 
       references employee (id)

    alter table reservation 
       add constraint FKnqrq9wvdum1hkwlvwrhr6ehma 
       foreign key (place_id) 
       references place (id)

    alter table seat 
       add constraint FKm9oq3tru7lp2ekas33iveaekb 
       foreign key (employee_id) 
       references employee (id)

    alter table seat 
       add constraint FKf7ts9n9knk984g06inp56n4ag 
       foreign key (floor_id) 
       references floor (id)

    alter table seat_arrangement_history 
       add constraint FKe1l9jmhqbbomgs8vxoyjjmkvf 
       foreign key (employee_id) 
       references employee (id)

    alter table seat_arrangement_history 
       add constraint FK5jdj16tx3y75lh5ckcbokvld7 
       foreign key (seat_id) 
       references seat (id)

    alter table seat_change_request 
       add constraint FKsjto6cp4b7p6rrb6f7gype1xs 
       foreign key (current_seat_id) 
       references seat (id)

    alter table seat_change_request 
       add constraint FKsgidv2v2ps13dh2k6gidpplxj 
       foreign key (employee_id) 
       references employee (id)

    alter table seat_change_request 
       add constraint FKkr3gs5r2xf2qtewngwkomr361 
       foreign key (request_seat_id) 
       references seat (id)

    alter table social_login_email 
       add constraint FKi29pv7tss6jj7j3f4xbqtsl2p 
       foreign key (employee_id) 
       references employee (id)

    create table department (
       id bigint not null,
        department_name varchar(20) not null,
        primary key (id)
    ) engine=MyISAM

    create table employee (
       id bigint not null,
        company_email varchar(320) not null,
        employee_extension integer not null,
        employee_name varchar(30) not null,
        employee_number varchar(20) not null,
        department_id bigint,
        role_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table floor (
       id bigint not null,
        floor_name varchar(30) not null,
        image_path longtext,
        primary key (id)
    ) engine=MyISAM

    create table hibernate_sequence (
       next_val bigint
    ) engine=MyISAM

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    create table place (
       id bigint not null,
        place_lat decimal(10,8) not null,
        place_lng decimal(10,8) not null,
        place_name varchar(20) not null,
        floor_id bigint,
        place_type_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table place_type (
       id bigint not null,
        type_name varchar(20) not null,
        primary key (id)
    ) engine=MyISAM

    create table reservation (
       id bigint not null,
        end_time time not null,
        start_time time not null,
        employee_id bigint not null,
        place_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    create table role (
       id bigint not null,
        role_type varchar(10) not null,
        primary key (id)
    ) engine=MyISAM

    create table seat (
       id bigint not null,
        seat_lat decimal(10,8) not null,
        seat_lng decimal(10,8) not null,
        employee_id bigint not null,
        floor_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table seat_arrangement_history (
       id bigint not null,
        employee_email varchar(255) not null,
        employee_extension integer not null,
        employee_name varchar(255) not null,
        end_date date,
        start_date date not null,
        employee_id bigint not null,
        seat_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    create table seat_change_request (
       id bigint not null,
        request_status varchar(255) not null,
        current_seat_id bigint not null,
        employee_id bigint not null,
        request_seat_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    create table social_login_email (
       id bigint not null,
        email_address varchar(10) not null,
        employee_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    alter table employee 
       add constraint FKbejtwvg9bxus2mffsm3swj3u9 
       foreign key (department_id) 
       references department (id)

    alter table employee 
       add constraint FK3046kvjyysq288vy3lsbtc9nw 
       foreign key (role_id) 
       references role (id)

    alter table place 
       add constraint FKpd108lech4yosearvwgpk5pc5 
       foreign key (floor_id) 
       references floor (id)

    alter table place 
       add constraint FK82jpk5fbcg0bh3njfbfscdpea 
       foreign key (place_type_id) 
       references place_type (id)

    alter table reservation 
       add constraint FKoq2iacdgt8val8v26jn0iw83q 
       foreign key (employee_id) 
       references employee (id)

    alter table reservation 
       add constraint FKnqrq9wvdum1hkwlvwrhr6ehma 
       foreign key (place_id) 
       references place (id)

    alter table seat 
       add constraint FKm9oq3tru7lp2ekas33iveaekb 
       foreign key (employee_id) 
       references employee (id)

    alter table seat 
       add constraint FKf7ts9n9knk984g06inp56n4ag 
       foreign key (floor_id) 
       references floor (id)

    alter table seat_arrangement_history 
       add constraint FKe1l9jmhqbbomgs8vxoyjjmkvf 
       foreign key (employee_id) 
       references employee (id)

    alter table seat_arrangement_history 
       add constraint FK5jdj16tx3y75lh5ckcbokvld7 
       foreign key (seat_id) 
       references seat (id)

    alter table seat_change_request 
       add constraint FKsjto6cp4b7p6rrb6f7gype1xs 
       foreign key (current_seat_id) 
       references seat (id)

    alter table seat_change_request 
       add constraint FKsgidv2v2ps13dh2k6gidpplxj 
       foreign key (employee_id) 
       references employee (id)

    alter table seat_change_request 
       add constraint FKkr3gs5r2xf2qtewngwkomr361 
       foreign key (request_seat_id) 
       references seat (id)

    alter table social_login_email 
       add constraint FKi29pv7tss6jj7j3f4xbqtsl2p 
       foreign key (employee_id) 
       references employee (id)

    create table department (
       id bigint not null,
        department_name varchar(20) not null,
        primary key (id)
    ) engine=MyISAM

    create table employee (
       id bigint not null,
        company_email varchar(320) not null,
        employee_extension integer not null,
        employee_name varchar(30) not null,
        employee_number varchar(20) not null,
        department_id bigint,
        role_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table floor (
       id bigint not null,
        floor_name varchar(30) not null,
        image_path longtext,
        primary key (id)
    ) engine=MyISAM

    create table hibernate_sequence (
       next_val bigint
    ) engine=MyISAM

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    insert into hibernate_sequence values ( 1 )

    create table place (
       id bigint not null,
        place_lat decimal(10,8) not null,
        place_lng decimal(10,8) not null,
        place_name varchar(20) not null,
        floor_id bigint,
        place_type_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table place_type (
       id bigint not null,
        type_name varchar(20) not null,
        primary key (id)
    ) engine=MyISAM

    create table reservation (
       id bigint not null,
        end_time time not null,
        start_time time not null,
        employee_id bigint not null,
        place_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    create table role (
       id bigint not null,
        role_type varchar(10) not null,
        primary key (id)
    ) engine=MyISAM

    create table seat (
       id bigint not null,
        seat_lat decimal(10,8) not null,
        seat_lng decimal(10,8) not null,
        employee_id bigint not null,
        floor_id bigint,
        primary key (id)
    ) engine=MyISAM

    create table seat_arrangement_history (
       id bigint not null,
        employee_email varchar(255) not null,
        employee_extension integer not null,
        employee_name varchar(255) not null,
        end_date date,
        start_date date not null,
        employee_id bigint not null,
        seat_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    create table seat_change_request (
       id bigint not null,
        request_status varchar(255) not null,
        current_seat_id bigint not null,
        employee_id bigint not null,
        request_seat_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    create table social_login_email (
       id bigint not null,
        email_address varchar(10) not null,
        employee_id bigint not null,
        primary key (id)
    ) engine=MyISAM

    alter table employee 
       add constraint FKbejtwvg9bxus2mffsm3swj3u9 
       foreign key (department_id) 
       references department (id)

    alter table employee 
       add constraint FK3046kvjyysq288vy3lsbtc9nw 
       foreign key (role_id) 
       references role (id)

    alter table place 
       add constraint FKpd108lech4yosearvwgpk5pc5 
       foreign key (floor_id) 
       references floor (id)

    alter table place 
       add constraint FK82jpk5fbcg0bh3njfbfscdpea 
       foreign key (place_type_id) 
       references place_type (id)

    alter table reservation 
       add constraint FKoq2iacdgt8val8v26jn0iw83q 
       foreign key (employee_id) 
       references employee (id)

    alter table reservation 
       add constraint FKnqrq9wvdum1hkwlvwrhr6ehma 
       foreign key (place_id) 
       references place (id)

    alter table seat 
       add constraint FKm9oq3tru7lp2ekas33iveaekb 
       foreign key (employee_id) 
       references employee (id)

    alter table seat 
       add constraint FKf7ts9n9knk984g06inp56n4ag 
       foreign key (floor_id) 
       references floor (id)

    alter table seat_arrangement_history 
       add constraint FKe1l9jmhqbbomgs8vxoyjjmkvf 
       foreign key (employee_id) 
       references employee (id)

    alter table seat_arrangement_history 
       add constraint FK5jdj16tx3y75lh5ckcbokvld7 
       foreign key (seat_id) 
       references seat (id)

    alter table seat_change_request 
       add constraint FKsjto6cp4b7p6rrb6f7gype1xs 
       foreign key (current_seat_id) 
       references seat (id)

    alter table seat_change_request 
       add constraint FKsgidv2v2ps13dh2k6gidpplxj 
       foreign key (employee_id) 
       references employee (id)

    alter table seat_change_request 
       add constraint FKkr3gs5r2xf2qtewngwkomr361 
       foreign key (request_seat_id) 
       references seat (id)

    alter table social_login_email 
       add constraint FKi29pv7tss6jj7j3f4xbqtsl2p 
       foreign key (employee_id) 
       references employee (id)
