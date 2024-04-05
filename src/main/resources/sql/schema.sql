CREATE TABLE `user` (
	`id`	bigint    NOT NULL	COMMENT '사용자 식별자',
    `tsid` varchar(255) NOT NULL UNIQUE COMMENT '사용자 식별 보조키',
	`username`	varchar(255)	NULL	COMMENT '사용자 이름',
	`balance`	bigint	NULL,
	`create_time`	datetime	NULL,
	`update_time`	datetime	NOT NULL,
    INDEX user_tsid_idx (tsid)
);

CREATE TABLE `token` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE COMMENT '토큰 식별 보조키',
	`expiration_time`	datetime	NULL,
	`status`	varchar(255)	NULL	COMMENT 'ACTIVE, EXPIRED',
	`create_time`	datetime	NULL,
    `update_time`	datetime	NULL,
	`user_id`	bigint	NOT NULL	COMMENT '사용자 식별자',
    INDEX token_tsid_idx (tsid)
);

CREATE TABLE `concert_date` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE COMMENT '콘서트 일자 식별 보조키',
	`date`	datetime	NULL,
    INDEX concert_date_tsid_idx (tsid)
);

CREATE TABLE `seat` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE COMMENT '좌석 식별 보조키',
	`number`	int4	NULL,
	`status`	varchar(255)	NULL,
	`concert_date_id`	bigint	NOT NULL,
    INDEX seat_tsid_idx (tsid)
);

CREATE TABLE `reservation` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE COMMENT '예약 식별 보조키',
    `status`	varchar(255)	NULL,
	`reservation_time`	datetime	NULL,
	`expiration_time`	datetime	NULL,
	`user_id`	bigint	NOT NULL	COMMENT '사용자 식별자',
	`seat_id`	bigint	NOT NULL,
    INDEX reservation_tsid_idx (tsid)
);

CREATE TABLE `payment` (
	`id`	bigint	NOT NULL,
    `tsid` varchar(255) NOT NULL UNIQUE COMMENT '결제 식별 보조키',
	`amount`	DECIMAL(19, 4)	NULL,
	`payment_time`	datetime	NULL,
	`reservation_id`	bigint	NOT NULL,
	`user_id`	bigint	NOT NULL	COMMENT '사용자 식별자',
    INDEX payment_tsid_idx (tsid)
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