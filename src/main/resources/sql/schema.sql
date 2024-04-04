CREATE TABLE `user` (
	`id`	varchar(255)    NOT NULL	COMMENT '사용자 식별자',
	`username`	varchar(255)	NULL	COMMENT '사용자 이름',
	`balance`	bigint	NULL,
	`create_time`	datetime	NULL,
	`update_time`	datetime	NOT NULL
);

CREATE TABLE `token` (
	`id`	varchar(255)	NOT NULL,
	`expiration_time`	datetime	NULL,
	`status`	varchar(255)	NULL	COMMENT 'ACTIVE, EXPIRED',
	`create_time`	datetime	NULL,
    `update_time`	datetime	NULL,
	`user_id`	varchar(255)	NOT NULL	COMMENT '사용자 식별자'
);

CREATE TABLE `concert_date` (
	`id`	varchar(255)	NOT NULL,
	`date`	datetime	NULL
);

CREATE TABLE `seat` (
	`id`	varchar(255)	NOT NULL,
	`number`	int4	NULL,
	`status`	varchar(255)	NULL,
	`concert_date_id`	varchar(255)	NOT NULL
);

CREATE TABLE `reservation` (
	`id`	varchar(255)	NOT NULL,
    `status`	varchar(255)	NULL,
	`reservation_time`	datetime	NULL,
	`expiration_time`	datetime	NULL,
	`user_id`	varchar(255)	NOT NULL	COMMENT '사용자 식별자',
	`seat_id`	varchar(255)	NOT NULL
);

CREATE TABLE `payment` (
	`id`	varchar(255)	NOT NULL,
	`amount`	DECIMAL(19, 4)	NULL,
	`payment_time`	datetime	NULL,
	`reservation_id`	varchar(255)	NOT NULL,
	`user_id`	varchar(255)	NOT NULL	COMMENT '사용자 식별자'
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
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