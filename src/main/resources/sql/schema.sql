CREATE TABLE `users` (
     `id` bigint auto_increment primary key,
    `tsid` varchar(255) NOT NULL UNIQUE,
    `point_id`	bigint	NOT NULL,
    INDEX user_tsid_idx (tsid)
);

CREATE TABLE `point` (
     `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique ,
	`balance`   decimal(19, 4)	null,
    `create_time`	datetime not null ,
    `update_time`	datetime null,
    INDEX point_tsid_idx (tsid)
);

CREATE TABLE `token` (
     `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
	`issued_time`	datetime null,
	`status`	varchar(255) null,
	`user_tsid`	varchar(255) not null,
    INDEX token_tsid_idx (tsid)
);

CREATE TABLE `concert` (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
	`date`	date null,
    INDEX concert_tsid_idx (tsid)
);

CREATE TABLE `seat` (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
	`number`	varchar(255) null,
	`status`	varchar(255) null ,
	`concert_id`	bigint not null,
    INDEX seat_tsid_idx (tsid)
);

CREATE TABLE `reservation` (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
    `status`	varchar(255) null,
	`reservation_time`	datetime null,
	`expiration_time`	datetime null,
	`user_id`	bigint not null,
	`seat_id`	bigint not null,
    INDEX reservation_tsid_idx (tsid)
);

CREATE TABLE `payment` (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
	`amount`	DECIMAL(19, 4)	null,
	`payment_time`	datetime null,
	`point_id`	bigint not null,
    INDEX payment_tsid_idx (tsid)
);

CREATE TABLE point_history (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
    `transaction_type` varchar(255) null,
    `user_id`	bigint	not null,
    `point_id`	bigint	not null,
    `create_time`	datetime not null,
    `update_time`	datetime not null,
    INDEX point_history_tsid_idx (tsid)
);

CREATE TABLE payment_history (
    `id` bigint auto_increment primary key,
    `tsid` varchar(255) not null unique,
    `transaction_key` varchar(255) null,
    `amount` DECIMAL(19, 4) null,
    `status` varchar(255) null,
    `occurred_time`	datetime not null,
    `update_time`	datetime null,
    `payment_id` bigint not null,
    `point_id` bigint not null,
    INDEX payment_history_tsid_idx (tsid)
);

insert into `point` (`id`, `tsid`, `balance`, `create_time`, `update_time`) values (1, 'point1', 1000, '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `users` (`id`, `tsid`, `point_id`) values (1, 'user1', 1);