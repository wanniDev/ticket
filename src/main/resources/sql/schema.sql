drop table if exists `users`;
drop table if exists `points`;
drop table if exists `tokens`;
drop table if exists `concerts`;
drop table if exists `seats`;
drop table if exists `reservations`;
drop table if exists `payments`;
drop table if exists `point_histories`;
drop table if exists `reservations_seats`;
drop table if exists `idempotent`;

CREATE TABLE `users` (
     `id` bigint auto_increment primary key,
    `tsid` varchar(255) NOT NULL UNIQUE,
    `point_id`	bigint not null,
    INDEX user_tsid_idx (tsid)
);

CREATE TABLE `points` (
     `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique ,
	`balance`   decimal(19, 4)	null,
    `create_time`	datetime not null ,
    `update_time`	datetime null,
    INDEX point_tsid_idx (tsid)
);

CREATE TABLE `tokens` (
     `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
	`issued_time`	datetime null,
	`status`	enum('ACTIVE', 'EXPIRED') null,
	`user_id`	bigint not null,
    INDEX token_tsid_idx (tsid)
);

CREATE TABLE `concerts` (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
	`date`	date null,
    INDEX concert_tsid_idx (tsid)
);

CREATE TABLE `seats` (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
	`number`	varchar(255) null,
    `price`	DECIMAL(19, 4)	null,
	`status`	enum('AVAILABLE', 'RESERVED', 'OCCUPIED') null ,
	`concert_id`    bigint not null,
    INDEX seat_tsid_idx (tsid)
);

CREATE TABLE `reservations` (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
    `status`	enum('PENDING', 'CONFIRMED', 'CANCELLED') null,
	`reservation_time`	datetime null,
	`expiration_time`	datetime null,
	`user_id`	bigint not null,
    `concert_id`	bigint not null,
    INDEX reservation_tsid_idx (tsid)
);

CREATE TABLE `payments` (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
	`amount`	DECIMAL(19, 4)	null,
	`payment_time`	datetime null,
    `user_id`	bigint not null,
	`point_id`	bigint not null,
    INDEX payment_tsid_idx (tsid)
);

CREATE TABLE point_histories (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
    `transaction_type` enum('CHARGE','USE') null,
    `user_id`	bigint not null,
    `point_id`	bigint not null,
    `amount`	DECIMAL(19, 4)	null,
    `create_time`	datetime not null,
    INDEX point_history_tsid_idx (tsid)
);
CREATE TABLE `reservations_seats` (
    `id` bigint auto_increment primary key,
    `reservation_id`	bigint not null,
    `seat_id`	bigint not null
);
CREATE TABLE `idempotent` (
    `id` bigint auto_increment primary key,
    `key` varchar(255) not null unique
);

insert into `points` (`id`, `tsid`, `balance`, `create_time`, `update_time`) values (1, 'point1', 1000, '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `users` (`id`, `tsid`, `point_id`) values (1, 'user1', 1);
insert into `concerts` (`id`, `tsid`, `date`) values (1, 'concert1', '2024-05-03');
insert into `seats` (`id`, `tsid`, `number`, `price`, `status`, `concert_id`) values (1, 'seat1', 'A1', 100, 'AVAILABLE', 1);
