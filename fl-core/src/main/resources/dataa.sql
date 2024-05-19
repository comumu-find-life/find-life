--insert into user (role, nick_name, email, profile_url, password, brith, phone_number,  nationality)
--VALUES ('GETTER', 'nickname', 'test@email.com', 'profileUrl', '$2a$10$jtkeHmjS4QBwiHFloIk31uvcYGBhCZ7madgWWbb8d2X8jUGmI.PAi', '001031', '01012341234', 'Korean'),
--    ('GETTER', 'Jason', 'Jason@email.com', 'profileUrl', '{bcrypt}$2a$10$pouEYC8pSdHrJuxRInPS/.F4G4HVFcAm0CouviPhcX0zDnHyPYvze', '001031', '01012341234', 'Korean'),
--    ('GETTER', 'Nick', 'nick@email.com', 'profileUrl', '{bcrypt}$2a$10$pouEYC8pSdHrJuxRInPS/.F4G4HVFcAm0CouviPhcX0zDnHyPYvze', '001031', '01012341234', 'Korean');


--insert into home_address(home_address_id, state, city, post_code, detail_address,street_name, street_number, latitude, longitude)
--values  ('3', 'VAC', '멜버른', 13234, '401호', '거리이름', '123fa', 20.13, 502.13);
--
--insert into home(home_id, user_id, home_address_id, bath_room_count, people_count, bond, short_introduce, introduce, bill, view_count, type)
--values ('1', '1','3', 10, 10, 20000,'짧은 소개', '소개글', 3000000, 10, 'RENT');


---- 홈 주소 생성
--insert into home_address(state, city, post_code, detail_address, street_name, street_number, latitude, longitude)
--values
--    ('VIC', 'Melbourne', 3000, '401호', '거리이름1', '123fa', 20.13, 502.13),
--    ('VIC', 'Melbourne', 3000, '402호', '거리이름2', '124fa', 20.14, 503.14),
--    ('VIC', 'Melbourne', 3000, '403호', '거리이름3', '125fa', 20.15, 504.15),
--    ('VIC', 'Melbourne', 3000, '404호', '거리이름4', '126fa', 20.16, 505.16),
--    ('VIC', 'Melbourne', 3000, '405호', '거리이름5', '127fa', 20.17, 506.17),
--    ('VIC', 'Melbourne', 3000, '406호', '거리이름6', '128fa', 20.18, 507.18),
--    ('VIC', 'Melbourne', 3000, '407호', '거리이름7', '129fa', 20.19, 508.19),
--    ('NSW', 'Sydney', 3223, '408호', '거리이름8', '130fa', 20.20, 509.20),
--    ('NSW', 'Sydney', 3223, '409호', '거리이름9', '131fa', 20.21, 510.21),
--    ('NSW', 'Sydney', 3223, '410호', '거리이름10', '132fa', 20.22, 511.22),
--    ('VIC', 'Melbourne', 13244, '411호', '거리이름11', '133fa', 20.23, 512.23),
--    ('VIC', 'Melbourne', 13245, '412호', '거리이름12', '134fa', 20.24, 513.24),
--    ('VIC', 'Melbourne', 13246, '413호', '거리이름13', '135fa', 20.25, 514.25),
--    ('VIC', 'Melbourne', 13247, '414호', '거리이름14', '136fa', 20.26, 515.26),
--    ('VIC', 'Melbourne', 13248, '415호', '거리이름15', '137fa', 20.27, 516.27);
---- 홈 생성 및 홈 주소 지정
--insert into home(user_id, rent, home_address_id, bedroom_count, bath_room_count, people_count, bond, short_introduce, introduce, bill, view_count, type, deal_savable)
--values
--    ('1', 270, '1', 2, 1, 10, 540, '짧은 소개1', '소개글1', 300, 10, 'RENT', 0),
--    ('1', 270, '2', 2, 2, 8, 540, '짧은 소개2', '소개글2', 250, 8, 'RENT', 0),
--    ('1', 220, '3', 2, 2, 12, 440, '짧은 소개3', '소개글3', 350, 12, 'RENT', 0),
--    ('1', 270, '4', 2, 1, 10, 540, '짧은 소개4', '소개글4', 300, 10, 'RENT', 0),
--    ('1', 350, '5', 2, 1, 8, 1400, '짧은 소개5', '소개글5', 250, 8, 'RENT', 0),
--    ('1', 450, '6', 2, 1, 12, 900, '짧은 소개6', '소개글6', 350, 12, 'RENT', 0),
--    ('1', 550, '7', 2, 2, 10, 550, '짧은 소개7', '소개글7', 300, 10, 'RENT', 0),
--    ('1', 270, '8', 3, 2, 8, 270, '짧은 소개8', '소개글8', 250, 8, 'RENT', 0),
--    ('1', 270, '9', 2, 3, 12, 540, '짧은 소개9', '소개글9', 350, 12, 'RENT', 0),
--    ('1', 270, '10', 2, 3, 10, 20000, '짧은 소개10', '소개글10', 300, 10, 'RENT', 0),
--    ('1', 520, '11', 2, 2, 8, 15000, '짧은 소개11', '소개글11', 250, 8, 'RENT', 0),
--    ('1', 270, '12', 2, 1, 12, 25000, '짧은 소개12', '소개글12', 35, 12, 'RENT', 0),
--    ('1', 270, '13', 2, 2, 10, 20000, '짧은 소개13', '소개글13', 300000, 10, 'RENT', 0),
--    ('1', 380, '14', 2, 2, 8, 15000, '짧은 소개14', '소개글14', 2500000, 8, 'RENT', 0),
--    ('1', 270, '15', 2, 1, 12, 25000, '짧은 소개15', '소개글15', 3500000, 12, 'RENT', 0);
--
--
--insert into home_image(home_image_id, image_url, home_id)
--values
--    ('1', '이미지 url', '1'),
--    ('2', '이미지 url', '2'),
--    ('3', '이미지 url', '3'),
--    ('4', '이미지 url', '4'),
--    ('5', '이미지 url', '5'),
--    ('6', '이미지 url', '6'),
--    ('7', '이미지 url', '7'),
--    ('8', '이미지 url', '8'),
--    ('9', '이미지 url', '9'),
--    ('10', '이미지 url', '10'),
--    ('11', '이미지 url', '11'),
--    ('12', '이미지 url', '12'),
--    ('13', '이미지 url', '13'),
--    ('14', '이미지 url', '14'),
--    ('15', '이미지 url', '15')

