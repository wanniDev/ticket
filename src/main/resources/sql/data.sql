
insert into `points` (`id`, `balance`, `created_at`, `updated_at`) values (1, 5000, '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `points` (`id`, `balance`, `created_at`, `updated_at`) values (2, 2000, '2024-01-02 00:00:00', '2024-01-02 00:00:00');
insert into `points` (`id`, `balance`, `created_at`, `updated_at`) values (3, 1000, '2024-01-03 00:00:00', '2024-01-03 00:00:00');
insert into `points` (`id`, `balance`, `created_at`, `updated_at`) values (4, 90000, '2024-01-04 00:00:00', '2024-01-04 00:00:00');
insert into `points` (`id`, `balance`, `created_at`, `updated_at`) values (5, 3000, '2024-01-05 00:00:00', '2024-01-05 00:00:00');


insert into `users` (`id`, `point_id`, `email`, `created_at`, `updated_at`) values (1, 1, 'user1@gmail.com', '2024-01-05 00:00:00', '2024-01-05 00:00:00');
insert into `users` (`id`, `point_id`, `email`, `created_at`, `updated_at`) values (2, 2, 'user2@gmail.com', '2024-01-05 00:00:00', '2024-01-05 00:00:00');
insert into `users` (`id`, `point_id`, `email`, `created_at`, `updated_at`) values (3, 3, 'user3@naver.com', '2024-01-05 00:00:00', '2024-01-05 00:00:00');
insert into `users` (`id`, `point_id`, `email`, `created_at`, `updated_at`) values (4, 4, 'user4@gmail.com', '2024-01-05 00:00:00', '2024-01-05 00:00:00');
insert into `users` (`id`, `point_id`, `email`, `created_at`, `updated_at`) values (5, 5, 'user5@naver.com', '2024-01-05 00:00:00', '2024-01-05 00:00:00');


insert into `concerts` (`id`, `date`, `created_at`, `updated_at`) values (1, '2024-05-05', '2024-01-05 00:00:00', '2024-01-05 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (1,  'A1', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (2,  'A2', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (3,  'A3', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (4,  'A4', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (5,  'A5', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (6,  'A6', 100, 'RESERVED', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (7,  'A7', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (8,  'A8', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (9,  'A9', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (10, 'A10' , 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');

insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (11,  'B1', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (12,  'B2', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (13,  'B3', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (14,  'B4', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (15,  'B5', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (16,  'B6', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (17,  'B7', 100, 'RESERVED', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (18,  'B8', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (19,  'B9', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (20,  'B10', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');

insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (21,  'C1', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (22,  'C2', 100, 'RESERVED', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (23,  'C3', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (24,  'C4', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (25,  'C5', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (26,  'C6', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (27,  'C7', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (28,  'C8', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (29,  'C9', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (30,  'C10', 100, 'AVAILABLE', 1, '2024-05-05', '2024-01-01 00:00:00', '2024-01-01 00:00:00');

insert into `concerts` (`id`, `date`, `created_at`, `updated_at`) values (2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (31, 'A1', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (32, 'A2', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (33, 'A3', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (34, 'A4', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (35, 'A5', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (36, 'A6', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (37, 'A7', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (38, 'A8', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (39, 'A9', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (40, 'A10', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');

insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (41, 'B1', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (42, 'B2', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (43, 'B3', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (44, 'B4', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (45, 'B5', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (46, 'B6', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (47, 'B7', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (48, 'B8', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (49, 'B9', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (50, 'B10', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');

insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (51, 'C1', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (52, 'C2', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (53, 'C3', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (54, 'C4', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (55, 'C5', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (56, 'C6', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (57, 'C7', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (58, 'C8', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (59, 'C9', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (60, 'C10', 100, 'AVAILABLE', 2, '2024-05-04', '2024-01-01 00:00:00', '2024-01-01 00:00:00');

insert into `concerts` (`id`, `date`, `created_at`, `updated_at`) values (3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (61, 'A1', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (62, 'A2', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (63, 'A3', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (64, 'A4', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (65, 'A5', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (66, 'A6', 100, 'RESERVED', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (67, 'A7', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (68, 'A8', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (69, 'A9', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (70, 'A10', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');

insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (71, 'B1', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (72, 'B2', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (73, 'B3', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (74, 'B4', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (75, 'B5', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (76, 'B6', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (77, 'B7', 100, 'RESERVED', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (78, 'B8', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (79, 'B9', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (80, 'B10', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');

insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (81, 'C1', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (82, 'C2', 100, 'RESERVED', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (83, 'C3', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (84, 'C4', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (85, 'C5', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (86, 'C6', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (87, 'C7', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (88, 'C8', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (89, 'C9', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
insert into `seats` (`id`, `number`, `price`, `status`, `concert_id`, `concert_date`, `created_at`, `updated_at`) values (90, 'C10', 100, 'AVAILABLE', 3, '2024-05-06', '2024-01-01 00:00:00', '2024-01-01 00:00:00');