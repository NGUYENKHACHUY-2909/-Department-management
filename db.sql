-- Drop the database if it already exists
DROP DATABASE IF EXISTS TestingSystem;
-- Create database
CREATE DATABASE IF NOT EXISTS TestingSystem;
USE TestingSystem;

-- Create table Department
DROP TABLE IF EXISTS 	`Department`;
CREATE TABLE IF NOT EXISTS `Department` (
	id 						INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`name` 					NVARCHAR(50) NOT NULL UNIQUE KEY,
    `author_id`             TINYINT UNSIGNED,  
    created_date			DATETIME DEFAULT NOW()
);

-- create table: Account
DROP TABLE IF EXISTS `Account`;
CREATE TABLE `Account`(
	id						TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username				VARCHAR(50) NOT NULL UNIQUE KEY,
	`email` 				VARCHAR(50) NOT NULL UNIQUE KEY,
    first_name				NVARCHAR(50) NOT NULL,
    last_name				NVARCHAR(50) NOT NULL,
    `password` 				VARCHAR(800) NOT NULL,
    department_id 			INT UNSIGNED,
    created_date			DATETIME DEFAULT NOW(),
    `role` 					ENUM('ADMIN','EMPLOYEE','MANAGER') NOT NULL DEFAULT 'EMPLOYEE',
    FOREIGN KEY(department_id) REFERENCES Department(id) ON DELETE SET NULL
);

-- =============================================
-- INSERT DATA 
-- =============================================
-- Add data Department
INSERT INTO Department(	`name`, 		author_id,		created_date) 
VALUES
						('Marketing'	, 	1,		'2020-03-05'),
						('Sale'		, 	2,		'2020-03-05'),
						('Bảo vệ'		, 	1,	    '2020-03-07'),
						('Nhân sự'		, 	3,		'2020-03-08'),
						('Kỹ thuật'	, 	4,		'2020-03-10'),
						('Tài chính'	, 	4,	 	NOW()		),
						('Phó giám đốc', 	5,	    NOW()		),
						('Giám đốc'	, 	1,		'2020-04-07'),
						('Thư kí'		, 	2,		'2020-04-07'),
						('Bán hàng'	, 	6,		'2020-04-09');
                    
-- Add data Account
-- Password: 123456
INSERT INTO `Account`(	username		,	`email`                 ,	first_name	,	last_name	,						`password`									    ,   department_id   ,   	created_date	,	`role`		)
VALUES 				(	'dangblack'		,   'dangblack@gmail.com'	,	'Nguyen'	,	'Hai Dang'	,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '5'			,		'2020-03-05'	,	'ADMIN'		),
					(	'quanganh'		,	'quângnh@gmail.com'	    ,	'Nguyen'	,	'Quang Anh'	,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '1'			,		'2020-03-05'	,	'MANAGER'	),
                    (	'vanchien'		,	'vanchien@gmail.com'	,	'Tran'		,	'Van Chien'	,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '1'			,	    '2020-03-07'	,	'ADMIN'		),
                    (	'cocoduongqua'	,	'cocoduongqua@gmail.com',	'Nguyen'	,	'Co Co'		,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '1'			,		'2020-03-08'	,	'EMPLOYEE'	),
                    (	'doccocaubai'	,   'doccocaubai@gmail.com'	,	'Nguyen'	,	'Doc Co'	,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '2'			,		'2020-03-10'	,	'ADMIN'		),
                    (	'khabanh'		,   'khabanh@gmail.com'	    ,	'Phan'		,	'Kha Bang'	,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '2'			,	 	NOW()			,	'EMPLOYEE'	),
                    (	'huanhoahong'	,   'huanhoahong@gmail.com'	,	'Tran'		,	'Van Huan'	,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '2'			,	    NOW()			,	'ADMIN'		),
                    (	'tungnui'		,   'tungnui@gmail.com'	    ,	'Nguyen'	,	'Tung Nui'	,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '8'			,		'2020-04-07'	,	'MANAGER'	),
                    (	'duongghuu'		,   'duongghuu@gmail.com'	,	'Phan'		,	'Duong Huu'	,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '9'			,		'2020-04-07'	,	'ADMIN'		),
                    (	'vtiaccademy'	,   'vtiacademy@gmail.com'	,	'Tran'		,	'Academy'	,	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi'	    ,	    '10'		,		'2020-04-09'	,	'MANAGER'	);
                    ALTER TABLE `Department`
ADD FOREIGN KEY (`author_id`) REFERENCES `Account`(id);