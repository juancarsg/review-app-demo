USE reviews_app_db;

CREATE TABLE `Category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `day_of_week` varchar(20) NOT NULL,
  `opening_time` time NOT NULL,
  `closing_time` time NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_schedule_dayOfWeek-openingTime-closingTime` (`day_of_week`,`opening_time`,`closing_time`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Commerce` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` varchar(200) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Commerce_Category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `commerce_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_commerce_category_commerceid-categoryid` (`commerce_id`,`category_id`),
  KEY `idx_commerce_category_category_id` (`category_id`),
  CONSTRAINT `fk_commerce_category_categoryid_category` FOREIGN KEY (`category_id`) REFERENCES `Category` (`id`),
  CONSTRAINT `fk_commerce_category_commerceid_commerce` FOREIGN KEY (`commerce_id`) REFERENCES `Commerce` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Commerce_Schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `commerce_id` bigint NOT NULL,
  `schedule_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_commerce_schedule_commerceid-scheduleid` (`commerce_id`,`schedule_id`),
  KEY `idx_commerce_schedule_schedule_id` (`schedule_id`),
  CONSTRAINT `fk_commerce_schedule_commerceid_commerce` FOREIGN KEY (`commerce_id`) REFERENCES `Commerce` (`id`),
  CONSTRAINT `fk_commerce_schedule_scheduleid_schedule` FOREIGN KEY (`schedule_id`) REFERENCES `Schedule` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7728 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `Commerce_Stat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `commerce_id` bigint NOT NULL,
  `avg_rating` double NOT NULL,
  `count_reviews` bigint NOT NULL,
  `last_updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_commerce_stat_id-commerceid` (`id`,`commerce_id`),
  KEY `idx_commerce_stat_commerce_id` (`commerce_id`),
  CONSTRAINT `fk_commerce_stat_commerceid_commerce` FOREIGN KEY (`commerce_id`) REFERENCES `Commerce` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1008 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `User` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `registration_date` date NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name-email` (`name`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10000029 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `rating` int NOT NULL,
  `creation_date` date NOT NULL,
  `user_id` bigint NOT NULL,
  `commerce_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_review_userid-commerceid` (`user_id`,`commerce_id`),
  KEY `idx_review_commerce_id` (`commerce_id`),
  CONSTRAINT `fk_review_commerceid_commerce` FOREIGN KEY (`commerce_id`) REFERENCES `Commerce` (`id`),
  CONSTRAINT `fk_review_userid_user` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000036 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;