-- HomeAddress 테이블에 스무 개의 더미 데이터 삽입 쿼리

-- HomeAddress 테이블에 스무 개의 더미 데이터 삽입 쿼리

-- HomeAddress 테이블에 스무 개의 더미 데이터 삽입 쿼리

INSERT INTO home_address (state, city, post_code, detail_address, street_name, street_number, latitude, longitude)
VALUES
('New South Wales', 'Sydney', 2000, '123 George St, Suite 101', 'George St', '123', -33.86785, 151.20732),
('Victoria', 'Melbourne', 3000, '456 Collins St, Apt 202', 'Collins St', '456', -37.814, 144.96332),
('Queensland', 'Brisbane', 4000, '789 Queen St, Unit 303', 'Queen St', '789', -27.46977, 153.02513),
('Western Australia', 'Perth', 6000, '987 Hay St, Flat 404', 'Hay St', '987', -31.950527, 115.860457),
('South Australia', 'Adelaide', 5000, '246 King William St, Suite 505', 'King William St', '246', -34.9285, 138.6007),
('Tasmania', 'Hobart', 7000, '369 Murray St, Apt 606', 'Murray St', '369', -42.88214, 147.3272),
('Northern Territory', 'Darwin', 800, '555 Mitchell St, Unit 707', 'Mitchell St', '555', -12.46113, 130.84185),
('Australian Capital Territory', 'Canberra', 2600, '101 Constitution Ave, Flat 808', 'Constitution Ave', '101', -35.282, 149.128684),
('New South Wales', 'Newcastle', 2300, '789 Hunter St, Suite 909', 'Hunter St', '789', -32.9267, 151.7799),
('Victoria', 'Geelong', 3220, '456 Malop St, Apt 303', 'Malop St', '456', -38.150002, 144.350006),
('Queensland', 'Gold Coast', 4217, '369 Surfers Paradise Blvd, Unit 101', 'Surfers Paradise Blvd', '369', -28.001388, 153.429077),
('Western Australia', 'Fremantle', 6160, '987 High St, Flat 202', 'High St', '987', -32.0569, 115.7439),
('South Australia', 'Barossa Valley', 5352, '246 Tanunda Rd, Suite 303', 'Tanunda Rd', '246', -34.5333, 138.95),
('Tasmania', 'Launceston', 7250, '555 Charles St, Apt 404', 'Charles St', '555', -41.4299, 147.1571),
('Northern Territory', 'Alice Springs', 870, '101 Todd St, Unit 707', 'Todd St', '101', -23.6975, 133.8836),
('Australian Capital Territory', 'Wollongong', 2500, '789 Crown St, Flat 808', 'Crown St', '789', -34.4278, 150.8931),
('New South Wales', 'Byron Bay', 2481, '456 Jonson St, Suite 909', 'Jonson St', '456', -28.6474, 153.6127),
('Victoria', 'Ballarat', 3350, '369 Sturt St, Apt 303', 'Sturt St', '369', -37.5606, 143.856),
('Queensland', 'Cairns', 4870, '987 Abbott St, Unit 101', 'Abbott St', '987', -16.9224, 145.7734),
('Western Australia', 'Margaret River', 6285, '246 Bussell Hwy, Suite 303', 'Bussell Hwy', '246', -33.9533, 115.0739);



-- Home 테이블에 스무 개의 더미 데이터 삽입 쿼리

