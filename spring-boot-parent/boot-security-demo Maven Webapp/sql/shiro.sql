

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `groupId` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(40) DEFAULT NULL,
  `groupDesc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for group_role
-- ----------------------------
DROP TABLE IF EXISTS `group_role`;
CREATE TABLE `group_role` (
  `groupId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menuId` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `menuName` varchar(40) DEFAULT NULL,
  `menuDesc` varchar(200) DEFAULT NULL,
  `link` varchar(200) DEFAULT NULL,
  `order` int ,
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into menu(pid,menuName,menuDesc,`link`,`order`)values(-1,'系统管理','系统管理',null,1);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'菜单管理','菜单管理','/menu/list',1);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'角色管理','角色管理','/menu/list',2);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'权限管理','权限管理','/permission/list',2);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'分组管理','分组管理','/group/list',2);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'用户管理','用户管理','/user/list',2);

insert into menu(pid,menuName,menuDesc,`link`,`order`)values(-1,'mq消息','mq消息',null,1);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='mq消息')a),'activemq消息','activemq消息','/admins/message/list',1);

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `menuId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permissionId` int(11) NOT NULL AUTO_INCREMENT,
  `permission` varchar(200) DEFAULT NULL,
  `desc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) DEFAULT NULL,
  `roleDesc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `roleId` int(11) DEFAULT NULL,
  `permissionId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `user`(userName,`password`,phone,`age`,sex)values('mark','mark','11111111111','22',true);
-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `userid` int(11) DEFAULT NULL,
  `groupid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `userId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--Remember Me持久化保存记录
create table PERSISTENT_LOGINS
(
  username  VARCHAR(64) not null,
  series   VARCHAR(64) not null,
  token     VARCHAR(64) not null,
  last_used DATE not null
);

alter table PERSISTENT_LOGINS
  add constraint PK_PERSISTENT_LOGIN primary key (series);
  
  
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `role` varchar(100) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;