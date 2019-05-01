  create table harga (
       id varchar(36) not null,
        amount decimal(19,2) not null,
        duration_length integer not null,
        duration_type integer not null,
        nama varchar(255),
        primary key (id)
    ) engine=InnoDB;
 
    
    create table member (
       id varchar(36) not null,
        alamat varchar(255),
        foto_ktp varchar(255),
        nomor_ktp varchar(255),
        tanggal_lahir date,
        tempat_lahir varchar(255),
        user_id varchar(36),
        primary key (id)
    ) engine=InnoDB;
 
    
    create table member_card (
       id varchar(36) not null,
        created_date date,
        expired_date date,
        nomor_kartu varchar(16),
        nomor_plat varchar(255),
        qr_code varchar(255),
        tipe_kendaraan varchar(255),
        member_id varchar(36),
        primary key (id)
    ) engine=InnoDB;
 
    
    create table transaksi (
       id varchar(36) not null,
        created_date date,
        paid bit,
        harga_id varchar(36) not null,
        member_id varchar(36) not null,
        member_card_id varchar(36) not null,
        primary key (id)
    ) engine=InnoDB;
 
    
    create table user (
       id varchar(36) not null,
        active bit not null,
        fullname varchar(255),
        hp varchar(255),
        password varchar(255),
        photo varchar(255),
        user_type varchar(255),
        username varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    
    alter table member 
       add constraint UK_5k89e3ig8vwbi07hmcqe7473t unique (nomor_ktp);

    
    alter table member_card 
       add constraint UK_69dmb6w2qq7uw2sl8ts3hr89o unique (nomor_kartu);

    
    alter table member_card 
       add constraint UK_sh87rsfunapp4bl00b74fi7fy unique (nomor_plat);

    
    alter table member_card 
       add constraint UK_q943ct1fflcnn8b31xldpfx2l unique (qr_code);
 
    
    alter table user 
       add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
 
    
    alter table member 
       add constraint FKswb523yn1xw3806ojrfpcyadl 
       foreign key (user_id) 
       references user (id);
 
    
    alter table member_card 
       add constraint FKnrtncuqhfypdeav3oot9ud573 
       foreign key (member_id) 
       references member (id);
 
    
    alter table transaksi 
       add constraint FKage2phg1x5qp8ghvoxg5pd8ln 
       foreign key (harga_id) 
       references harga (id);
 
    
    alter table transaksi 
       add constraint FKmv4k7j8iaggprpb8tdtnt16in 
       foreign key (member_id) 
       references member (id);
 
    
    alter table transaksi 
       add constraint FK9kwqexw9jwgpqcf2xev5v85tf 
       foreign key (member_card_id) 
       references member_card (id);