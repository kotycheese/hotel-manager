CREATE TABLE room (
    id bigint primary key generated always as identity,
    number integer,
    beds integer,
    pricePerNight decimal,
    note varchar(255));

CREATE TABLE guest (
    id bigint primary key generated always as identity,
    name varchar(255),
    born date,
    email varchar(255));

CREATE TABLE rent (
    id bigint primary key generated always as identity,
    price decimal,
    guestid bigint,
    roomid bigint,
    startTime date,
    endTime date);