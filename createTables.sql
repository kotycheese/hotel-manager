CREATE TABLE room (
    id bigint primary key generated always as identity,
    number int,
    beds int,
    pricePerNight decimal,
    note varchar(255));

CREATE TABLE guest (
    id bigint primary key generated always as identity,
    name varchar(255),
    born date,
    email varchar(255));

CREATE TABLE rent (
    id bigint primary key generated always as identity,
    pricePerNight decimal,
    guest bigint,
    room bigint,
    born startTime,
    born endTime;