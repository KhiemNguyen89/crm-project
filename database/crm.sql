DROP DATABASE IF EXISTS `crm_project`;

CREATE DATABASE IF NOT EXISTS `crm_project`;
USE `crm_project`;

-- Tao table cho `loai_thanh_vien`

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

-- Tao table cho `nguoi_dung`

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`id` int NOT NULL AUTO_INCREMENT,
    `email` varchar(50) NOT NULL,
    `password` varchar(255) NOT NULL,
    `full_name` varchar(50) NOT NULL,
    `mobile_number` varchar(20) DEFAULT NULL,
    `role_id` int NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES role(`id`)
);

-- Tao table cho `du_an`

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `start` date NOT NULL,
    `end` date NOT NULL,
    `user_id` int NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES user(`id`)
);

-- Tao table cho `trang_thai_cong_viec`

DROP TABLE IF EXISTS `task_status`;
CREATE TABLE `task_status` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
);

-- Tao table cho `cong_viec`

DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
	`description` varchar(255) DEFAULT NULL,
    `start` date NOT NULL,
    `end` date NOT NULL,
    `user_id` int NOT NULL,
    `project_id` int NOT NULL,
    `task_status_id` int NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES user(`id`),
    FOREIGN KEY (`project_id`) REFERENCES project(`id`),
    FOREIGN KEY (`task_status_id`) REFERENCES task_status(`id`)
);


INSERT INTO `role` (id, name, description)
VALUES
	(1, 'ADMIN', 'admin'),
    (2, 'MANAGER', 'manager'),
	(3, 'MEMBER', 'member');

INSERT INTO 
	`user`(
		id, 
        email, 
        password, 
        full_name, 
        mobile_number, 
        role_id
	) 
VALUES (
		1,
        'khiemnguyen@gmail.com',
        '$2a$12$jxrGxVzyfzD1WNAlhBIfdOuZlyzJCXTdbJ/RcMjCiEsnWcOwcmo5O',
        -- test123
        'Nguyễn Duy Khiêm',
        '0901234567',
        1
	),(
		2,
        'nguyenvana@gmail.com',
        '$2a$12$OQrXSDSByAk5Z7Ic9RRTTuCy5Nexpcf31CXjVL6dltwtKb8V37vYS',
        -- leadera
        'Nguyen Văn A',
        '0123456789',
        2
	),(
		3,
        'nguyenthib@gmail.com',
        '$2a$12$waBXgM9EO8YNXsaI6W1rReLNkFideWl38Pq3Pz5akn3jgqz9CkOvK',
        -- leaderb
        'Nguyễn Thị B',
        '0987654321',
        2
	),(
		4,
        'tranthic@gmail.com',
        '$2a$12$SukkrWxFcTWKiaIV65X6gu7eboW1t610nGXthnJDl2HBa8TmiQ9d6',
        -- memberc
        'Trần Thị C',
        '0981654321',
        3
	),(
		5,
        'letrungd@gmail.com',
        '$2a$12$Hv9CosHB0JIlr/TZmbI9qOER7H2e0Bqu9fk8mugpL0RbkoA2nLGWu',
        -- memberd
        'Lê Trung D',
        '0933123456',
        3
	),(
		6,
        'duongthie@gmail.com',
        '$2a$12$x0E0yXCrEtL1wtbMgRu8sOIf5oDleikcxnyWWFFRGjDq3Y9QE8DU6',
        -- membere
        'Dương Thị E',
        '0983654321',
        3
	),(
		7,
        'hothanhg@gmail.com',
        '$2a$12$SbaNWrDEfWfd/b4kGG2D..7OJWvxPNPOt4KDeOcxeYzNaknckbB8.',
        -- memberg
        'Hồ Thanh G',
        '0976564321',
        3
	);
    
INSERT INTO `task_status` (name)
VALUES 
	('Chưa bắt đầu'),
    ('Đang thực hiện'),
    ('Đã hoàn thành');
    
