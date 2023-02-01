create database if not exists order_admin;
-- 创建数据库 order_admin

use order_admin;


create table tb_order
(
    id               bigint auto_increment not null primary key comment '自增id',
    customer_id      bigint comment '客户id',
    customer_name    varchar(120) comment '客户名称',
    customer_phone   varchar(120) comment '客户手机号',
    customer_address varchar(120) comment '客户地址',
    order_number     varchar(120) comment '订单编号',
    create_user_name varchar(120) comment '制单人',
    create_time      date comment '订单日期',
    money            decimal(12, 4) comment '金额',
    receive_money    decimal(12, 4) comment '已收款金额',
    total_area       decimal(12, 4) comment '总面积',
    remark           varchar(150) comment '备注',
    pay_status       boolean comment '付款状态',
    send_status      boolean comment '发货状态'
) comment '订单表';


create table tb_customer
(
    id               bigint auto_increment not null primary key comment '自增id',
    customer_name    varchar(120) comment '客户名称',
    customer_phone   varchar(120) comment '客户手机号',
    customer_address varchar(120) comment '客户地址'
) comment '客户表';

create table tb_goods
(
    id                      bigint auto_increment not null primary key comment '自增id',
    goods_name              varchar(120) comment '客户名称',
    goods_width             decimal(12, 4) comment '宽度 单位mm',
    goods_length            decimal(12, 4) comment '长度 单位mm',
    total                   decimal(12, 4) comment '总数',
    area                    decimal(12, 4) comment '面积 单位平方米',
    goods_price             decimal(12, 4) comment '单价',
    processing_requirements varchar(200) comment '加工需求',
    processing_expenses     decimal(12, 4) comment '加工费用',
    total_money             decimal(12, 4) comment '总金额',
    remark                  varchar(200) comment '备注',
    production_process      varchar(200) comment '生产流程',
    order_id                bigint comment '关联订单id'
) comment '货物清单表';

insert into tb_customer(customer_name, customer_phone, customer_address) value ('张三','18888888888','上海市');