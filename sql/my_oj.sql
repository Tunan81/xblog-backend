/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : my_db

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 17/02/2024 13:45:05
*/

SET NAMES utf8mb4;
SET
    FOREIGN_KEY_CHECKS = 0;

-- 创建数据库
create database if not exists my_oj;

-- 使用数据库
use my_oj;

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`            bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_account`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '账号',
    `user_password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '123456' COMMENT '密码',
    `user_name`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '用户昵称',
    `user_avatar`   varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '用户头像',
    `user_profile`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '用户简介',
    `user_role`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
    `gender`        int                                                            NULL     DEFAULT NULL COMMENT '性别0-男、1-女',
    `address`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '地址',
    `tags`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '标签',
    `birthday`      date                                                           NULL     DEFAULT NULL COMMENT '生日',
    `company`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '公司',
    `position`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '职位',
    `school`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '就读学校',
    `phone`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '电话',
    `email`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '邮箱',
    `website`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '个人网站',
    `status`        int                                                            NOT NULL DEFAULT 0 COMMENT '状态：0-正常，1-禁用',
    `update_time`   datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time`   datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_delete`     tinyint                                                        NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 88949137860489217
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 题目表
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`
(
    `id`           bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '标题',
    `content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '内容',
    `tags`         varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '标签列表（json 数组）',
    `difficulty`   int(1) UNSIGNED ZEROFILL                                       NULL     DEFAULT 0 COMMENT '难度',
    `submit_num`   int                                                            NOT NULL DEFAULT 0 COMMENT '题目提交次数',
    `accept_num`   int                                                            NOT NULL DEFAULT 0 COMMENT '题目通过次数',
    `judge_case`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '判题用例（Json数组）',
    `judge_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '判题配置（Json对象）',
    `thumb_num`    int                                                            NOT NULL DEFAULT 0 COMMENT '点赞数',
    `favour_num`   int                                                            NOT NULL DEFAULT 0 COMMENT '收藏数',
    `user_id`      bigint                                                         NOT NULL COMMENT '创建用户 id',
    `create_time`  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`    tinyint                                                        NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_userId` (`user_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 115738602877427713
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '题目表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 题目评论表
-- ----------------------------
DROP TABLE IF EXISTS `question_comment`;
CREATE TABLE `question_comment`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `parent_id`   int(0)                                                        NULL     DEFAULT NULL COMMENT '父节点',
    `question_id` bigint                                                        NOT NULL COMMENT '题目 id',
    `user_id`     bigint                                                        NOT NULL COMMENT '评论用户 id',
    `address`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '地址',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '评论内容',
    `likes`       int(0)                                                        NULL     DEFAULT 0 COMMENT '点赞数',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_question_comment_question_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
    CONSTRAINT `fk_question_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='题目评论表';

-- ----------------------------
-- 题目点赞表
-- ----------------------------
DROP TABLE IF EXISTS `question_thumb`;
CREATE TABLE `question_thumb`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `question_id` bigint   NOT NULL COMMENT '题目 id',
    `user_id`     bigint   NOT NULL COMMENT '点赞用户 id',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_like` (`question_id`, `user_id`),
    CONSTRAINT `fk_question_like_question_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
    CONSTRAINT `fk_question_like_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='题目点赞表';

-- ----------------------------
-- 题目收藏表
-- ----------------------------
CREATE TABLE `question_favorite`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `question_id` bigint   NOT NULL COMMENT '题目 id',
    `user_id`     bigint   NOT NULL COMMENT '收藏用户 id',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_favorite` (`question_id`, `user_id`),
    CONSTRAINT `fk_question_favorite_question_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
    CONSTRAINT `fk_question_favorite_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='题目收藏表';

-- ----------------------------
-- 题解表
-- ----------------------------
DROP TABLE IF EXISTS `question_answer`;
CREATE TABLE `answer`
(
    `id`          bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `question_id` bigint                                                         NOT NULL COMMENT '题目 id',
    `user_id`     bigint                                                         NOT NULL COMMENT '回答用户 id',
    `title`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL COMMENT '题解标题',
    `tags`        varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '标签列表（json 数组）',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '题解内容',
    `create_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint                                                        NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_answer_question_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
    CONSTRAINT `fk_answer_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='题解表';

-- ----------------------------
-- 题解评论表
-- ----------------------------
DROP TABLE IF EXISTS `question_answer_comment`;
CREATE TABLE `question_answer_comment`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `parent_id`   int(0)                                                        NULL     DEFAULT NULL COMMENT '父节点',
    `answer_id`   bigint                                                        NOT NULL COMMENT '题解 id',
    `user_id`     bigint                                                        NOT NULL COMMENT '评论用户 id',
    `address`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '地址',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '评论内容',
    `likes`       int(0)                                                        NULL     DEFAULT 0 COMMENT '点赞数',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_answer_comment_answer_id` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`),
    CONSTRAINT `fk_answer_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='题解评论表';


-- ----------------------------
-- 题目提交表
-- ----------------------------
DROP TABLE IF EXISTS `question_submit`;
CREATE TABLE `question_submit`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `language`    varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '编程语言',
    `code`        text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         NOT NULL COMMENT '用户代码',
    `judge_info`  text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci         NULL COMMENT '判题信息（Json数组）',
    `status`      int                                                           NOT NULL DEFAULT 0 COMMENT '判题状态：0-待判题，1-判题中，2-成功，3-失败',
    `question_id` bigint                                                        NOT NULL COMMENT '题目 id',
    `user_id`     bigint                                                        NOT NULL COMMENT '创建用户 id',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_userId` (`user_id` ASC) USING BTREE,
    INDEX `idx_questionId` (`question_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 115913616624955393
  CHARACTER SET = utf8mb3
  COLLATE = utf8mb3_general_ci COMMENT = '题目提交表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- 帖子表
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`       varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '标题',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NULL COMMENT '内容',
    `tags`        varchar(1024)                                                 null comment '标签列表（json 数组）',
    `cover`       varchar(1024)                                                 null comment '封面图',
    `thumbNum`    int                                                                    default 0 not null comment '点赞数',
    `favourNum`   int                                                                    default 0 not null comment '收藏数',
    `user_id`     bigint                                                        NOT NULL COMMENT '创建用户 id',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_post_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='帖子表';

-- ----------------------------
-- 帖子点赞表
-- ----------------------------
DROP TABLE IF EXISTS `post_thumb`;
CREATE TABLE `post_thumb`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `post_id`     bigint   NOT NULL COMMENT '题目 id',
    `user_id`     bigint   NOT NULL COMMENT '点赞用户 id',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_like` (`post_id`, `user_id`),
    CONSTRAINT `fk_post_like_post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
    CONSTRAINT `fk_post_like_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='帖子点赞表';

-- ----------------------------
-- 帖子收藏表
-- ----------------------------
DROP TABLE IF EXISTS `post_favorite`;
CREATE TABLE `post_favorite`
(
    `id`          bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `post_id`     bigint   NOT NULL COMMENT '题目 id',
    `user_id`     bigint   NOT NULL COMMENT '收藏用户 id',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_favorite` (`post_id`, `user_id`),
    CONSTRAINT `fk_post_favorite_post_id` FOREIGN KEY (`post_id`) REFERENCES `question` (`id`),
    CONSTRAINT `fk_post_favorite_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='帖子收藏表';

-- ----------------------------
-- 帖子评论表
-- ----------------------------
DROP TABLE IF EXISTS `post_comment`;
CREATE TABLE `post_comment`
(
    `id`          bigint                                                NOT NULL AUTO_INCREMENT COMMENT 'id',
    `post_id`     bigint                                                NOT NULL COMMENT '帖子 id',
    `user_id`     bigint                                                NOT NULL COMMENT '评论用户 id',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '评论内容',
    `create_time` datetime                                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_comment_post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
    CONSTRAINT `fk_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='帖子评论表';

-- ----------------------------
-- 队伍表
-- ----------------------------
create table team
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(256)                       not null comment '队伍名称',
    description varchar(1024)                      null comment '描述',
    maxNum      int      default 1                 not null comment '最大人数',
    expire_time datetime                           null comment '过期时间',
    userId      bigint comment '用户id（队长 id）',
    status      int      default 0                 not null comment '0 - 公开，1 - 私有，2 - 加密',
    password    varchar(512)                       null comment '密码',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_delete   tinyint  default 0                 not null comment '是否删除'
) comment '队伍';

-- 用户队伍关系
create table user_team
(
    id          bigint auto_increment comment 'id'
        primary key,
    user_id     bigint comment '用户id',
    team_id     bigint comment '队伍id',
    join_time   datetime                           null comment '加入时间',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_delete   tinyint  default 0                 not null comment '是否删除'
) comment '用户队伍关系';

-- ----------------------------
-- 竞赛表
-- ----------------------------
DROP TABLE IF EXISTS `competition`;
create table competition
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(256)                       not null comment '竞赛名称',
    description varchar(1024)                      null comment '描述',
    maxNum      int      default 1                 not null comment '最大人数',
    start_time  datetime                           not null comment '开始时间',
    expire_time datetime                           null comment '过期时间',
    user_id     bigint comment '用户id（创建者 id）',
    status      int      default 0                 not null comment '0 - 公开，1 - 私有，2 - 加密',
    password    varchar(512)                       null comment '密码',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint  default 0                 not null comment '是否删除'
) comment '竞赛';

-- ----------------------------
-- 用户竞赛关系
-- ----------------------------
DROP TABLE IF EXISTS `user_competition`;
create table user_competition
(
    id             bigint auto_increment comment 'id'
        primary key,
    user_id        bigint comment '用户id',
    competition_id bigint comment '竞赛id',
    join_time      datetime                           null comment '加入时间',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_delete      tinyint  default 0                 not null comment '是否删除'
) comment '用户竞赛关系';

-- ----------------------------
-- 竞赛题目表
-- ----------------------------
DROP TABLE IF EXISTS `competition_question`;
create table competition_question
(
    id             bigint auto_increment comment 'id'
        primary key,
    competition_id bigint comment '竞赛id',
    question_id    bigint comment '题目id',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_delete      tinyint  default 0                 not null comment '是否删除'
) comment '竞赛题目表';

-- ----------------------------
-- 竞赛排名表
-- ----------------------------
DROP TABLE IF EXISTS `competition_rank`;
create table competition_rank
(
    id                 bigint auto_increment comment 'id'
        primary key,
    competition_id     bigint comment '竞赛id',
    userId             bigint comment '用户id',
    user_name          varchar(256)                                          null comment '用户名',
    total_memory       int      default 0                                    not null comment '总内存',
    total_time         int      default 0                                    not null comment '总时间',
    totalScore         int      default 0                                    not null comment '总分数',
    score              int      default 0                                    not null comment '分数',
    competition_detail text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci null comment '竞赛详情',
    create_time        datetime default CURRENT_TIMESTAMP                    null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP                    null on update CURRENT_TIMESTAMP,
    is_delete          tinyint  default 0                                    not null comment '是否删除'
) comment '竞赛排名表';

-- ----------------------------
-- 文件表
-- ----------------------------
DROP TABLE IF EXISTS `file`;
create table file
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(256)                       not null comment '文件名',
    description varchar(256)                       null comment '描述',
    url         varchar(1024)                      not null comment '文件地址',
    type        varchar(256)                       not null comment '文件类型/后缀',
    size        bigint                             not null comment '文件大小',
    userId      bigint                             not null comment '用户id',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    sort        varchar(128)                       null comment '分类（前端、后端、行业研究）',
    `usage`     varchar(256)                       not null comment '文件用途，例如：图片、资源等',
    downloadNum int      default 0                 not null comment '下载次数',
    status      int      default 0                 not null comment '0 - 未审核，1 - 审核通过，2 - 审核未通过',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_delete   tinyint  default 0                 not null comment '是否删除'
) comment '文件表';

