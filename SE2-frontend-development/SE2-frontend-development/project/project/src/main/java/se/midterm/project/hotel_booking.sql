SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- Drop existing tables
DROP TABLE IF EXISTS booking, room, users;

-- Create users table
CREATE TABLE `users` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `username` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE,
                         `password` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `email` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE,
                         `role` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'USER',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `users` (`id`, `username`, `password`, `email`, `role`) VALUES
                                                                        (1, 'admin', '$2a$10$XURPShl5edEp5IDjX5LiL.vY1oSJ7i.4Y55h39VfcuQXNDE1R2WXm', 'admin@demo.com', 'ADMIN'),
                                                                        (2, 'user', '$2a$10$XURPShl5edEp5IDjX5LiL.vY1oSJ7i.4Y55h39VfcuQXNDE1R2WXm', 'user@demo.com', 'USER'),
                                                                        (3, 'thao', '$2a$10$XURPShl5edEp5IDjX5LiL.vY1oSJ7i.4Y55h39VfcuQXNDE1R2WXm', 'thao@demo.com', 'USER'),
                                                                        (4, 'vuong', '$2a$10$XURPShl5edEp5IDjX5LiL.vY1oSJ7i.4Y55h39VfcuQXNDE1R2WXm', 'vuong@demo.com', 'USER');

-- Create room table
CREATE TABLE `room` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT,
                        `number` VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE,
                        `type` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                        `price` DECIMAL(10,2) NOT NULL,
                        `is_available` TINYINT(1) NOT NULL DEFAULT 1,
                        `image_url` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `image_url2` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `image_url3` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `image_url4` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `image_url5` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `image_url6` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `image_url7` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                        `description` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
                        `area` DOUBLE NOT NULL DEFAULT 0.0,
                        `capacity` INT NOT NULL DEFAULT 0,
                        `amenities` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `room` (`id`, `number`, `type`, `price`, `is_available`, `image_url`, `image_url2`, `image_url3`, `image_url4`, `image_url5`, `image_url6`, `image_url7`, `description`, `area`, `capacity`, `amenities`) VALUES
                                                                                                                                                                                                                          (1, '101', 'Couple Simple Room', 60.00, 1, 'assets/images/rooms/Couple Simple Room/couple01.jpg', 'assets/images/rooms/Couple Simple Room/couple02.jpg', 'assets/images/rooms/Couple Simple Room/couple03.jpg', 'assets/images/rooms/Couple Simple Room/couple04.jpg', 'assets/images/rooms/Couple Simple Room/couple05.jpg', 'assets/images/rooms/Couple Simple Room/couple06.jpg', 'assets/images/rooms/Couple Simple Room/couple07.jpg', 'A cozy room for couples.', 25.5, 2, 'WiFi, TV'),
                                                                                                                                                                                                                          (2, '102', 'Deluxe Room', 80.00, 0, 'assets/images/rooms/Deluxe Room/deluxe01.jpg', 'assets/images/rooms/Deluxe Room/deluxe02.jpg', 'assets/images/rooms/Deluxe Room/deluxe03.jpg', NULL, NULL, NULL, NULL, 'A luxurious deluxe room.', 30.0, 2, 'WiFi, Mini Bar'),
                                                                                                                                                                                                                          (3, '103', 'Family Room', 100.00, 0, 'assets/images/rooms/Family Room/family01.jpg', 'assets/images/rooms/Family Room/family02.jpg', 'assets/images/rooms/Family Room/family03.jpg', NULL, NULL, NULL, NULL, 'Spacious room for families.', 40.0, 4, 'WiFi, TV, Balcony');

-- Create booking table
CREATE TABLE `booking` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                           `user_id` BIGINT NOT NULL,
                           `room_id` BIGINT NOT NULL,
                           `check_in_date` DATE NOT NULL,
                           `check_out_date` DATE NOT NULL,
                           `status` ENUM('Pending','Confirmed','Cancelled') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Pending',
                           `guest_full_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
                           `guest_phone` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
                           `num_of_adults` INT NOT NULL DEFAULT 0,
                           `num_of_children` INT NOT NULL DEFAULT 0,
                           `booking_confirmation_code` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
                           PRIMARY KEY (`id`),
                           CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                           CONSTRAINT `fk_room_id` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `booking` (`id`, `user_id`, `room_id`, `check_in_date`, `check_out_date`, `status`, `guest_full_name`, `guest_phone`, `num_of_adults`, `num_of_children`, `booking_confirmation_code`) VALUES
                                                                                                                                                                                                       (1, 3, 1, '2024-03-25', '2024-03-27', 'Confirmed', 'Le Phuong Thao', '0123456789', 2, 0, 'ABC123'),
                                                                                                                                                                                                       (2, 4, 3, '2024-03-28', '2024-03-30', 'Pending', 'Duy Vuong', '0987654321', 1, 1, 'DEF456');

COMMIT;


ALTER TABLE `users`
ADD COLUMN `profile_image` LONGBLOB,
ADD COLUMN `profile_image_content_type` VARCHAR(50);