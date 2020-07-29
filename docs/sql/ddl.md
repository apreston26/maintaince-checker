```sqlite
CREATE TABLE IF NOT EXISTS `Mechanic`
(
    `mechanic_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `location`    INTEGER                           NOT NULL,
    `name`        TEXT                              NOT NULL COLLATE NOCASE
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_Mechanic_name` ON `Mechanic` (`name`);

CREATE TABLE IF NOT EXISTS `Maintenance`
(
    `maintenance_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `mechanic_id`    INTEGER,
    `date`           INTEGER,
    `type`           INTEGER                           NOT NULL,
    FOREIGN KEY (`mechanic_id`) REFERENCES `Mechanic` (`mechanic_id`) ON UPDATE NO ACTION ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS `index_Maintenance_mechanic_id` ON `Maintenance` (`mechanic_id`)
``` 

[Downloadable Version of SQL](ddl.sql)