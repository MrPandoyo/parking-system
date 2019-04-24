create table user_permission (
   id_user varchar(36) not null,
    id_permission varchar(36) not null,
    primary key (id_user, id_permission)
) engine=InnoDB;

create table permission (
   id varchar(36) not null,
    permission_label varchar(255) not null,
    permission_value varchar(255) not null,
    primary key (id)
) engine=InnoDB;

create table user (
   id varchar(36) not null,
    active bit not null,
    email varchar(255),
    fullname varchar(255),
    hp varchar(255),
    photo varchar(255),
    user_type varchar(255),
    username varchar(255) not null,
    primary key (id)
) engine=InnoDB;

create table user_password (
   id_user varchar(36) not null,
    password varchar(255) not null,
    primary key (id_user)
) engine=InnoDB;

alter table permission
   add constraint UK_4u071c8xsdwe0k6mitlb96gig unique (permission_value);

alter table user
   add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

alter table user
   add constraint UK_65uf1tebumngwn6338vqn505c unique (hp);

alter table user
   add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);

alter table user_permission
   add constraint FKr7x5g4jsd96nwq4hak8vrkeq0
   foreign key (id_permission)
   references permission (id);

alter table user_permission
   add constraint FKs8ps2lm408x2onohod4gnu0vl
   foreign key (id_user)
   references user (id);

alter table user_password
   add constraint FKc6ej0m55bov3brbfiih66sj5i
   foreign key (id_user)
   references user (id);