INSERT INTO home (user_id, home_address_id, bath_room_count, bedroom_count, deal_savable, people_count, bond, gender, type, short_introduce, introduce, bill, rent, view_count)
VALUES
(1, 1, 2, 3, true, 4, 2000, 'MALE', 'APARTMENT', 'Cozy apartment in the heart of Sydney CBD', 'This apartment is conveniently located near public transportation and shopping centers.', 300, 2500, 500),
(2, 2, 2, 2, true, 2, 1500, 'FEMALE', 'HOUSE', 'Spacious house with a garden in Melbourne suburbs', 'Perfect for a family looking for a quiet neighborhood with easy access to schools and parks.', 200, 3000, 450),
(3, 3, 1, 1, true, 1, 1000, 'OTHER', 'STUDIO', 'Modern studio apartment in Brisbane CBD', 'Ideal for a single professional looking for a convenient location close to work and entertainment options.', 150, 2000, 400),
(4, 4, 2, 3, true, 4, 2500, 'MALE', 'APARTMENT', 'Luxurious apartment with a view in Perth waterfront', 'Enjoy breathtaking views of the Swan River and city skyline from this high-rise apartment.', 350, 3500, 600),
(5, 5, 1, 1, true, 1, 1200, 'FEMALE', 'STUDIO', 'Charming studio apartment in Adelaide CBD', 'Located in a historic building, this studio offers a unique living experience with modern amenities.', 180, 1800, 300),
(6, 6, 2, 2, true, 2, 1800, 'OTHER', 'HOUSE', 'Quaint house with a garden in Hobart outskirts', 'Escape the hustle and bustle of the city in this peaceful neighborhood surrounded by nature.', 250, 2200, 400),
(7, 7, 1, 1, true, 1, 1000, 'MALE', 'STUDIO', 'Convenient studio apartment in Darwin city center', 'Close to restaurants, shops, and entertainment venues, this studio offers urban living at its best.', 150, 2000, 350),
(8, 8, 2, 3, true, 4, 2200, 'FEMALE', 'APARTMENT', 'Modern apartment with city views in Canberra CBD', 'Live in style in this contemporary apartment with access to amenities like swimming pool and gym.', 300, 2800, 550),
(9, 9, 2, 2, true, 2, 1500, 'OTHER', 'HOUSE', 'Cozy house with a backyard in Newcastle suburbs', 'Perfect for a small family looking for a peaceful neighborhood with good schools and parks.', 200, 2500, 400),
(10, 10, 1, 1, true, 1, 800, 'MALE', 'STUDIO', 'Affordable studio apartment in Geelong city center', 'This studio offers budget-friendly living without compromising on comfort and convenience.', 120, 1500, 250),
(11, 11, 3, 4, true, 6, 3000, 'FEMALE', 'APARTMENT', 'Spacious apartment with ocean views on the Gold Coast', 'Wake up to stunning ocean views every day from this luxury apartment in a prime beachfront location.', 400, 4000, 700),
(12, 12, 2, 2, true, 2, 1800, 'OTHER', 'HOUSE', 'Charming cottage with garden in Fremantle', 'Experience the charm of Fremantle in this historic cottage with modern amenities and a private garden.', 250, 2200, 400),
(13, 13, 1, 1, true, 1, 1000, 'MALE', 'STUDIO', 'Modern studio apartment in Barossa Valley', 'Ideal for wine lovers, this studio is located in the heart of Barossa Valley close to renowned wineries and restaurants.', 150, 2000, 350),
(14, 14, 2, 3, true, 4, 2200, 'FEMALE', 'APARTMENT', 'Luxurious apartment with river views in Launceston CBD', 'Enjoy luxury living in this spacious apartment overlooking the Tamar River in Launceston CBD.', 300, 2800, 550),
(15, 15, 2, 2, true, 2, 1500, 'OTHER', 'HOUSE', 'Comfortable house with a backyard in Alice Springs', 'Escape the desert heat in this comfortable house with a shaded backyard and close proximity to amenities.', 200, 2500, 400),
(16, 16, 1, 1, true, 1, 800, 'MALE', 'STUDIO', 'Affordable studio apartment in Wollongong city center', 'Enjoy beachside living in this budget-friendly studio apartment located close to Wollongongs attractions.', 120, 1500, 250),
(17, 17, 3, 4, true, 6, 3000, 'FEMALE', 'APARTMENT', 'Spacious apartment with ocean views in Byron Bay', 'Live the beach lifestyle in this spacious apartment just steps away from Byron Bays famous beaches and surf spots.', 400, 4000, 700),
(18, 18, 2, 2, true, 2, 1800, 'OTHER', 'HOUSE', 'Charming house with garden in Ballarat suburbs', 'Escape to the countryside in this charming house with a well-maintained garden and easy access to amenities.', 250, 2200, 400),
(19, 19, 1, 1, true, 1, 1000, 'MALE', 'STUDIO', 'Modern studio apartment in Cairns city center', 'This studio apartment offers convenient living in the heart of Cairns close to shops, restaurants, and attractions.', 150, 2000, 350),
(20, 20, 2, 3, true, 4, 2200, 'FEMALE', 'APARTMENT', 'Luxurious apartment with vineyard views in Margaret River', 'Indulge in luxury living surrounded by vineyards and nature in this stunning apartment in Margaret River.', 300, 2800, 550);
