create database hotel_booking;
use hotel_booking;


INSERT INTO room (
    number, type, price, status,
    image_url, image_url2, image_url3, image_url4, image_url5, image_url6, image_url7,
    area, capacity, amenities, description
) VALUES

      ('S101', 'Standard Room', 80.00, 'Available',
       'assets/images/rooms/Standard Room/standard01.jpg','assets/images/rooms/Couple Simple Room/coupleBack.jpg','assets/images/rooms/Standard Room/standardBack.jpg','assets/images/rooms/Standard Room/standardFront.jpg','assets/images/rooms/Standard Room/standardLeft.jpg','assets/images/rooms/Standard Room/standardRight.jpg', NULL,
       22.0, 2, 'WiFi,TV,Air Conditioning', 'A comfortable and clean standard room perfect for solo travelers or couples.'),
      ('S102', 'Standard Room', 85.00, 'Available',
       'assets/images/rooms/Standard Room/standard02.jpg','assets/images/rooms/Standard Room/standardBack.jpg','assets/images/rooms/Standard Room/standardFront.jpg','assets/images/rooms/Standard Room/standardLeft.jpg','assets/images/rooms/Standard Room/standardRight.jpg', NULL, NULL,
       23.0, 2, 'WiFi,TV,Air Conditioning', 'Standard room with upgraded furnishings and a small workspace.'),
      ('S103', 'Standard Room', 90.00, 'Available',
       'assets/images/rooms/Standard Room/standard03.jpg','assets/images/rooms/Standard Room/standardBack.jpg','assets/images/rooms/Standard Room/standardFront.jpg','assets/images/rooms/Standard Room/standardLeft.jpg','assets/images/rooms/Standard Room/standardRight.jpg', NULL, NULL,
       24.5, 2, 'WiFi,TV,Air Conditioning,View', 'Standard room with modern amenities, suitable for business travelers.'),
      ('S104', 'Standard Room', 75.00, 'Available',
       'assets/images/rooms/Standard Room/standard04.jpg','assets/images/rooms/Standard Room/standardBack.jpg','assets/images/rooms/Standard Room/standardFront.jpg','assets/images/rooms/Standard Room/standardLeft.jpg','assets/images/rooms/Standard Room/standardRight.jpg', NULL, NULL,
       21.0, 2, 'WiFi,TV,Air Conditioning,Bathtub', 'Affordable standard room with basic essentials.'),
      ('S105', 'Standard Room', 88.00, 'Available',
       'assets/images/rooms/Standard Room/standard05.jpg','assets/images/rooms/Couple Simple Room/coupleBack.jpg','assets/images/rooms/Standard Room/standardBack.jpg','assets/images/rooms/Standard Room/standardFront.jpg','assets/images/rooms/Standard Room/standardLeft.jpg','assets/images/rooms/Standard Room/standardRight.jpg', NULL,
       22.5, 2, 'WiFi,TV,Air Conditioning,Bathtub', 'Well-equipped standard room with clean design and natural light.'),

      ('D201', 'Deluxe Room', 120.00, 'Available',
       'assets/images/rooms/Deluxe Room/deluxe01.jpg','assets/images/rooms/Standard Room/standardBack.jpg','assets/images/rooms/Deluxe Room/deluxeBack.jpg','assets/images/rooms/Deluxe Room/deluxeFront.jpg','assets/images/rooms/Deluxe Room/deluxeLeft.jpg','assets/images/rooms/Deluxe Room/deluxeRight.jpg', NULL,
       28.0, 2, 'WiFi,TV,Air Conditioning,Bathtub', 'Spacious deluxe room with stylish furniture and a private balcony.'),
      ('D202', 'Deluxe Room', 125.00, 'Available',
       'assets/images/rooms/Deluxe Room/deluxe02.jpg','assets/images/rooms/Deluxe Room/deluxeBack.jpg','assets/images/rooms/Deluxe Room/deluxeFront.jpg','assets/images/rooms/Deluxe Room/deluxeLeft.jpg','assets/images/rooms/Deluxe Room/deluxeRight.jpg', NULL, NULL,
       30.0, 2, 'WiFi,TV,Minibar,Bathtub,View', 'Deluxe room with complimentary breakfast and modern decor.'),
      ('D203', 'Deluxe Room', 118.00, 'Available',
       'assets/images/rooms/Deluxe Room/deluxe03.jpg','assets/images/rooms/Deluxe Room/deluxeBack.jpg','assets/images/rooms/Deluxe Room/deluxeFront.jpg','assets/images/rooms/Deluxe Room/deluxeLeft.jpg','assets/images/rooms/Deluxe Room/deluxeRight.jpg', NULL, NULL,
       27.0, 2, 'WiFi,TV,Minibar,Bathtub', 'Cozy deluxe room with city view and minibar included.'),
      ('D204', 'Deluxe Room', 130.00, 'Available',
       'assets/images/rooms/Deluxe Room/deluxe04.jpg','assets/images/rooms/Standard Room/standardBack.jpg','assets/images/rooms/Deluxe Room/deluxeBack.jpg','assets/images/rooms/Deluxe Room/deluxeFront.jpg','assets/images/rooms/Deluxe Room/deluxeLeft.jpg','assets/images/rooms/Deluxe Room/deluxeRight.jpg', NULL,
       32.0, 2, 'WiFi,TV,Minibar,Bathtub', 'High-end deluxe room with large bathroom and breakfast.'),
      ('D205', 'Deluxe Room', 115.00, 'Available',
       'assets/images/rooms/Deluxe Room/deluxe05.jpg','assets/images/rooms/Deluxe Room/deluxeBack.jpg','assets/images/rooms/Deluxe Room/deluxeFront.jpg','assets/images/rooms/Deluxe Room/deluxeLeft.jpg','assets/images/rooms/Deluxe Room/deluxeRight.jpg', NULL, NULL,
       29.5, 2, 'WiFi,Air Conditioning,Minibar,Bathtub', 'Comfortable deluxe room for relaxing stays and business trips.'),

      ('SP301', 'Superior Room', 150.00, 'Available',
       'assets/images/rooms/Superior Room/super01.jpg','assets/images/rooms/Superior Room/superBack.jpg','assets/images/rooms/Superior Room/superFront.jpg','assets/images/rooms/Superior Room/superLeft.jpg','assets/images/rooms/Superior Room/superRight.jpg', NULL, NULL,
       35.0, 3, 'WiFi,TV, View,Bathtub', 'Superior room with refined design, breakfast included, and spacious layout for small families.'),
      ('SP302', 'Superior Room', 145.00, 'Available',
       'assets/images/rooms/Superior Room/super02.jpg','assets/images/rooms/Superior Room/superBack.jpg','assets/images/rooms/Superior Room/superFront.jpg','assets/images/rooms/Superior Room/superLeft.jpg','assets/images/rooms/Superior Room/superRight.jpg', NULL, NULL,
       34.0, 3, 'WiFi,TV,Air Conditioning,Bathtub', 'Quiet superior room with extra space and modern amenities.'),
      ('SP303', 'Superior Room', 155.00, 'Available',
       'assets/images/rooms/Superior Room/super03.jpg','assets/images/rooms/Standard Room/standardBack.jpg','assets/images/rooms/Superior Room/superBack.jpg','assets/images/rooms/Superior Room/superFront.jpg','assets/images/rooms/Superior Room/superLeft.jpg','assets/images/rooms/Superior Room/superRight.jpg', NULL,
       36.0, 3, 'WiFi,TV,Air Conditioning,Minibar,View,Bathtub', 'Spacious superior room with large bathtub and premium décor.'),
      ('SP304', 'Superior Room', 158.00, 'Available',
       'assets/images/rooms/Superior Room/super04.jpg','assets/images/rooms/Superior Room/superBack.jpg','assets/images/rooms/Superior Room/superFront.jpg','assets/images/rooms/Superior Room/superLeft.jpg','assets/images/rooms/Superior Room/superRight.jpg', NULL, NULL,
       37.0, 3, 'WiFi,TV,Air Conditioning', 'Superior room with minibar and top-floor city view.'),
      ('SP305', 'Superior Room', 149.00, 'Available',
       'assets/images/rooms/Superior Room/super05.jpg','assets/images/rooms/Superior Room/superBack.jpg','assets/images/rooms/Superior Room/superFront.jpg','assets/images/rooms/Superior Room/superLeft.jpg','assets/images/rooms/Superior Room/superRight.jpg', NULL, NULL,
       35.5, 3, 'WiFi,Air Conditioning,Minibar,View', 'Superior room with elegant lighting and a large workspace.'),

      ('F401', 'Family Room', 180.00, 'Available',
       'assets/images/rooms/Family Room/family01.jpg','assets/images/rooms/Family Room/familyBack.jpg','assets/images/rooms/Family Room/familyFront.jpg','assets/images/rooms/Family Room/familyLeft.jpg','assets/images/rooms/Family Room/familyRight.jpg', NULL, NULL,
       40.0, 4, 'WiFi,Minibar,View,Bathtub', 'Perfect for families with children, includes bunk beds and plenty of space.'),
      ('F402', 'Family Room', 190.00, 'Available',
       'assets/images/rooms/Family Room/family02.jpg','assets/images/rooms/Family Room/familyBack.jpg','assets/images/rooms/Family Room/familyFront.jpg','assets/images/rooms/Family Room/familyLeft.jpg','assets/images/rooms/Family Room/familyRight.jpg', NULL, NULL,
       42.0, 5, 'WiFi,TV,Air Conditioning,Minibar', 'Large family room with multiple beds and complimentary breakfast.'),
      ('F403', 'Family Room', 185.00, 'Available',
       'assets/images/rooms/Family Room/family03.jpg','assets/images/rooms/Family Room/familyBack.jpg','assets/images/rooms/Family Room/familyFront.jpg','assets/images/rooms/Family Room/familyLeft.jpg','assets/images/rooms/Family Room/familyRight.jpg', NULL, NULL,
       41.0, 4, 'WiFi,TV, View,Bathtub', 'Family room with small kitchen, ideal for longer stays.'),
      ('F404', 'Family Room', 195.00, 'Available',
       'assets/images/rooms/Family Room/family04.jpg','assets/images/rooms/Standard Room/standardBack.jpg','assets/images/rooms/Family Room/familyBack.jpg','assets/images/rooms/Family Room/familyFront.jpg','assets/images/rooms/Family Room/familyLeft.jpg','assets/images/rooms/Family Room/familyRight.jpg', NULL,
       43.0, 5, 'WiFi,TV,Air Conditioning,Minibar,View,Bathtub', 'Spacious layout and clean design for a relaxing family vacation.'),
      ('F405', 'Family Room', 175.00, 'Available',
       'assets/images/rooms/Family Room/family05.jpg','assets/images/rooms/Couple Simple Room/coupleBack.jpg','assets/images/rooms/Family Room/familyBack.jpg','assets/images/rooms/Family Room/familyFront.jpg','assets/images/rooms/Family Room/familyLeft.jpg','assets/images/rooms/Family Room/familyRight.jpg', NULL,
       40.5, 4, 'WiFi,TV,Minibar', 'Family room with garden view and comfortable bedding.'),

      ('C501', 'Couple Simple Room', 100.00, 'Available',
       'assets/images/rooms/Couple Simple Room/couple01.jpg','assets/images/rooms/Couple Simple Room/coupleBack.jpg','assets/images/rooms/Couple Simple Room/coupleFront.jpg','assets/images/rooms/Couple Simple Room/coupleLeft.jpg','assets/images/rooms/Couple Simple Room/coupleRight.jpg', NULL, NULL,
       25.0, 2, 'WiFi,Air Conditioning', 'Simple and romantic room for couples seeking peace and privacy.'),
      ('C502', 'Couple Simple Room', 105.00, 'Available',
       'assets/images/rooms/Couple Simple Room/couple01.jpg','assets/images/rooms/Couple Simple Room/coupleBack.jpg','assets/images/rooms/Couple Simple Room/coupleFront.jpg','assets/images/rooms/Couple Simple Room/coupleLeft.jpg','assets/images/rooms/Couple Simple Room/coupleRight.jpg', NULL, NULL,
       26.0, 2, 'WiFi,TV,Air Conditioning', 'Couple room with natural light, soft bedding, and flat-screen TV.'),
      ('C503', 'Couple Simple Room', 98.00, 'Available',
       'assets/images/rooms/Couple Simple Room/couple01.jpg','assets/images/rooms/Couple Simple Room/coupleBack.jpg','assets/images/rooms/Couple Simple Room/coupleFront.jpg','assets/images/rooms/Couple Simple Room/coupleLeft.jpg','assets/images/rooms/Couple Simple Room/coupleRight.jpg', NULL, NULL,
       24.5, 2, 'WiFi,TV', 'Intimate and quiet space for couples on a budget.'),
      ('C504', 'Couple Simple Room', 110.00, 'Available',
       'assets/images/rooms/Couple Simple Room/couple01.jpg','assets/images/rooms/Couple Simple Room/coupleBack.jpg','assets/images/rooms/Couple Simple Room/coupleFront.jpg','assets/images/rooms/Couple Simple Room/coupleLeft.jpg','assets/images/rooms/Couple Simple Room/coupleRight.jpg', NULL, NULL,
       27.0, 2, 'WiFi,TV,Bathtub', 'Room designed for couples to enjoy breakfast in bed and comfort.'),
      ('C505', 'Couple Simple Room', 102.00, 'Available',
       'assets/images/rooms/Couple Simple Room/couple01.jpg','assets/images/rooms/Couple Simple Room/coupleBack.jpg','assets/images/rooms/Couple Simple Room/coupleFront.jpg','assets/images/rooms/Couple Simple Room/coupleLeft.jpg','assets/images/rooms/Couple Simple Room/coupleRight.jpg', NULL, NULL,
       25.5, 2, 'WiFi,TV,View', 'Charming space for couples, ideal for short romantic getaways.');
























-- SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
-- START TRANSACTION;
-- SET time_zone = "+00:00";
--
-- -- Drop existing tables
-- DROP TABLE IF EXISTS booking, room, users;
--
-- -- Create users table
-- CREATE TABLE `users` (
--                          `id` BIGINT NOT NULL AUTO_INCREMENT,
--                          `username` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE,
--                          `password` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
--                          `email` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE,
--                          `role` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'USER',
--                          PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
--
-- INSERT INTO `users` (`id`, `username`, `password`, `email`, `role`) VALUES
--                                                                         (1, 'admin', '$2a$10$XURPShl5edEp5IDjX5LiL.vY1oSJ7i.4Y55h39VfcuQXNDE1R2WXm', 'admin@demo.com', 'ADMIN'),
--                                                                         (2, 'user', '$2a$10$XURPShl5edEp5IDjX5LiL.vY1oSJ7i.4Y55h39VfcuQXNDE1R2WXm', 'user@demo.com', 'USER'),
--                                                                         (3, 'thao', '$2a$10$XURPShl5edEp5IDjX5LiL.vY1oSJ7i.4Y55h39VfcuQXNDE1R2WXm', 'thao@demo.com', 'USER'),
--                                                                         (4, 'vuong', '$2a$10$XURPShl5edEp5IDjX5LiL.vY1oSJ7i.4Y55h39VfcuQXNDE1R2WXm', 'vuong@demo.com', 'USER');
--
-- -- Create room table
-- CREATE TABLE `room` (
--                         `id` BIGINT NOT NULL AUTO_INCREMENT,
--                         `number` VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE,
--                         `type` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
--                         `price` DECIMAL(10,2) NOT NULL,
--                         `is_available` TINYINT(1) NOT NULL DEFAULT 1,
--                         `image_url` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
--                         `image_url2` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
--                         `image_url3` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
--                         `image_url4` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
--                         `image_url5` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
--                         `image_url6` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
--                         `image_url7` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
--                         `description` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
--                         `area` DOUBLE NOT NULL DEFAULT 0.0,
--                         `capacity` INT NOT NULL DEFAULT 0,
--                         `amenities` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
--                         PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
--
-- INSERT INTO `room` (`id`, `number`, `type`, `price`, `is_available`, `image_url`, `image_url2`, `image_url3`, `image_url4`, `image_url5`, `image_url6`, `image_url7`, `description`, `area`, `capacity`, `amenities`) VALUES
--                                                                                                                                                                                                                           (1, '101', 'Couple Simple Room', 60.00, 1, 'assets/images/rooms/Couple Simple Room/couple01.jpg', 'assets/images/rooms/Couple Simple Room/couple02.jpg', 'assets/images/rooms/Couple Simple Room/couple03.jpg', 'assets/images/rooms/Couple Simple Room/couple04.jpg', 'assets/images/rooms/Couple Simple Room/couple05.jpg', 'assets/images/rooms/Couple Simple Room/couple06.jpg', 'assets/images/rooms/Couple Simple Room/couple07.jpg', 'A cozy room for couples.', 25.5, 2, 'WiFi, TV'),
--                                                                                                                                                                                                                           (2, '102', 'Deluxe Room', 80.00, 0, 'assets/images/rooms/Deluxe Room/deluxe01.jpg', 'assets/images/rooms/Deluxe Room/deluxe02.jpg', 'assets/images/rooms/Deluxe Room/deluxe03.jpg', NULL, NULL, NULL, NULL, 'A luxurious deluxe room.', 30.0, 2, 'WiFi, Mini Bar'),
--                                                                                                                                                                                                                           (3, '103', 'Family Room', 100.00, 0, 'assets/images/rooms/Family Room/family01.jpg', 'assets/images/rooms/Family Room/family02.jpg', 'assets/images/rooms/Family Room/family03.jpg', NULL, NULL, NULL, NULL, 'Spacious room for families.', 40.0, 4, 'WiFi, TV, Balcony');
--
-- -- Create booking table
-- CREATE TABLE `booking` (
--                            `id` BIGINT NOT NULL AUTO_INCREMENT,
--                            `user_id` BIGINT NOT NULL,
--                            `room_id` BIGINT NOT NULL,
--                            `check_in_date` DATE NOT NULL,
--                            `check_out_date` DATE NOT NULL,
--                            `status` ENUM('Pending','Confirmed','Cancelled') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'Pending',
--                            `guest_full_name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
--                            `guest_phone` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
--                            `num_of_adults` INT NOT NULL DEFAULT 0,
--                            `num_of_children` INT NOT NULL DEFAULT 0,
--                            `booking_confirmation_code` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
--                            PRIMARY KEY (`id`),
--                            CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
--                            CONSTRAINT `fk_room_id` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE CASCADE
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
--
-- INSERT INTO `booking` (`id`, `user_id`, `room_id`, `check_in_date`, `check_out_date`, `status`, `guest_full_name`, `guest_phone`, `num_of_adults`, `num_of_children`, `booking_confirmation_code`) VALUES
--                                                                                                                                                                                                        (1, 3, 1, '2024-03-25', '2024-03-27', 'Confirmed', 'Le Phuong Thao', '0123456789', 2, 0, 'ABC123'),
--                                                                                                                                                                                                        (2, 4, 3, '2024-03-28', '2024-03-30', 'Pending', 'Duy Vuong', '0987654321', 1, 1, 'DEF456');
--
-- COMMIT;
--
--
-- ALTER TABLE `users`
-- ADD COLUMN `profile_image` LONGBLOB,
-- ADD COLUMN `profile_image_content_type` VARCHAR(50);