
create region --name=menu --type=REPLICATE_PERSISTENT 

insert into menu(pid,menuName,menuDesc,`link`,`order`)values(-1,'系统管理','系统管理',null,1);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'菜单管理','菜单管理','/menu/list',1);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'角色管理','角色管理','/menu/list',2);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'权限管理','权限管理','/permission/list',2);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'分组管理','分组管理','/group/list',2);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='系统管理')a),'用户管理','用户管理','/user/list',2);

insert into menu(pid,menuName,menuDesc,`link`,`order`)values(-1,'mq消息','mq消息',null,1);
insert into menu(pid,menuName,menuDesc,`link`,`order`)values((select a.menuId from (select menuId from menu where menuName='mq消息')a),'activemq消息','activemq消息','/admins/message/list',1);

create region --name=menu_role --type=REPLICATE_PERSISTENT 

create region --name=permission --type=REPLICATE_PERSISTENT 

create region --name=role --type=REPLICATE_PERSISTENT 

create region --name=role_permission --type=REPLICATE_PERSISTENT 

create region --name=user --type=REPLICATE_PERSISTENT 
insert into `user`(userName,`password`,phone,`age`,sex)values('mark','mark','11111111111','22',true);

create region --name=user_group --type=REPLICATE_PERSISTENT 

create region --name=user_role --type=REPLICATE_PERSISTENT
--Remember Me持久化保存记录

create region --name=PERSISTENT_LOGINS --type=REPLICATE_PERSISTENT
alter table PERSISTENT_LOGINS
  add constraint PK_PERSISTENT_LOGIN primary key (series);
  
create region --name=resource --type=REPLICATE_PERSISTENT