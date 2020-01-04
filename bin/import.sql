-- 创建项目是会自动初始化数据
INSERT INTO `blog`.`user` (`id`, `avator`, `email`, `name`, `password`, `username`) VALUES ('1', NULL, '15156350313@163.com', 'zmm', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'zmm');
INSERT INTO `blog`.`user` (`id`, `avator`, `email`, `name`, `password`, `username`) VALUES ('2', NULL, '1805783671@qq.com', 'zy', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'zy');
INSERT INTO `blog`.`user` (`id`, `avator`, `email`, `name`, `password`, `username`) VALUES ('3', NULL, 'jack@qq.com', '哈姆雷特', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'jack');
INSERT INTO `blog`.`user` (`id`, `avator`, `email`, `name`, `password`, `username`) VALUES ('5', NULL, 'cat@163.com', 'TOM', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'cat');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (5, 2);
