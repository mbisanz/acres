INSERT INTO `acres_aircraft_aircraft` VALUES (7,'VH-FNA',4);
INSERT INTO `acres_aircraft_aircrafttype` VALUES (4,'F50',NULL,'Fokker 50'),(5,'CNA',NULL,'Cessna');
INSERT INTO `acres_license_license` VALUES (8,'2014-01-01 01:00:00','2024-01-01 01:00:00',4,1);
INSERT INTO `acres_user_user` VALUES (1,'user@acres.pac','user','Udo User','04F8996DA763B7A969B1028EE3007569EAF3A635486DDAB211D512C85B9DF8FB'),(2,NULL,'guest',NULL,'84983C60F7DAADC1CB8698621F802C0D9F9A3C3C295C810748FB048115C186EC'),(3,'admin@acres.pac','admin','Adam Admin','8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918');
INSERT INTO `acres_user_user_roles` VALUES (1,'user'),(2,'guest'),(3,'admin'),(3,'user');
INSERT INTO `hibernate_sequence` VALUES (1000);
