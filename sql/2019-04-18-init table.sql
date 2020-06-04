drop table if exists profit_distribution;

/*==============================================================*/
/* Table: profit_distribution                                   */
/*==============================================================*/
CREATE TABLE `profit_distribution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  `service_rate` decimal(16,4) DEFAULT NULL COMMENT '服务费率',
  `is_effect` tinyint(1) DEFAULT '1' COMMENT '是否立即生效（1-是；0-否；）',
  `effective_time` datetime DEFAULT NULL COMMENT '生效时间',
  `change_service_rate` decimal(16,4) DEFAULT NULL COMMENT '变更后服务费率',
  `created_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间（提现申请时间）',
  `modified_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='收益分配表';

alter table profit_distribution comment '收益分配表';

drop table if exists profit_distribution_history;

/*==============================================================*/
/* Table: profit_distribution_history                           */
/*==============================================================*/
CREATE TABLE `profit_distribution_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `distribution_id` bigint(20) DEFAULT NULL COMMENT '收益配置ID（关联至profit_distribution主键）',
  `before_service_rate` decimal(16,4) DEFAULT NULL COMMENT '变更前服务费率',
  `after_service_rate` decimal(16,4) DEFAULT NULL COMMENT '变更后服务费率',
  `effective_time` datetime DEFAULT NULL COMMENT '生效时间',
  `created_user_name` varchar(50) DEFAULT NULL COMMENT '创建人姓名（平台管理员）',
  `created_user_account` varchar(50) DEFAULT NULL COMMENT '创建人账号（平台管理员）',
  `created_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间（提现申请时间）',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='收益分配历史记录表';

alter table profit_distribution_history comment '收益分配历史记录表';

drop table if exists platform_balance_flow;

/*==============================================================*/
/* Table: platform_balance_flow                                 */
/*==============================================================*/
CREATE TABLE `platform_balance_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `trans_src` int(3) DEFAULT NULL COMMENT '交易来源（1-用户支付+；2-用户退款-；3-店铺提现成功-；）',
  `balance` decimal(21,0) DEFAULT NULL COMMENT '当前余额（单位：分）',
  `credit_amount` decimal(21,0) DEFAULT NULL COMMENT '贷记金额+（单位：分）',
  `debit_amount` decimal(21,0) DEFAULT NULL COMMENT '借记金额-（单位：分）',
  `pre_balance` decimal(21,0) DEFAULT NULL COMMENT '上期余额（单位：分）',
  `trade_id` bigint(20) DEFAULT NULL COMMENT '关联外部ID（订单ID-关联至shop_order表主键；提现ID-关联至cash_withdrawal表主键；）',
  `remark` varchar(100) DEFAULT NULL COMMENT '余额流水备注说明',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='平台余额流水表';

alter table platform_balance_flow comment '平台余额流水表';

drop table if exists shop_balance_flow;

/*==============================================================*/
/* Table: shop_balance_flow                                     */
/*==============================================================*/
CREATE TABLE `shop_balance_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  `trans_src` int(3) DEFAULT NULL COMMENT '交易来源（1-订单结算+；2-提现-；3-提现不通过返还+；）',
  `balance` decimal(21,0) DEFAULT NULL COMMENT '当前余额（单位：分）',
  `credit_amount` decimal(21,0) DEFAULT NULL COMMENT '贷记金额+（单位：分）',
  `debit_amount` decimal(21,0) DEFAULT NULL COMMENT '借记金额-（单位：分）',
  `pre_balance` decimal(21,0) DEFAULT NULL COMMENT '上期余额（单位：分）',
  `trade_id` bigint(20) DEFAULT NULL COMMENT '关联外部ID（订单结算ID-关联至shop_order_settlement表主键；提现ID-关联至cash_withdrawal表主键；）',
  `remark` varchar(100) DEFAULT NULL COMMENT '余额流水备注说明',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='店铺余额流水表';

alter table shop_balance_flow comment '店铺余额流水表';

drop table if exists cash_withdrawal;

/*==============================================================*/
/* Table: cash_withdrawal                                       */
/*==============================================================*/
CREATE TABLE `cash_withdrawal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  `draw_way` int(3) DEFAULT NULL COMMENT '提现方式（1-微信；2-支付宝；3-银行卡；）',
  `amount` decimal(21,0) DEFAULT NULL COMMENT '提现金额',
  `account_name` varchar(30) DEFAULT NULL COMMENT '账户姓名',
  `account_no` varchar(50) DEFAULT NULL COMMENT '提现账号',
  `status` int(3) DEFAULT '1' COMMENT '提现状态（1-待审核；2-审核通过；3-确认打款；4-审核不通过；）',
  `review_user_id` bigint(20) DEFAULT NULL COMMENT '审核用户ID（平台用户）',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `review_remark` varchar(512) DEFAULT NULL COMMENT '审核说明',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认打款时间',
  `created_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间（提现申请时间）',
  `modified_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='资金提现记录表';

alter table cash_withdrawal comment '资金提现记录表';

drop table if exists shop_order_settlement;

/*==============================================================*/
/* Table: shop_order_settlement                                 */
/*==============================================================*/
CREATE TABLE `shop_order_settlement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `order_amount` decimal(21,0) DEFAULT NULL COMMENT '订单金额（单位：分）',
  `refund_amount` decimal(21,0) DEFAULT NULL COMMENT '退款金额（单位：分）',
  `service_fee` decimal(21,0) DEFAULT NULL COMMENT '服务费（单位：分）',
  `service_rate` decimal(16,4) DEFAULT NULL COMMENT '服务费率（保留4位小数）',
  `settle_amount` decimal(21,0) DEFAULT NULL COMMENT '结算金额 = （订单金额 - 退款金额）*（1 - 服务费率）（单位：分）',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间（发货时间）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='店铺订单结算表';

alter table shop_order_settlement comment '店铺订单结算表';

drop table if exists shop_order_cash_flow;

/*==============================================================*/
/* Table: shop_order_cash_flow                                  */
/*==============================================================*/
CREATE TABLE `shop_order_cash_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `cash_type` int(3) DEFAULT NULL COMMENT '资金类型（1-待出账金额；2-待到账金额；）',
  `trans_src` int(3) DEFAULT NULL COMMENT '交易来源（1-用户已付款（待出账金额+）；2-用户退款（待出账金额-）；3-用户已收货=交易完成（待出账金额-、待到账金额+）；4-用户售后退款（待到账金额-）；5-订单已结算（待到账金额-、店铺余额+）；）',
  `amount` decimal(21,0) DEFAULT NULL COMMENT '当前金额（单位：分）',
  `credit_amount` decimal(21,0) DEFAULT NULL COMMENT '贷记金额+（单位：分）',
  `debit_amount` decimal(21,0) DEFAULT NULL COMMENT '借记金额-（单位：分）',
  `pre_amount` decimal(21,0) DEFAULT NULL COMMENT '上期金额（单位：分）',
  `remark` varchar(50) DEFAULT NULL COMMENT '流水备注说明',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='店铺订单资金流水表（待到账/待出账金额）';

alter table shop_order_cash_flow comment '店铺订单资金流水表（待到账/待出账金额）';