-- ----------------------------
-- 用户文件关系表
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;
create table user_file
(
    id          bigint auto_increment comment 'id'
        primary key,
    user_id     bigint comment '用户id',
    file_id     bigint comment '文件id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_delete   tinyint  default 0                 not null comment '是否删除'
) comment '用户文件关系表';

SET
    FOREIGN_KEY_CHECKS = 1;



DROP TABLE IF EXISTS `social_user`;
CREATE TABLE `social_user`
(
    `id`     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uuid`   bigint(20)   NOT NULL COMMENT '第三方系统的唯一ID',
    `source` varchar(128) NOT NULL COMMENT '第三方系统名称',
    `access_token` varchar(256) DEFAULT NULL COMMENT 'accessToken',
    `expire_in` bigint(20) DEFAULT NULL COMMENT '过期时间',
    `refresh_token` varchar(256) DEFAULT NULL COMMENT 'refreshToken',
    `open_id` varchar(256) DEFAULT NULL COMMENT 'openId',
    `uid` bigint(20) DEFAULT NULL COMMENT '第三方用户的ID',
    `access_code` varchar(256) DEFAULT NULL COMMENT 'accessCode',
    `union_id` varchar(256) DEFAULT NULL COMMENT 'unionId',
    `scope` varchar(256) DEFAULT NULL COMMENT 'scope',
    `token_type` varchar(256) DEFAULT NULL COMMENT 'tokenType',
    `id_token` varchar(256) DEFAULT NULL COMMENT 'idToken',
    `mac_algorithm` varchar(256) DEFAULT NULL COMMENT 'macAlgorithm',
    `mac_key` varchar(256) DEFAULT NULL COMMENT 'macKey',
    `code` varchar(256) DEFAULT NULL COMMENT 'code',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='社交用户信息表';

DROP TABLE IF EXISTS `social_user_auth`;


CREATE TABLE `social_user_auth`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `social_user_id` bigint(20) NOT NULL COMMENT '社交用户ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='社交用户认证表';