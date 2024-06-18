drop table if exists `user_roles`;
drop table if exists `users`;
drop table if exists `refresh_token_info`;
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
    `id` bigint primary key,
    `point_id`	bigint not null,
    `email` varchar(255) not null unique,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null,
    INDEX user_idx (point_id, email)
);

CREATE TABLE `user_roles` (
    `user_id` bigint not null,
    `roles` enum('USER') not null,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);

CREATE TABLE refresh_token_info (
    `id` bigint primary key,
    `refresh_token_jti` varchar(255) not null unique,
    `expired` boolean not null default false,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null
);


CREATE TABLE `points` (
    `id` bigint primary key,
	`balance`   decimal(19, 4)	null,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null
);

CREATE TABLE point_histories (
    `id` bigint primary key,
    `transaction_type` enum('CHARGE','RECHARGE') null,
    `user_id`	bigint not null,
    `point_id`	bigint not null,
    `amount`	DECIMAL(19, 4)	null,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null,
    INDEX point_history_idx (user_id, point_id)
);

CREATE TABLE `concerts` (
    `id` bigint primary key,
	`date`	date not null,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null,
    INDEX concert_idx (date)
);

CREATE TABLE `seats` (
    `id` bigint primary key,
	`number` varchar(255) null,
    `price`	DECIMAL(19, 4)	null,
	`status`	enum('AVAILABLE', 'RESERVED', 'OCCUPIED') null ,
	`concert_id`    bigint not null,
    `concert_date`  date not null,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null,
    INDEX seat_idx (concert_id)
);

CREATE TABLE `reservations` (
    `id` bigint primary key,
    `status`	enum('PENDING', 'CONFIRMED', 'CANCELLED') null,
	`reservation_time`	datetime null,
	`expiration_time`	datetime null,
	`user_id`	bigint not null,
    `concert_id`	bigint not null,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null,
    INDEX reservation_idx (user_id, concert_id)
);

CREATE TABLE `payments` (
    `id` bigint primary key,
	`amount`	DECIMAL(19, 4)	null,
	`payment_time`	datetime null,
    `user_id`	bigint not null,
	`point_id`	bigint not null,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null,
    INDEX payment_idx (user_id, point_id)
);
CREATE TABLE `reservations_seats` (
    `id` bigint auto_increment primary key,
    `reservation_id`	bigint not null,
    `seat_id`	bigint not null,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null
);
CREATE TABLE `idempotent` (
    `id` bigint auto_increment primary key,
    `key` varchar(255) not null unique,
    `created_at` datetime(6) not null,
    `updated_at` datetime(6) not null
);