INSERT INTO `project` (name, description, start, end, user_id)
VALUES
	('HRM', 'Human Resource Manager', '2023-01-01', '2023-12-01', 2),
	('CRM', 'Customer Relationship Manager', '2023-01-01', '2023-07-01', 3),
    ('Jira_220701','Godzilla (Gojira)','2023-07-01','2024-01-01',3);
    
INSERT INTO 
	`task` (
		name,
        description,
        start,
        end,
        user_id,
        project_id,
        task_status_id)
VALUES (
        'Soft_HRM',
        'Soft design: tạo spec tài liệu cho HRM',
        '2023-01-01',
        '2023-03-01',
        1,
        1,
        3
    ), (
        'Sys_HRM',
        'System design: study, investigate, estimate, confirm, transfer, đưa ra schedule cho HRM',
        '2023-03-01',
        '2023-04-01',
        2,
        1,
        3
    ), (
        'DB_HRM',
        'Tạo database cho HRM',
        '2023-04-01',
        '2023-06-01',
        3,
        1,
        3
    ), (
        'BE_HRM',
        'Tạo backend cho HRM',
        '2023-06-01',
        '2023-08-01',
        5,
        1,
        2
    ), (
        'FE_HRM',
        'Tạo frontend cho HRM',
        '2023-06-01',
        '2023-08-01',
        6,
        1,
        2
    ), (
        'UT_HRM',
        'Unit test cho HRM',
        '2023-08-01',
        '2023-10-01',
        7,
        1,
        1
    ), (
        'ST_HRM',
        'System test cho HRM',
        '2023-10-01',
        '2023-12-01',
        2,
        1,
        1
    ), (
        'Soft_CRM',
        'Soft design: tạo spec tài liệu cho CRM',
        '2023-01-01',
        '2023-02-01',
        1,
        2,
        3
    ), (
        'Sys_CRM',
        'System design: study, investigate, estimate, confirm, transfer, đưa ra schedule cho CRM',
        '2023-02-01',
        '2023-03-01',
        4,
        2,
        3
    ), (
        'DB_CRM',
        'Tạo database cho CRM',
        '2023-03-01',
        '2023-04-01',
        3,
        2,
        3
    ), (
        'BE_CRM',
        'Tạo backend cho CRM',
        '2023-04-01',
        '2023-05-01',
        5,
        2,
        3
    ), (
        'FE_CRM',
        'Tạo frontend cho CRM',
        '2023-04-01',
        '2023-05-01',
        6,
        2,
        3
    ), (
        'UT_CRM',
        'Unit test cho CRM',
        '2023-05-01',
        '2023-06-01',
        7,
        2,
        3
    ), (
        'ST_CRM',
        'System test cho CRM',
        '2023-06-01',
        '2023-07-01',
        4,
        2,
        3
    ), (
        'Soft_Jira',
        'Soft design: tạo spec tài liệu cho Jira',
        '2023-07-01',
        '2023-08-01',
        1,
        3,
        2
    ), (
        'Sys_Jira',
        'System design: study, investigate, estimate, confirm, transfer, đưa ra schedule cho Jira',
        '2023-08-01',
        '2023-09-01',
        4,
        3,
        1
    ), (
        'DB_Jira',
        'Tạo database cho Jira',
        '2023-09-01',
        '2023-10-01',
        3,
        3,
        1
    ), (
        'BE_Jira',
        'Tạo backend cho Jira',
        '2023-10-01',
        '2023-11-01',
        5,
        3,
        1
    ), (
        'FE_Jira',
        'Tạo frontend cho Jira',
        '2023-10-01',
        '2023-11-01',
        6,
        3,
        1
    ), (
        'UT_Jira',
        'Unit test cho Jira',
        '2023-11-01',
        '2023-12-01',
        7,
        3,
        1
    ), (
        'ST_Jira',
        'System test cho Jira',
        '2023-12-01',
        '2024-01-01',
        4,
        3,
        1
    );
    








