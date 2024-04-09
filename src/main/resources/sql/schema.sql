CREATE TABLE `user` (
    `id`	bigint    NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE,
    `point_id`	bigint	NOT NULL,
    INDEX user_tsid_idx (tsid)
);

CREATE TABLE `point` (
	`id`	bigint    NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE,
	`balance`   DECIMAL(19, 4)	NULL,
    `create_time`	datetime	NOT NULL,
    `update_time`	datetime	NULL,
    INDEX point_tsid_idx (tsid)
);

CREATE TABLE `token` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE,
	`issued_time`	datetime	NULL,
	`status`	varchar(255)	NULL,
	`user_id`	bigint	NOT NULL,
    INDEX token_tsid_idx (tsid)
);

CREATE TABLE `concert_date` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE,
	`date`	datetime	NULL,
    INDEX concert_date_tsid_idx (tsid)
);

CREATE TABLE `seat` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE,
	`number`	varchar(255)	NULL,
	`status`	varchar(255)	NULL,
	`concert_date_id`	bigint	NOT NULL,
    INDEX seat_tsid_idx (tsid)
);

CREATE TABLE `reservation` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE,
    `status`	varchar(255)	NULL,
	`reservation_time`	datetime	NULL,
	`expiration_time`	datetime	NULL,
	`user_id`	bigint	NOT NULL,
	`seat_id`	bigint	NOT NULL,
    INDEX reservation_tsid_idx (tsid)
);

CREATE TABLE `payment` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE,
	`amount`	DECIMAL(19, 4)	NULL,
	`payment_time`	datetime	NULL,
	`reservation_id`	bigint	NOT NULL,
	`point_id`	bigint	NOT NULL,
    INDEX payment_tsid_idx (tsid)
);

CREATE TABLE `point_usage` (
    `id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE,
    `transaction_type` varchar(255) NULL,
    `user_id`	bigint	NOT NULL,
    `point_id`	bigint	NOT NULL,
    `create_time`	datetime	NOT NULL,
    `update_time`	datetime	NULL,
    INDEX point_usage_tsid_idx (tsid)
);

CREATE TABLE `payment_usage`(
    `id`   bigint       NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE,
    `transaction_key` varchar(255) NULL,
    `amount` DECIMAL(19, 4) NULL,
    `status` varchar(255) NULL,
    `create_time`	datetime	NOT NULL,
    `update_time`	datetime	NULL,
    `payment_id` bigint NOT NULL,
    `point_id` bigint NOT NULL,
    INDEX upayment_usage_tsid_idx (tsid)
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
    `id`
);

ALTER TABLE `point` ADD CONSTRAINT `PK_POINT` PRIMARY KEY (
	`id`
);

ALTER TABLE `token` ADD CONSTRAINT `PK_TOKEN` PRIMARY KEY (
	`id`
);

ALTER TABLE `concert_date` ADD CONSTRAINT `PK_CONCERT_DATE` PRIMARY KEY (
	`id`
);

ALTER TABLE `seat` ADD CONSTRAINT `PK_SEAT` PRIMARY KEY (
	`id`
);

ALTER TABLE `reservation` ADD CONSTRAINT `PK_RESERVATION` PRIMARY KEY (
	`id`
);

ALTER TABLE `payment` ADD CONSTRAINT `PK_PAYMENT` PRIMARY KEY (
	`id`
);

ALTER TABLE `point_usage` ADD CONSTRAINT `PK_USER_POINT` PRIMARY KEY (
    `id`
);

ALTER TABLE `payment_usage` ADD CONSTRAINT `PK_PAYMENT_USAGE` PRIMARY KEY (
    `id`
);