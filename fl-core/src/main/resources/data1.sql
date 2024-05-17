insert into user (role, nick_name, email, profile_url, password, brith, phone_number,  nationality)
VALUES ('GETTER', 'nickname', 'test@email.com', 'profileUrl', '$2a$10$jtkeHmjS4QBwiHFloIk31uvcYGBhCZ7madgWWbb8d2X8jUGmI.PAi', '001031', '01012341234', 'Korean'),
    ('GETTER', 'Jason', 'Jason@email.com', 'profileUrl', '{bcrypt}$2a$10$pouEYC8pSdHrJuxRInPS/.F4G4HVFcAm0CouviPhcX0zDnHyPYvze', '001031', '01012341234', 'Korean'),
    ('GETTER', 'Nick', 'nick@email.com', 'profileUrl', '{bcrypt}$2a$10$pouEYC8pSdHrJuxRInPS/.F4G4HVFcAm0CouviPhcX0zDnHyPYvze', '001031', '01012341234', 'Korean');


--insert into home_address(home_address_id, state, city, post_code, detail_address,street_name, street_number, latitude, longitude)
--values  ('3', 'VAC', '멜버른', 13234, '401호', '거리이름', '123fa', 20.13, 502.13);
--
--insert into home(home_id, user_id, home_address_id, bath_room_count, people_count, bond, short_introduce, introduce, bill, view_count, type)
--values ('1', '1','3', 10, 10, 20000,'짧은 소개', '소개글', 3000000, 10, 'RENT');


-- 홈 주소 생성
insert into home_address(state, city, post_code, detail_address, street_name, street_number, latitude, longitude)
values
    ('VIC', 'Melbourne', 3000, '401호', '거리이름1', '123fa', 20.13, 502.13),
    ('VIC', 'Melbourne', 3000, '402호', '거리이름2', '124fa', 20.14, 503.14),
    ('VIC', 'Melbourne', 3000, '403호', '거리이름3', '125fa', 20.15, 504.15),
    ('VIC', 'Melbourne', 3000, '404호', '거리이름4', '126fa', 20.16, 505.16),
    ('VIC', 'Melbourne', 3000, '405호', '거리이름5', '127fa', 20.17, 506.17),
    ('VIC', 'Melbourne', 3000, '406호', '거리이름6', '128fa', 20.18, 507.18),
    ('VIC', 'Melbourne', 3000, '407호', '거리이름7', '129fa', 20.19, 508.19),
    ('NSW', 'Sydney', 3223, '408호', '거리이름8', '130fa', 20.20, 509.20),
    ('NSW', 'Sydney', 3223, '409호', '거리이름9', '131fa', 20.21, 510.21),
    ('NSW', 'Sydney', 3223, '410호', '거리이름10', '132fa', 20.22, 511.22),
    ('VIC', 'Melbourne', 13244, '411호', '거리이름11', '133fa', 20.23, 512.23),
    ('VIC', 'Melbourne', 13245, '412호', '거리이름12', '134fa', 20.24, 513.24),
    ('VIC', 'Melbourne', 13246, '413호', '거리이름13', '135fa', 20.25, 514.25),
    ('VIC', 'Melbourne', 13247, '414호', '거리이름14', '136fa', 20.26, 515.26),
    ('VIC', 'Melbourne', 13248, '415호', '거리이름15', '137fa', 20.27, 516.27);
-- 홈 생성 및 홈 주소 지정
insert into home(user_id, rent, home_address_id, bedroom_count, bath_room_count, people_count, bond, short_introduce, introduce, bill, view_count, type, deal_savable)
values
    ('1', 270, '1', 2, 1, 10, 540, '짧은 소개1', '소개글1', 300, 10, 'RENT', 0),
    ('1', 270, '2', 2, 2, 8, 540, '짧은 소개2', '소개글2', 250, 8, 'RENT', 0),
    ('1', 220, '3', 2, 2, 12, 440, '짧은 소개3', '소개글3', 350, 12, 'RENT', 0),
    ('1', 270, '4', 2, 1, 10, 540, '짧은 소개4', '소개글4', 300, 10, 'RENT', 0),
    ('1', 350, '5', 2, 1, 8, 1400, '짧은 소개5', '소개글5', 250, 8, 'RENT', 0),
    ('1', 450, '6', 2, 1, 12, 900, '짧은 소개6', '소개글6', 350, 12, 'RENT', 0),
    ('1', 550, '7', 2, 2, 10, 550, '짧은 소개7', '소개글7', 300, 10, 'RENT', 0),
    ('1', 270, '8', 3, 2, 8, 270, '짧은 소개8', '소개글8', 250, 8, 'RENT', 0),
    ('1', 270, '9', 2, 3, 12, 540, '짧은 소개9', '소개글9', 350, 12, 'RENT', 0),
    ('1', 270, '10', 2, 3, 10, 20000, '짧은 소개10', '소개글10', 300, 10, 'RENT', 0),
    ('1', 520, '11', 2, 2, 8, 15000, '짧은 소개11', '소개글11', 250, 8, 'RENT', 0),
    ('1', 270, '12', 2, 1, 12, 25000, '짧은 소개12', '소개글12', 35, 12, 'RENT', 0),
    ('1', 270, '13', 2, 2, 10, 20000, '짧은 소개13', '소개글13', 300000, 10, 'RENT', 0),
    ('1', 380, '14', 2, 2, 8, 15000, '짧은 소개14', '소개글14', 2500000, 8, 'RENT', 0),
    ('1', 270, '15', 2, 1, 12, 25000, '짧은 소개15', '소개글15', 3500000, 12, 'RENT', 0);


insert into home_image(home_image_id, image_url, home_id)
values
    ('1', '이미지 url', '1'),
    ('2', '이미지 url', '2'),
    ('3', '이미지 url', '3'),
    ('4', '이미지 url', '4'),
    ('5', '이미지 url', '5'),
    ('6', '이미지 url', '6'),
    ('7', '이미지 url', '7'),
    ('8', '이미지 url', '8'),
    ('9', '이미지 url', '9'),
    ('10', '이미지 url', '10'),
    ('11', '이미지 url', '11'),
    ('12', '이미지 url', '12'),
    ('13', '이미지 url', '13'),
    ('14', '이미지 url', '14'),
    ('15', '이미지 url', '15')
