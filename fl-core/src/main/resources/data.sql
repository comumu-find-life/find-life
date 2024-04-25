insert into user (user_id,role, nick_name, email, profile_url, password, brith, phone_number,  nationality)
VALUES ('1','GETTER', 'nickname', 'test@email.com', 'profileUrl', '$2a$10$jtkeHmjS4QBwiHFloIk31uvcYGBhCZ7madgWWbb8d2X8jUGmI.PAi', '001031', '01012341234', 'Korean');

--insert into home_address(home_address_id, state, city, post_code, detail_address,street_name, street_number, latitude, longitude)
--values  ('3', 'VAC', '멜버른', 13234, '401호', '거리이름', '123fa', 20.13, 502.13);
--
--insert into home(home_id, user_id, home_address_id, bath_room_count, people_count, bond, short_introduce, introduce, bill, view_count, type)
--values ('1', '1','3', 10, 10, 20000,'짧은 소개', '소개글', 3000000, 10, 'RENT');


-- 홈 주소 생성
insert into home_address(home_address_id, state, city, post_code, detail_address, street_name, street_number, latitude, longitude)
values
    ('1','VAC', '멜버른', 13234, '401호', '거리이름1', '123fa', 20.13, 502.13),
    ('2','VAC', '멜버른', 13235, '402호', '거리이름2', '124fa', 20.14, 503.14),
    ('3','VAC', '멜버른', 13236, '403호', '거리이름3', '125fa', 20.15, 504.15),
    ('4','VAC', '멜버른', 13237, '404호', '거리이름4', '126fa', 20.16, 505.16),
    ('5','VAC', '멜버른', 13238, '405호', '거리이름5', '127fa', 20.17, 506.17),
    ('6','VAC', '멜버른', 13239, '406호', '거리이름6', '128fa', 20.18, 507.18),
    ('7','VAC', '멜버른', 13240, '407호', '거리이름7', '129fa', 20.19, 508.19),
    ('8','VAC', '멜버른', 13241, '408호', '거리이름8', '130fa', 20.20, 509.20),
    ('9','VAC', '멜버른', 13242, '409호', '거리이름9', '131fa', 20.21, 510.21),
    ('10','VAC', '멜버른', 13243, '410호', '거리이름10', '132fa', 20.22, 511.22),
    ('11','VAC', '멜버른', 13244, '411호', '거리이름11', '133fa', 20.23, 512.23),
    ('12','VAC', '멜버른', 13245, '412호', '거리이름12', '134fa', 20.24, 513.24),
    ('13','VAC', '멜버른', 13246, '413호', '거리이름13', '135fa', 20.25, 514.25),
    ('14','VAC', '멜버른', 13247, '414호', '거리이름14', '136fa', 20.26, 515.26),
    ('15','VAC', '멜버른', 13248, '415호', '거리이름15', '137fa', 20.27, 516.27);

-- 홈 생성 및 홈 주소 지정
insert into home(user_id, home_address_id, bath_room_count, people_count, bond, short_introduce, introduce, bill, view_count, type)
values
    ('1', '1', 10, 10, 20000, '짧은 소개1', '소개글1', 3000000, 10, 'RENT'),
    ('1', '2', 8, 8, 15000, '짧은 소개2', '소개글2', 2500000, 8, 'RENT'),
    ('1', '3', 12, 12, 25000, '짧은 소개3', '소개글3', 3500000, 12, 'RENT'),
    ('1', '4', 10, 10, 20000, '짧은 소개4', '소개글4', 3000000, 10, 'RENT'),
    ('1', '5', 8, 8, 15000, '짧은 소개5', '소개글5', 2500000, 8, 'RENT'),
    ('1', '6', 12, 12, 25000, '짧은 소개6', '소개글6', 3500000, 12, 'RENT'),
    ('1', '7', 10, 10, 20000, '짧은 소개7', '소개글7', 3000000, 10, 'RENT'),
    ('1', '8', 8, 8, 15000, '짧은 소개8', '소개글8', 2500000, 8, 'RENT'),
    ('1', '9', 12, 12, 25000, '짧은 소개9', '소개글9', 3500000, 12, 'RENT'),
    ('1', '10', 10, 10, 20000, '짧은 소개10', '소개글10', 3000000, 10, 'RENT'),
    ('1', '11', 8, 8, 15000, '짧은 소개11', '소개글11', 2500000, 8, 'RENT'),
    ('1', '12', 12, 12, 25000, '짧은 소개12', '소개글12', 3500000, 12, 'RENT'),
    ('1', '13', 10, 10, 20000, '짧은 소개13', '소개글13', 3000000, 10, 'RENT'),
    ('1', '14', 8, 8, 15000, '짧은 소개14', '소개글14', 2500000, 8, 'RENT'),
    ('1', '15', 12, 12, 25000, '짧은 소개15', '소개글15', 3500000, 12, 'RENT');


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
