create table if not exists public.users
(
    id               bigserial
    constraint users_pk
    primary key,
    firstname        varchar(20),
    second_name      varchar(20),
    age              integer,
    telephone_number varchar(20),
    email            varchar(20),
    created          timestamp default now() not null,
    updated          timestamp default now() not null,
    sex              varchar(1),
    deleted          boolean   default false
    );

alter table public.users
    owner to user32;

create table if not exists public.products
(
    id      bigint    default nextval('product_id_seq'::regclass) not null
    constraint products_pk
    primary key,
    name    varchar(50)                                           not null,
    price   double precision                                      not null,
    created timestamp default now()                               not null,
    updated timestamp default now()                               not null
    );

alter table public.products
    owner to user32;

create table if not exists public.l_users_product
(
    id         integer not null
    constraint l_users_product_pk
    primary key,
    user_id    bigint  not null
    constraint l_users_product_users_id_fk
    references public.users
    on update cascade on delete cascade,
    product_id bigint  not null
    constraint l_users_product_product_id_fk
    references public.products
    on update cascade on delete cascade,
    created    timestamp default now(),
    updated    timestamp default now()
    );

alter table public.l_users_product
    owner to user32;

create table if not exists public.security
(
    id       bigserial
    constraint security_pk
    primary key,
    login    varchar(20)             not null,
    password varchar(20)             not null,
    created  timestamp default now() not null,
    updated  timestamp default now() not null,
    user_id  bigint                  not null
    unique
    constraint security_users_id_fk
    references public.users
    on update cascade on delete cascade
    );

alter table public.security
    owner to user32;

