DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo` (
   `id` INTEGER NOT NULL AUTO_INCREMENT,
   `description` TEXT NOT NULL,
   `created_date` DATETIME(6) NOT NULL DEFAULT NOW(6),
   `last_modified_date` DATETIME(6) NOT NULL DEFAULT NOW(6),
   PRIMARY KEY (`id`)
);