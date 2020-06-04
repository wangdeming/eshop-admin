/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50719
Source Host           : 172.16.0.200:3030
Source Database       : account

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2019-02-19 14:04:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dept`
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` bigint(11) DEFAULT NULL COMMENT '父部门id',
  `pids` varchar(255) DEFAULT NULL COMMENT '父级ids',
  `simplename` varchar(45) DEFAULT NULL COMMENT '简称',
  `fullname` varchar(255) DEFAULT NULL COMMENT '全称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('24', '1', '0', '[0],', '总公司', '总公司', '', null);
INSERT INTO `dept` VALUES ('25', '2', '24', '[0],[24],', '开发部', '开发部', '', null);
INSERT INTO `dept` VALUES ('26', '3', '24', '[0],[24],', '运营部', '运营部', '', null);
INSERT INTO `dept` VALUES ('27', '4', '24', '[0],[24],', '战略部', '战略部', '', null);

-- ----------------------------
-- Table structure for `dict`
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` bigint(11) DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('16', '0', '0', '状态', null);
INSERT INTO `dict` VALUES ('17', '1', '16', '启用', null);
INSERT INTO `dict` VALUES ('18', '2', '16', '禁用', null);
INSERT INTO `dict` VALUES ('29', '0', '0', '性别', null);
INSERT INTO `dict` VALUES ('30', '1', '29', '男', null);
INSERT INTO `dict` VALUES ('31', '2', '29', '女', null);
INSERT INTO `dict` VALUES ('35', '0', '0', '账号状态', null);
INSERT INTO `dict` VALUES ('36', '1', '35', '启用', null);
INSERT INTO `dict` VALUES ('37', '2', '35', '冻结', null);
INSERT INTO `dict` VALUES ('38', '3', '35', '已删除', null);

-- ----------------------------
-- Table structure for `login_log`
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` bigint(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` bigint(65) DEFAULT NULL COMMENT '管理员id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否执行成功',
  `message` text COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8 COMMENT='登录记录';

-- ----------------------------
-- Records of login_log
-- ----------------------------
INSERT INTO `login_log` VALUES ('190', '登录日志', '1', '2018-04-04 10:59:01', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('191', '登录日志', '1', '2018-04-04 11:42:36', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('192', '登录日志', '1', '2018-04-04 11:46:03', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('193', '登录日志', '1', '2019-01-18 16:27:11', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('194', '登录日志', '1', '2019-01-18 16:27:11', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('195', '登录日志', '1', '2019-01-19 10:34:13', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('196', '登录日志', '1', '2019-01-19 10:34:58', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('197', '登录日志', '1', '2019-01-19 10:57:41', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('198', '登录日志', '1', '2019-01-19 11:00:19', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('199', '登录日志', '1', '2019-01-19 11:11:24', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('200', '登录日志', '1', '2019-01-21 08:39:49', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('201', '登录日志', '1', '2019-01-21 09:18:34', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('202', '登录日志', '1', '2019-01-21 11:03:01', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('203', '登录日志', '1', '2019-01-21 11:14:30', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('204', '登录日志', '1', '2019-01-28 08:48:51', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('205', '登录日志', '1', '2019-01-28 13:36:11', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('206', '登录日志', '1', '2019-01-28 13:36:11', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('207', '退出日志', '1', '2019-01-28 13:39:06', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('208', '登录日志', '1', '2019-01-28 13:39:09', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('209', '登录日志', '1', '2019-01-28 14:08:10', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('210', '登录失败日志', null, '2019-01-28 15:45:18', '成功', '账号:lsadmin,账号密码错误', '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('211', '登录日志', '1', '2019-01-28 15:45:22', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('212', '登录日志', '1', '2019-01-29 08:42:20', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('213', '退出日志', '1', '2019-01-29 08:42:43', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('214', '登录日志', '1', '2019-01-29 08:42:54', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('215', '登录日志', '1', '2019-01-29 13:42:01', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('216', '退出日志', '1', '2019-01-29 13:42:03', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('217', '登录日志', '1', '2019-01-29 13:42:07', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('218', '退出日志', '1', '2019-01-29 13:43:33', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('219', '登录日志', '1', '2019-01-29 13:43:35', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('220', '登录日志', '1', '2019-01-31 09:36:19', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('221', '登录日志', '1', '2019-02-01 14:03:44', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('222', '登录日志', '1', '2019-02-01 14:42:45', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('223', '登录日志', '1', '2019-02-19 10:29:07', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('224', '登录日志', '1', '2019-02-19 10:51:48', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('225', '登录日志', '1', '2019-02-19 11:09:52', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('226', '登录日志', '1', '2019-02-19 11:35:35', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('227', '登录日志', '1', '2019-02-19 13:55:08', '成功', null, '0:0:0:0:0:0:0:1');
INSERT INTO `login_log` VALUES ('228', '登录日志', '1', '2019-02-19 14:00:18', '成功', null, '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(65) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `ismenu` int(11) DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('105', 'system', '0', '[0],', '系统管理', 'fa-user', '', '3', '1', '1', null, '1', '1');
INSERT INTO `menu` VALUES ('106', 'mgr', 'system', '[0],[system],', '用户管理', '', '/mgr', '1', '2', '1', null, '1', '0');
INSERT INTO `menu` VALUES ('107', 'mgr_add', 'mgr', '[0],[system],[mgr],', '添加用户', null, '/mgr/add', '1', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('108', 'mgr_edit', 'mgr', '[0],[system],[mgr],', '修改用户', null, '/mgr/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('109', 'mgr_delete', 'mgr', '[0],[system],[mgr],', '删除用户', null, '/mgr/delete', '3', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('110', 'mgr_reset', 'mgr', '[0],[system],[mgr],', '重置密码', null, '/mgr/reset', '4', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('111', 'mgr_freeze', 'mgr', '[0],[system],[mgr],', '冻结用户', null, '/mgr/freeze', '5', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('112', 'mgr_unfreeze', 'mgr', '[0],[system],[mgr],', '解除冻结用户', null, '/mgr/unfreeze', '6', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('113', 'mgr_setRole', 'mgr', '[0],[system],[mgr],', '分配角色', null, '/mgr/setRole', '7', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('114', 'role', 'system', '[0],[system],', '角色管理', null, '/role', '2', '2', '1', null, '1', '0');
INSERT INTO `menu` VALUES ('115', 'role_add', 'role', '[0],[system],[role],', '添加角色', null, '/role/add', '1', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('116', 'role_edit', 'role', '[0],[system],[role],', '修改角色', null, '/role/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('117', 'role_remove', 'role', '[0],[system],[role],', '删除角色', null, '/role/remove', '3', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('118', 'role_setAuthority', 'role', '[0],[system],[role],', '配置权限', null, '/role/setAuthority', '4', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('119', 'menu', 'system', '[0],[system],', '菜单管理', null, '/menu', '4', '2', '1', null, '1', '0');
INSERT INTO `menu` VALUES ('120', 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', null, '/menu/add', '1', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('121', 'menu_edit', 'menu', '[0],[system],[menu],', '修改菜单', null, '/menu/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('122', 'menu_remove', 'menu', '[0],[system],[menu],', '删除菜单', null, '/menu/remove', '3', '3', '0', null, '1', '0');
INSERT INTO `menu` VALUES ('128', 'log', 'system', '[0],[system],', '业务日志', null, '/log', '6', '2', '1', null, '1', '0');
INSERT INTO `menu` VALUES ('130', 'druid', 'system', '[0],[system],', '监控管理', null, '/druid', '7', '2', '1', null, '1', null);
INSERT INTO `menu` VALUES ('131', 'dept', 'system', '[0],[system],', '部门管理', null, '/dept', '3', '2', '1', null, '1', null);
INSERT INTO `menu` VALUES ('132', 'dict', 'system', '[0],[system],', '字典管理', null, '/dict', '4', '2', '1', null, '1', null);
INSERT INTO `menu` VALUES ('133', 'loginLog', 'system', '[0],[system],', '登录日志', null, '/loginLog', '6', '2', '1', null, '1', null);
INSERT INTO `menu` VALUES ('134', 'log_clean', 'log', '[0],[system],[log],', '清空日志', null, '/log/delLog', '3', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('135', 'dept_add', 'dept', '[0],[system],[dept],', '添加部门', null, '/dept/add', '1', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('136', 'dept_update', 'dept', '[0],[system],[dept],', '修改部门', null, '/dept/update', '1', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('137', 'dept_delete', 'dept', '[0],[system],[dept],', '删除部门', null, '/dept/delete', '1', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('138', 'dict_add', 'dict', '[0],[system],[dict],', '添加字典', null, '/dict/add', '1', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('139', 'dict_update', 'dict', '[0],[system],[dict],', '修改字典', null, '/dict/update', '1', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('140', 'dict_delete', 'dict', '[0],[system],[dict],', '删除字典', null, '/dict/delete', '1', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('141', 'notice', 'system', '[0],[system],', '通知管理', null, '/notice', '9', '2', '1', null, '1', null);
INSERT INTO `menu` VALUES ('142', 'notice_add', 'notice', '[0],[system],[notice],', '添加通知', null, '/notice/add', '1', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('143', 'notice_update', 'notice', '[0],[system],[notice],', '修改通知', null, '/notice/update', '2', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('144', 'notice_delete', 'notice', '[0],[system],[notice],', '删除通知', null, '/notice/delete', '3', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('150', 'to_menu_edit', 'menu', '[0],[system],[menu],', '菜单编辑跳转', '', '/menu/menu_edit', '4', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('151', 'menu_list', 'menu', '[0],[system],[menu],', '菜单列表', '', '/menu/list', '5', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('152', 'to_dept_update', 'dept', '[0],[system],[dept],', '修改部门跳转', '', '/dept/dept_update', '4', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('153', 'dept_list', 'dept', '[0],[system],[dept],', '部门列表', '', '/dept/list', '5', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('154', 'dept_detail', 'dept', '[0],[system],[dept],', '部门详情', '', '/dept/detail', '6', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('155', 'to_dict_edit', 'dict', '[0],[system],[dict],', '修改菜单跳转', '', '/dict/dict_edit', '4', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('156', 'dict_list', 'dict', '[0],[system],[dict],', '字典列表', '', '/dict/list', '5', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('157', 'dict_detail', 'dict', '[0],[system],[dict],', '字典详情', '', '/dict/detail', '6', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('158', 'log_list', 'log', '[0],[system],[log],', '日志列表', '', '/log/list', '2', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('159', 'log_detail', 'log', '[0],[system],[log],', '日志详情', '', '/log/detail', '3', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('160', 'del_login_log', 'loginLog', '[0],[system],[loginLog],', '清空登录日志', '', '/loginLog/delLoginLog', '1', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('161', 'login_log_list', 'loginLog', '[0],[system],[loginLog],', '登录日志列表', '', '/loginLog/list', '2', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('162', 'to_role_edit', 'role', '[0],[system],[role],', '修改角色跳转', '', '/role/role_edit', '5', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('163', 'to_role_assign', 'role', '[0],[system],[role],', '角色分配跳转', '', '/role/role_assign', '6', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('164', 'role_list', 'role', '[0],[system],[role],', '角色列表', '', '/role/list', '7', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('165', 'to_assign_role', 'mgr', '[0],[system],[mgr],', '分配角色跳转', '', '/mgr/role_assign', '8', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('166', 'to_user_edit', 'mgr', '[0],[system],[mgr],', '编辑用户跳转', '', '/mgr/user_edit', '9', '3', '0', null, '1', null);
INSERT INTO `menu` VALUES ('167', 'mgr_list', 'mgr', '[0],[system],[mgr],', '用户列表', '', '/mgr/list', '10', '3', '0', null, '1', null);

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` text COMMENT '内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` bigint(11) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='通知表';

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('6', '世界', '10', '欢迎使用Guns管理系统', '2017-01-11 08:53:20', '1');
INSERT INTO `notice` VALUES ('8', '你好', null, '你好<p><br></p>', '2017-05-10 19:28:57', '1');

-- ----------------------------
-- Table structure for `operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` bigint(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logtype` varchar(255) DEFAULT NULL COMMENT '日志类型',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` bigint(65) DEFAULT NULL COMMENT '用户id',
  `classname` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method` text COMMENT '方法名称',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否成功',
  `message` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=569 DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of operation_log
-- ----------------------------
INSERT INTO `operation_log` VALUES ('568', '业务日志', '清空业务日志', '1', 'cn.ibdsr.web.modular.system.controller.LogController', 'delLog', '2019-02-19 14:03:17', '成功', '主键id=null');

-- ----------------------------
-- Table structure for `person`
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `age` tinyint(4) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('2', '徐工', '11', '13111111111', '11.1', '24');
INSERT INTO `person` VALUES ('3', '肖工', '11', '13111111111', '11.1', '24');
INSERT INTO `person` VALUES ('4', '朱工', '11', '13111111111', '11.1', '24');
INSERT INTO `person` VALUES ('5', '张工', '11', '13111111111', '11.1', '24');

-- ----------------------------
-- Table structure for `relation`
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuid` bigint(11) DEFAULT NULL COMMENT '菜单id',
  `roleid` bigint(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4019 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of relation
-- ----------------------------
INSERT INTO `relation` VALUES ('3377', '105', '5');
INSERT INTO `relation` VALUES ('3378', '106', '5');
INSERT INTO `relation` VALUES ('3379', '107', '5');
INSERT INTO `relation` VALUES ('3380', '108', '5');
INSERT INTO `relation` VALUES ('3381', '109', '5');
INSERT INTO `relation` VALUES ('3382', '110', '5');
INSERT INTO `relation` VALUES ('3383', '111', '5');
INSERT INTO `relation` VALUES ('3384', '112', '5');
INSERT INTO `relation` VALUES ('3385', '113', '5');
INSERT INTO `relation` VALUES ('3386', '114', '5');
INSERT INTO `relation` VALUES ('3387', '115', '5');
INSERT INTO `relation` VALUES ('3388', '116', '5');
INSERT INTO `relation` VALUES ('3389', '117', '5');
INSERT INTO `relation` VALUES ('3390', '118', '5');
INSERT INTO `relation` VALUES ('3391', '119', '5');
INSERT INTO `relation` VALUES ('3392', '120', '5');
INSERT INTO `relation` VALUES ('3393', '121', '5');
INSERT INTO `relation` VALUES ('3394', '122', '5');
INSERT INTO `relation` VALUES ('3395', '150', '5');
INSERT INTO `relation` VALUES ('3396', '151', '5');
INSERT INTO `relation` VALUES ('3966', '105', '1');
INSERT INTO `relation` VALUES ('3967', '106', '1');
INSERT INTO `relation` VALUES ('3968', '107', '1');
INSERT INTO `relation` VALUES ('3969', '108', '1');
INSERT INTO `relation` VALUES ('3970', '109', '1');
INSERT INTO `relation` VALUES ('3971', '110', '1');
INSERT INTO `relation` VALUES ('3972', '111', '1');
INSERT INTO `relation` VALUES ('3973', '112', '1');
INSERT INTO `relation` VALUES ('3974', '113', '1');
INSERT INTO `relation` VALUES ('3975', '165', '1');
INSERT INTO `relation` VALUES ('3976', '166', '1');
INSERT INTO `relation` VALUES ('3977', '167', '1');
INSERT INTO `relation` VALUES ('3978', '114', '1');
INSERT INTO `relation` VALUES ('3979', '115', '1');
INSERT INTO `relation` VALUES ('3980', '116', '1');
INSERT INTO `relation` VALUES ('3981', '117', '1');
INSERT INTO `relation` VALUES ('3982', '118', '1');
INSERT INTO `relation` VALUES ('3983', '162', '1');
INSERT INTO `relation` VALUES ('3984', '163', '1');
INSERT INTO `relation` VALUES ('3985', '164', '1');
INSERT INTO `relation` VALUES ('3986', '119', '1');
INSERT INTO `relation` VALUES ('3987', '120', '1');
INSERT INTO `relation` VALUES ('3988', '121', '1');
INSERT INTO `relation` VALUES ('3989', '122', '1');
INSERT INTO `relation` VALUES ('3990', '150', '1');
INSERT INTO `relation` VALUES ('3991', '151', '1');
INSERT INTO `relation` VALUES ('3992', '128', '1');
INSERT INTO `relation` VALUES ('3993', '134', '1');
INSERT INTO `relation` VALUES ('3994', '158', '1');
INSERT INTO `relation` VALUES ('3995', '159', '1');
INSERT INTO `relation` VALUES ('3996', '130', '1');
INSERT INTO `relation` VALUES ('3997', '131', '1');
INSERT INTO `relation` VALUES ('3998', '135', '1');
INSERT INTO `relation` VALUES ('3999', '136', '1');
INSERT INTO `relation` VALUES ('4000', '137', '1');
INSERT INTO `relation` VALUES ('4001', '152', '1');
INSERT INTO `relation` VALUES ('4002', '153', '1');
INSERT INTO `relation` VALUES ('4003', '154', '1');
INSERT INTO `relation` VALUES ('4004', '132', '1');
INSERT INTO `relation` VALUES ('4005', '138', '1');
INSERT INTO `relation` VALUES ('4006', '139', '1');
INSERT INTO `relation` VALUES ('4007', '140', '1');
INSERT INTO `relation` VALUES ('4008', '155', '1');
INSERT INTO `relation` VALUES ('4009', '156', '1');
INSERT INTO `relation` VALUES ('4010', '157', '1');
INSERT INTO `relation` VALUES ('4011', '133', '1');
INSERT INTO `relation` VALUES ('4012', '160', '1');
INSERT INTO `relation` VALUES ('4013', '161', '1');
INSERT INTO `relation` VALUES ('4014', '141', '1');
INSERT INTO `relation` VALUES ('4015', '142', '1');
INSERT INTO `relation` VALUES ('4016', '143', '1');
INSERT INTO `relation` VALUES ('4017', '144', '1');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `pid` bigint(11) DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `deptid` bigint(11) DEFAULT NULL COMMENT '部门名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '1', '0', '超级管理员', '24', 'administrator', '1');
INSERT INTO `role` VALUES ('5', '2', '1', '临时', '26', 'temp', null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `roleid` varchar(255) DEFAULT NULL COMMENT '角色id',
  `deptid` bigint(11) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '91da7f95-29d8-4ad0-a206-04eed55f6add.jpg', 'admin', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '张三', '2017-05-05 00:00:00', '2', 'sn93@qq.com', '18200000000', '1', '27', '1', '2016-01-29 08:49:53', '25');
INSERT INTO `user` VALUES ('44', null, 'test', '45abb7879f6a8268f1ef600e6038ac73', 'ssts3', 'test', '2017-05-01 00:00:00', '1', 'abc@123.com', '', '5', '26', '1', '2017-05-16 20:33:37', null);
INSERT INTO `user` VALUES ('45', null, 'xiaors', '4758f94f187641d3e218d01d691c8bc0', 'gms3y', '张三', '2018-04-11 00:00:00', '1', '876057701@qq.com', '', '6', '34', '3', '2018-04-03 11:07:28', null);
INSERT INTO `user` VALUES ('46', null, 'zhangsan', 'bcfe0ca05769d820a18499309027bd61', 'f5kvs', '荣', '2019-02-04 00:00:00', '1', 'sdaf@126.com', '', null, '25', '3', '2019-02-19 13:55:38', null);
INSERT INTO `user` VALUES ('47', null, 'zhangsan', '465a720b399793b50b9a2af9761ac8f0', 'j3g09', 'rong', '2019-02-21 00:00:00', '1', '', '', null, '0', '3', '2019-02-19 14:00:55', null);
