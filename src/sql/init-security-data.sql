/**
 * 用户初始化，默认密码123456
 */
delete from sec_user;
insert into sec_user(id, username, email, mobile_phone_number, password, salt, create_date, status, admin, deleted, is_register) values
(1, 'admin', 'admin@jict.org', '18970986887', 'ec21fa1738f39d5312c6df46002d403d', 'yDd1956wn1', now(), 'normal', true, false, false)
;
select setval('seq_sec_user_id', 1);

/**
 * 组织机构初始化
 */
delete from sec_organization;
insert into sec_organization(id, parent_id, parent_ids, weight, name, is_show) values(1, 0, '0/', 1, '组织机构', true);
select setval('seq_sec_organization_id', 1);

/**
 * 职务初始化
 */
delete from sec_job;
insert into sec_job(id, parent_id, parent_ids, weight, name, is_show) values(1, 0, '0/', 1, '职务', true);
select setval('seq_sec_job_id', 1);

/**
 * 菜单资源初始化
 */
delete from sec_resource;
insert into sec_resource(id, icon, identity, name, parent_id, parent_ids, is_show, style, url, weight) values
(1,'','','资源',0,'0/',true,null,'',1)
,(5,'ztree_file','security','权限管理',1,'0/1/',true,'tree','',8)
,(6,'ztree_file','system','系统管理',1,'0/1/',true,'tree','',9)
,(9,'ewcms-security-resource-tree','resource','控制列表',5,'0/1/5/',true,null,'/security/resource/index',1)
,(49,'ewcms-security-group-tree','group','分组列表',5,'0/1/5/',true,null,'/security/group/group/index',2)
,(50,'ztree_file','','用户管理',5,'0/1/5/',true,null,'',4)
,(51,'ewcms-security-user-user-tree','user','用户列表',50,'0/1/5/50/',true,null,'/security/user/user/index',1)
,(52,'ewcms-security-user-online-tree','userOnline','在线用户列表',50,'0/1/5/50/',true,null,'/security/user/online/index',2)
,(53,'ewcms-security-user-statushistory-tree','userStatusHistory','状态变更历史列表',50,'0/1/5/50/',true,null,'/security/user/statusHistory/index',3)
,(54,'ewcms-security-user-lastonline-tree','userLastOnline','用户最后在线历史列表',50,'0/1/5/50/',true,null,'/security/user/lastOnline/index',4)
,(55,'ztree_file','','组织机构管理',5,'0/1/5/',true,null,'',5)
,(56,'ewcms-security-organization-organiation-tree','organization','组织机构列表',55,'0/1/5/55/',true,null,'/security/organization/organization/index',1)
,(57,'ewcms-security-organization-job-tree','job','工作职务列表',55,'0/1/5/55/',true,null,'/security/organization/job/index',2)
,(58,'ztree_file','','权限管理',5,'0/1/5/',true,null,'',6)
,(59,'ewcms-security-permission-permission-tree','permission','权限列表',58,'0/1/5/58/',true,null,'/security/permission/permission/index',1)
,(60,'ewcms-security-permission-role-tree','role','授权权限给角色',58,'0/1/5/58/',true,null,'/security/permission/role/index',2)
,(61,'ewcms-security-permission-auth-tree','auth','授权角色给实体',58,'0/1/5/58/',true,null,'/security/auth/index',3)
,(62,'ztree_file','','个人中心',1,'0/1/',true,'accordion','',5)
,(63,'ewcms-personal-memoranda-tree','','备忘录',62,'0/1/62/',true,null,'/personal/memoranda/index',3)
,(64,'ewcms-personal-message-tree','','个人消息',62,'0/1/62/',true,null,'/personal/message/index',4)
,(65,'ewcms-security-switch-tree','','切换身份',5,'0/1/5/',true,null,'/security/user/runAs/index',3)
,(66,'ewcms-system-icon-tree','icon','图标管理',6,'0/1/6/',true,null,'/system/icon/index',1)
,(67,'ewcms-system-history-tree','','历史记录',6,'0/1/6/',true,null,'/system/history/index',2)
,(68,'ewcms-system-externalds-tree','externalds','数据源',6,'0/1/6/',true,null,'/system/externalds/index',3)
,(81,'ztree_file','','报表管理',6,'0/1/6/',true,null,'',6)
,(82,'ewcms-system-report-text-tree','textreport','文字报表',81,'0/1/6/81/',true,null,'/system/report/text/index',1)
,(83,'ewcms-system-report-chart-tree','chartreport','图型报表',81,'0/1/6/81/',true,null,'/system/report/chart/index',2)
,(84,'ewcms-system-report-category-tree','categoryreport','报表分类',81,'0/1/6/81/',true,null,'/system/report/category/index',3)
,(85,'ewcms-system-report-repository-tree','categoryreport','报表存储',81,'0/1/6/81/',true,null,'/system/report/repository/index',4)
,(86,'ewcms-system-report-show-tree','','报表集',81,'0/1/6/81/',true,null,'/system/report/show/index',5)
,(87,'ztree_file','personnel','专家管理',1,'0/1/',true,'accordion','',7)
,(88,'ewcms-personnel-archive-tree','archive','登记表管理',87,'0/1/87/',true,null,'/personnel/archive/index',1)
,(90,'ewcms-personal-archive-tree','','登记表',62,'0/1/62/',true,null,'/personnel/archive/edit',2)
,(91,'ewcms-personnel-nation-tree','nation','民族管理',87,'0/1/87/',true,null,'/personnel/nation/index',3)
,(92,'ewcms-personnel-allowance-tree','allowance','津贴管理',87,'0/1/87/',true,null,'/personnel/allowance/index',4)
,(93,'ewcms-personnel-currentstate-tree','currentState','目前情况管理',87,'0/1/87/',true,null,'/personnel/currentState/index',5)
,(94,'ewcms-personnel-category-tree','acadCategory','学会分类',87,'0/1/87/',true,null,'/personnel/acadCategory/index',6)
,(95,'ztree_file','monitor','监控管理',1,'0/1/',true,null,'',10)
,(96,'ewcms-monitor-db-tree','db','数据库监控',95,'0/1/95/',true,null,'/monitor/druid/index.html',1)
,(97,'ewcms-monitor-ehcache-tree','ehcache','Ehcache监控',95,'0/1/95/',true,null,'/monitor/ehcache/index',2)
,(98,'ewcms-monitor-jvm-tree','jvm','JVM监控',95,'0/1/95/',true,null,'/monitor/jvm/index',3)
,(99,'ewcms-monitor-sql-tree','ql','SQL执行',95,'0/1/95/',true,null,'/monitor/db/sqlIndex',4)
,(100,'ewcms-monitor-jpaql-tree','ql','JPAQL执行',95,'0/1/95/',true,null,'/monitor/db/jpaqlIndex',5)
,(101,'ewcms-monitor-hibernate-tree','hibernate','Hibernate监控',95,'0/1/95/',true,null,'/monitor/hibernate/index',6)

;
select setval('seq_sec_resource_id', 101);

/**
 * 权限初始化
 */
delete from sec_permission;
insert into sec_permission(id, name, permission, description, is_show) values
(1, '所有', '*', '所有数据操作的权限', true)
,(2, '新增', 'create', '新增数据操作的权限', true)
,(3, '修改', 'update', '修改数据操作的权限', true)
,(4, '删除', 'delete', '删除数据操作的权限', true)
,(5, '查看', 'view', '查看数据操作的权限', true)
,(6, '审核', 'audit', '审核数据操作的权限', true)
;
select setval('seq_sec_permission_id', 6);

/**
 * 角色初始化
 */
delete from sec_role;

insert into sec_role(id, name, role, description, is_show) values
(1, '超级管理员', 'role_admin', '拥有所有权限', true)
,(2, '专家库管理员', 'role_archive', '拥有专家库管理的所有权限', true)
;
select setval('seq_sec_role_id', 2);

/**
 * 角色/资源/权限初始化
 */
delete from sec_role_resource_permission;
insert into sec_role_resource_permission(id, role_id, resource_id, permission_ids) values
(1, 1, 2, '1')
;
select setval('seq_sec_role_resource_permission_id', 1);

/**
 * 授权初始化
 */
delete from sec_auth;
insert into sec_auth(id, organization_id, job_id, user_id, group_id, role_ids, type) values
(1, 0, 0, 1, 0, '1', 'user')
;
select setval('seq_sec_auth_id', 1);



/**
 * 初始化各学会管理人员
 */
select * from sec_user where username like '江西省%';

delete from sec_user where id in (69, 210, 497, 80, 74, 108);

update sec_user set username = username || '韩道福' where id=246;
update sec_user set username = username || '黄国勤' where id=98;
update sec_user set username = username || '赵小敏' where id=447;

insert into sec_user (admin, deleted, email, is_register, mobile_phone_number, password, salt, status, username, id) values 
(false, false, '江西省通信学会@jict.org', false, '15407910001', 'cf2bc6a498e5ac4375d703a0188eb9e8', '2vmFOUfN8W', 'normal', '江西省通信学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省测绘学会@jict.org', false, '15407910002', '98eaf59ed3fe8ed1fca37a41e319b25a', 'MyvmukMgQ8', 'normal', '江西省测绘学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省造船工程学会@jict.org', false, '15407910003', '27662484022b96937c8331939ba86b17', 'AYBumzWcyT', 'normal', '江西省造船工程学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省节能技术学会@jict.org', false, '15407910004', '59785d434e5f5ea1ca4477d0122cd4b9', 'X5KAWpuhYT', 'normal', '江西省节能技术学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省清洁生产促进会@jict.org', false, '15407910005', '3b025b7686102b486a116d40495136e6', 'PtH728Pfk2', 'normal', '江西省清洁生产促进会',nextval ('seq_sec_user_id'))
, (false, false, '江西省铁道学会@jict.org', false, '15407910006', '3d07b0a7c5954bb867ac17e97c15d39f', 'fRzNWeLmFI', 'normal', '江西省铁道学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省公路学会@jict.org', false, '15407910007', '9cf0a93f9d669cba2f5165d521a14fbe', 'C9yfNKb2uI', 'normal', '江西省公路学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省航空学会@jict.org', false, '15407910008', 'bc55fc7387622bc445f41beb9031c5a8', 'AqYW7fTAxj', 'normal', '江西省航空学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省烟草学会@jict.org', false, '15407910009', 'afe3cb4522264ee77ca9b71ca9483661', 'nK0uFdG4aQ', 'normal', '江西省烟草学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省金属学会@jict.org', false, '15407910010', '95eb80ca0ba24df7b97a936ab31c296b', '0fpmetgB8k', 'normal', '江西省金属学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省有色金属学会@jict.org', false, '15407910011', 'b7079f6fbd8645a11f93181cf5bf419b', 'qnResJCBFI', 'normal', '江西省有色金属学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省稀土学会@jict.org', false, '15407910012', 'ca32e4c43a0dffd97eecf14cfbf1c5f7', 'LEF6tBnoOE', 'normal', '江西省稀土学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省化学化工学会@jict.org', false, '15407910013', 'd256d3b59a9f0af91f23d286f021044e', 'A8cMsN6Ryg', 'normal', '江西省化学化工学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省核学会@jict.org', false, '15407910014', '297f46aa1de3a9defdac55bfe980217c', 'BCUJ76YapQ', 'normal', '江西省核学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省林学会@jict.org', false, '15407910015', 'ee93f7eeb566be289d098c9197985f9d', '1OuBSxxtRt', 'normal', '江西省林学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省民营科技实业家协会@jict.org', false, '15407910016', 'f96bc0755cd21a04434637316c935b7e', 'XOhdLAkaCy', 'normal', '江西省民营科技实业家协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省技术创新促进会@jict.org', false, '15407910017', 'b3adfd3915e4346a7398041f148d9c82', 'BkUIxpfiMi', 'normal', '江西省技术创新促进会',nextval ('seq_sec_user_id'))
, (false, false, '江西省现场统计研究会@jict.org', false, '15407910018', '50bd87981b13ea5078b593a339d94c42', 'kHL1nGgp2j', 'normal', '江西省现场统计研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省老科技工作者协会@jict.org', false, '15407910019', 'f14199dbc575f78fda3ac1e645cb63e4', 'mrmWbXaEAj', 'normal', '江西省老科技工作者协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省科学技术情报学会@jict.org', false, '15407910020', '16aa9e20b17f7c55b969c90ceadad783', 'On0mEjnkwE', 'normal', '江西省科学技术情报学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省图书馆学会@jict.org', false, '15407910021', '0416a92f0f1d328545145083983d0dd2', 'G8crfU6vLa', 'normal', '江西省图书馆学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省女科技工作者联谊会@jict.org', false, '15407910022', 'c29a6cf553eb73fbb7f02231880f1beb', 'RxgqVaRVBR', 'normal', '江西省女科技工作者联谊会',nextval ('seq_sec_user_id'))
, (false, false, '江西省中医药学会@jict.org', false, '15407910023', 'c5d654adbdea28526ff90e752fa3752f', 'q2HyU0wmQz', 'normal', '江西省中医药学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省营养学会@jict.org', false, '15407910024', '6b81120f905b3b9e13792022e2cc4032', 'ezTIn7keP9', 'normal', '江西省营养学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省土地学会@jict.org', false, '15407910025', '1780d8f2e56707674e13efe21fd554b8', '4YJ2ngM7GM', 'normal', '江西省土地学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省医学会@jict.org', false, '15407910026', 'd2e38cbd6d024d82abd79b170005a4df', '2bJcNwZQaP', 'normal', '江西省医学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省中西医结合学会@jict.org', false, '15407910027', '20f4f3de09046bf3d4428e1e6ffb9518', 'KId9uoYM3g', 'normal', '江西省中西医结合学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省药学会@jict.org', false, '15407910028', '61c095643d17f21cb1a2956c0cb1092a', 'TOkcxdjzu3', 'normal', '江西省药学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省护理学会@jict.org', false, '15407910029', 'a6b3842d7219faa160a26d81fd7d99c3', 'PqDqpHUIk0', 'normal', '江西省护理学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省生理科学会@jict.org', false, '15407910030', '27760989afe49b74013d940786ee3c3c', 'JX19C7cDtV', 'normal', '江西省生理科学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省解剖学会@jict.org', false, '15407910031', 'fc0e11a4e495ee12edf8cd2749bd6de8', 'q28rB5WYmA', 'normal', '江西省解剖学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省抗癌协会@jict.org', false, '15407910032', 'a4dbc9ec5af39732d4a9a6be4ce762e7', 'yrOYUyYBsf', 'normal', '江西省抗癌协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省体育科学学会@jict.org', false, '15407910033', '79e18a96932c9fff88584b0cf7cd5269', 'LCbvR7L9Tm', 'normal', '江西省体育科学学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省预防医学会@jict.org', false, '15407910034', '6332da7845f27813dbffc467f9475199', 'QwgQcGtJAg', 'normal', '江西省预防医学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省针灸学会@jict.org', false, '15407910035', '07a2a252190fa215934a63f26bc757be', 'cBUwL3mEHj', 'normal', '江西省针灸学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省防痨协会@jict.org', false, '15407910036', 'b0bf2e4e66cef75adf8c07ded981df0f', 'n0AdGt4gRp', 'normal', '江西省防痨协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省麻风防治协会@jict.org', false, '15407910037', 'a199ec103e6927233daa48856de89608', 'QqXIXHVXLg', 'normal', '江西省麻风防治协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省数学学会@jict.org', false, '15407910038', '359242c2bafd472b50ecb9cbf6d4f367', 'LfRPTG0Eea', 'normal', '江西省数学学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省物理学会@jict.org', false, '15407910039', '444cb23179bbb88b87b559382aa00804', 'HqBiC20hNA', 'normal', '江西省物理学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省力学学会@jict.org', false, '15407910040', '6782d0a9cab22aa6058f3c2e38370d4e', 'RFDPKPFOjg', 'normal', '江西省力学学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省光学学会@jict.org', false, '15407910041', '7dad94498f67c9e3114524c486f85371', 'tGQiaRXe3R', 'normal', '江西省光学学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省植物生理学会@jict.org', false, '15407910042', '0619c599c1d342a916711c5f08db9688', 'kZYIF9HVlj', 'normal', '江西省植物生理学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省遗传学会@jict.org', false, '15407910043', '621ca3af3f910ab8feff9af4d8b28c72', '1l764y6doU', 'normal', '江西省遗传学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省心理学会@jict.org', false, '15407910044', '836703a87f69ffea15790e10c1150d83', 'GUCOAvcwpJ', 'normal', '江西省心理学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省气象学会@jict.org', false, '15407910045', '71aba38761eb51bd531e34942e3e0a66', 'IsAsB7QvCw', 'normal', '江西省气象学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省生态学会@jict.org', false, '15407910046', '9719cff467fe5214303edda0f420020d', 'kEAQqt1s3F', 'normal', '江西省生态学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省地质学会@jict.org', false, '15407910047', '71f0a53fe7145dd5c915e07dd1de35b3', 'YJxClMGpBR', 'normal', '江西省地质学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省地理学会@jict.org', false, '15407910048', '77f2e7fbffb351bad4c3654ebe33c61e', 'tWUwVtpazA', 'normal', '江西省地理学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省环境科学学会@jict.org', false, '15407910049', 'dc70e6bd538b8ea0e8a20d6045c619e6', 'h8IBXJl2FV', 'normal', '江西省环境科学学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省岩石力学与工程学会@jict.org', false, '15407910050', '63537b86b8e2ea6ee813d371ce300c68', 'SC3Bu3SpFo', 'normal', '江西省岩石力学与工程学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省系统工程学会@jict.org', false, '15407910051', 'b8906b74a21a0bd80a3318bc6acbeeaa', 'nQaG3JVKVJ', 'normal', '江西省系统工程学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省实验动物学会@jict.org', false, '15407910052', '3cf81a80f3872c4650f8ba066ad70cb6', '6zN6I4w8Q3', 'normal', '江西省实验动物学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省动物学会@jict.org', false, '15407910053', '51126604cefe704a1cb0a24b19bd5332', 'KjkfNA2wiM', 'normal', '江西省动物学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省植物学会@jict.org', false, '15407910054', '80c2c51133f89736d3f93351afefbc5e', 'PnXxbK3y3p', 'normal', '江西省植物学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省昆虫学会@jict.org', false, '15407910055', '4283699630dfa7a349eb6be4b67cc609', 'yfrVZAbL33', 'normal', '江西省昆虫学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省微生物学会@jict.org', false, '15407910056', '9704ab28059de3cf8a18117c856f6cfb', 'VCTLpnXv6K', 'normal', '江西省微生物学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省生物化学与分子生物学会@jict.org', false, '15407910057', 'c0e02fb7881490fc4f3debda191c210a', 'auoOeOOUJ6', 'normal', '江西省生物化学与分子生物学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省珠算协会@jict.org', false, '15407910058', '91db92120750da468733f8c259c98823', 'LLlwK00NVk', 'normal', '江西省珠算协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省爱鸟协会@jict.org', false, '15407910059', '1718d6313b3a1353faacba9e6a7292de', 'SKkbExjhrE', 'normal', '江西省爱鸟协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省神经科学学会@jict.org', false, '15407910060', 'fdd5697740365d0ddf2c5988b59fd946', 'gDVNK4Xsd6', 'normal', '江西省神经科学学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省天文学会@jict.org', false, '15407910061', '58cfda059870bdc12f0039d3ca75454a', 'gZ263Hca6l', 'normal', '江西省天文学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省计算机学会@jict.org', false, '15407910062', 'b8b4a0c214f7a1dd8c9094ad20412637', 'd8LWf5xq8n', 'normal', '江西省计算机学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省心理卫生协会@jict.org', false, '15407910063', 'ebf5dd01fc0002031e88c0186711dac0', 'f07S0aRA3j', 'normal', '江西省心理卫生协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省菌物学会@jict.org', false, '15407910064', '1ee45ed2d7ed5dd636cd870a8feb0681', 'RB84EzHCnr', 'normal', '江西省菌物学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省石油学会@jict.org', false, '15407910065', 'fddf7f11b1cf2a10cd4980ecbc3eecec', 'IgEzMeooR9', 'normal', '江西省石油学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省煤炭学会@jict.org', false, '15407910066', '46f006b28ca79e29fdebe8a880ddab5b', 'RzvuiBBJb0', 'normal', '江西省煤炭学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省科学学与科技管理研究会@jict.org', false, '15407910067', 'e9fef3f2963ab2a093623c1007bfacdc', 'GlWM0VcPxr', 'normal', '江西省科学学与科技管理研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省咨询业协会@jict.org', false, '15407910068', '1bc27584eb1af2b3e75864bba4ddbc80', 'Du18tpEeA8', 'normal', '江西省咨询业协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省工艺美术学会@jict.org', false, '15407910069', 'bc90a303adfc9517212237ffeb652803', 'MdVwB9f8dT', 'normal', '江西省工艺美术学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省自然科学学会研究会@jict.org', false, '15407910070', 'ebc95cca125d3f0bb096f39e331b03ad', 'jqPmBkEa5E', 'normal', '江西省自然科学学会研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省青少年科技辅导员协会@jict.org', false, '15407910071', '7f37d6915286d498f86ecbb3dc9c8803', 'udG9gyrrxv', 'normal', '江西省青少年科技辅导员协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省科学技术普及创作协会@jict.org', false, '15407910072', '0579118a367ed64941ce877911572d21', '0Yiq2Hlp5Y', 'normal', '江西省科学技术普及创作协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省企业科协联合会@jict.org', false, '15407910073', '892f43d08165a7d245f61c97cedc9f74', '5Ag1edUBc4', 'normal', '江西省企业科协联合会',nextval ('seq_sec_user_id'))
, (false, false, '江西省消防协会@jict.org', false, '15407910074', '5b0a6e8ff796bd120482a8aaf2303cac', 'OAFKUpkyJR', 'normal', '江西省消防协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省能源研究会@jict.org', false, '15407910075', 'fc3d488c322e91dada95d9060642aa8d', '1M3dnOXRFC', 'normal', '江西省能源研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省遥感信息技术应用协会@jict.org', false, '15407910076', '11455b3620ae32fd1230c920ddc00b3b', '2hUIEsmw30', 'normal', '江西省遥感信息技术应用协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省人力资源协会@jict.org', false, '15407910077', '62c04bc4b87b0971bd63d05c0e1ba509', 'O4ZTlql3b3', 'normal', '江西省人力资源协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省食品科学技术学会@jict.org', false, '15407910078', 'e94e2001d408683f8a7afaef0c09cc5c', 'Z1TcbFBdoW', 'normal', '江西省食品科学技术学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省国际科技交流促进会@jict.org', false, '15407910079', '2257f2586748142decbe14b508dce356', 'f3T5iVEnG3', 'normal', '江西省国际科技交流促进会',nextval ('seq_sec_user_id'))
, (false, false, '江西省数字影像协会@jict.org', false, '15407910080', 'cb08e859d60cfaf964c68febce5a0a2f', '69zZ6WiDj1', 'normal', '江西省数字影像协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省农学会@jict.org', false, '15407910081', 'aba2e3c76b91e6ce2947abdcfcbc5585', 'mjIa3Jp0GK', 'normal', '江西省农学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省科技创新与进步促进会@jict.org', false, '15407910082', '70b016f7902783c94d158fe2bcabd3f6', '0S6X24Ni9P', 'normal', '江西省科技创新与进步促进会',nextval ('seq_sec_user_id'))
, (false, false, '南昌大学科协@jict.org', false, '15407910083', '81e05d194dc21828ffb4799795de5e73', 'Vk2J6eXFN2', 'normal', '南昌大学科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西省农科院科协@jict.org', false, '15407910084', '2994eada43ba0a8fc6cbcfe98aff6290', '46YTgQCmyE', 'normal', '江西省农科院科协',nextval ('seq_sec_user_id')) 
, (false, false, '华东交大科协@jict.org', false, '15407910085', '2dd6878c7f2d3580c4204677230be3a0', 'felDjkgi9G', 'normal', '华东交大科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西省野外生存研究会@jict.org', false, '15407910086', 'dc8814a7a991df69d5aa6afd44e1dfc5', 'iJStOSQZRP', 'normal', '江西省野外生存研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省生态地质环境学会@jict.org', false, '15407910087', 'aef6908fd2b34d8ce9f4536a6d44d539', 'RPIMGnQeOP', 'normal', '江西省生态地质环境学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省软科学研究会@jict.org', false, '15407910088', '968fa8da2cfacb7a4f1d16875116f0a5', 'OMSEoHypYh', 'normal', '江西省软科学研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省机械工程学会@jict.org', false, '15407910089', '29effa0f13995b8fc0b7212007868309', 'hdaL8WkQtW', 'normal', '江西省机械工程学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省汽车工程学会@jict.org', false, '15407910090', 'a925619f664113b4ecdfc66bbe50c54b', 'nG30uaEqUg', 'normal', '江西省汽车工程学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省电机工程学会@jict.org', false, '15407910091', 'c400b7536740218c7f3f3a428aae3ded', 'HcdYw2Uu7j', 'normal', '江西省电机工程学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省质量协会@jict.org', false, '15407910092', '0d02839ad0aef7cc350707a6b32a45cc', 'hS8jsuod1V', 'normal', '江西省质量协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省土木建筑学会@jict.org', false, '15407910093', '10d01697b4728e2ad41eed0f5dd1b895', 'iQXUZL0AxT', 'normal', '江西省土木建筑学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省生物工程研究会@jict.org', false, '15407910094', 'c97967f685d93da6f0af090b86357407', 'uqDrEzh7So', 'normal', '江西省生物工程研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省自动化学会@jict.org', false, '15407910095', 'c770ec05cd17942e04ef3e11686f3553', 'vbzJpnsa92', 'normal', '江西省自动化学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省计量测试学会@jict.org', false, '15407910096', 'c268ca5450d611a9834e40cbfb089deb', '1Irbw7EDPH', 'normal', '江西省计量测试学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省标准化协会@jict.org', false, '15407910097', '50a975b0e12a8a7ae036e8eaf394586c', 'pkRQSgpNYR', 'normal', '江西省标准化协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省工程图学学会@jict.org', false, '15407910098', 'b8293a60f8f9b53a02851704c83715e6', 'IU2W2Gjys9', 'normal', '江西省工程图学学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省电子学会@jict.org', false, '15407910099', '4341faf610b9327bf1895c6d6505c678', '6FnUh3bYnd', 'normal', '江西省电子学会',nextval ('seq_sec_user_id'))
, (false, false, '南昌工程学院科协@jict.org', false, '15407910100', 'e082efd41047536d68a9f458a9b5e076', 'mReS31tne6', 'normal', '南昌工程学院科协',nextval ('seq_sec_user_id')) 
, (false, false, '南昌理工学院科协@jict.org', false, '15407910101', '0149ea2f438463091f948c22890ba397', 'Sherjvvnjw', 'normal', '南昌理工学院科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西科技学院科协@jict.org', false, '15407910102', 'ce9742181f8a2a44fb40bc79bc5b9ad5', 'asCfYknRYU', 'normal', '江西科技学院科协',nextval ('seq_sec_user_id')) 
, (false, false, '宜春学院科协@jict.org', false, '15407910103', '67c15f61543fecfd0738263d69908345', 'k6taOeYRNt', 'normal', '宜春学院科协',nextval ('seq_sec_user_id')) 
, (false, false, '新余学院科协@jict.org', false, '15407910104', '2654ebd633a5fac80aea276763905f1c', '3zY2Goeu5W', 'normal', '新余学院科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西理工大学科协@jict.org', false, '15407910105', 'a2fb7eb9064ffdd8d9cc6b6d181f55a1', 'Y9KrFp8Cjh', 'normal', '江西理工大学科协',nextval ('seq_sec_user_id')) 
, (false, false, '景德镇陶瓷大学科协@jict.org', false, '15407910106', '767c1ace357fdb049458d9a551ff2f11', '95qkygg8VE', 'normal', '景德镇陶瓷大学科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西工程学院科协@jict.org', false, '15407910107', '8987c271bc73fd989da04652e34d2874', 'gjXPTExTBN', 'normal', '江西工程学院科协',nextval ('seq_sec_user_id')) 
, (false, false, '赣南师范学院科协@jict.org', false, '15407910108', '6d549b8acec562385b36080e8c455125', 'MV0EjNkjVE', 'normal', '赣南师范学院科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西农业大学科协@jict.org', false, '15407910109', '83ed07300bd6a2bf8cc20a79e708d657', 'T9PZlcmevG', 'normal', '江西农业大学科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西中医药大学科协@jict.org', false, '15407910110', 'b939f5ab878b66ee97402a51001d7670', 'O2znBjbr5j', 'normal', '江西中医药大学科协',nextval ('seq_sec_user_id')) 
, (false, false, '井冈山大学科协@jict.org', false, '15407910111', 'a98e626cff977ca07ec1820871b86d9a', 'ESqBRqXs0q', 'normal', '井冈山大学科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西省土壤学会@jict.org', false, '15407910112', '951d6ca31313c0770f5f68f11dec0682', 'zBNeKEcTsj', 'normal', '江西省土壤学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省水产学会@jict.org', false, '15407910113', '845843fc4e653191cca0a00627f6cf54', 'RgCznpwvFB', 'normal', '江西省水产学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省园艺学会@jict.org', false, '15407910114', '8375038e66a25135a665e16ad306ab4e', 'eq8Jsg9f5K', 'normal', '江西省园艺学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省畜牧兽医学会@jict.org', false, '15407910115', '32b4c07b9775b3179c3f472271a0684b', 'CH3E8jI4NX', 'normal', '江西省畜牧兽医学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省植物病理学会@jict.org', false, '15407910116', '7787073fbf3860995189ece146f0a311', 'qwdyNadQ8D', 'normal', '江西省植物病理学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省植物保护学会@jict.org', false, '15407910117', '1d10afa21a9d803fb3d11f63ba3d96ae', 'w3s6krlRCf', 'normal', '江西省植物保护学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省作物学会@jict.org', false, '15407910118', 'f0902dbb183941a91127ddf7d7371f58', 'dzXtCTfLdw', 'normal', '江西省作物学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省饲料学会@jict.org', false, '15407910119', 'eebd2fda75a3b6771bebe92d658b8a8b', 'NQrYm0eHBs', 'normal', '江西省饲料学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省生态经济学会@jict.org', false, '15407910120', '47dac4c93da79c0109fa3d169bf75a1a', 'Oqv2uIoAgp', 'normal', '江西省生态经济学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省水土保持学会@jict.org', false, '15407910121', '1472af72f794ce6a3dc76250553da538', 'QU7sCPE2rp', 'normal', '江西省水土保持学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省农村专业技术协会@jict.org', false, '15407910122', '944b59f07d24cd27c266eaa6b851ec04', 'W0ZeO1lLvS', 'normal', '江西省农村专业技术协会',nextval ('seq_sec_user_id'))
, (false, false, '江西省农业机械学会@jict.org', false, '15407910123', '47888f4e54b2562c693c946351a8e889', 'A6WzKw3gy6', 'normal', '江西省农业机械学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省农业工程学会@jict.org', false, '15407910124', '6167670704ac8f6336827574695e2238', 'zOMmx4fwLC', 'normal', '江西省农业工程学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省水利学会@jict.org', false, '15407910125', '4dff28a1843c5e775a0ad143c90d3e97', '8b7RdybCmR', 'normal', '江西省水利学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省风景园林学会@jict.org', false, '15407910126', '6e66f1e92644497b769f79a064ebb896', 'a9LrT9AAjb', 'normal', '江西省风景园林学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省植物营养与肥料学会@jict.org', false, '15407910127', 'bb69a255aeca95b375d51ec5aac90ff5', 'M9eumj4xUA', 'normal', '江西省植物营养与肥料学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省微量元素与健康研究会@jict.org', false, '15407910128', 'abccb8c81a93e295cdddce2b7143cbb4', 'ggFGC3MatJ', 'normal', '江西省微量元素与健康研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省生物医学工程学会@jict.org', false, '15407910129', 'ac1be8ec24b5bfd45a94d07691f2cb02', 'rV9zxXhqlz', 'normal', '江西省生物医学工程学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省药理学会@jict.org', false, '15407910130', '88bc0ea232047def1a662ef0640db2a4', 'ZuIsrCHJvJ', 'normal', '江西省药理学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省介入心脏病学会@jict.org', false, '15407910131', '1f82bd61cf08762c0c563787002eb0bf', 'k3Rddpv3dp', 'normal', '江西省介入心脏病学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省心电学会@jict.org', false, '15407910132', '5c4d0c9708bff40901b4c739c2823650', 'ofvsfJaCdq', 'normal', '江西省心电学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省口腔医学会@jict.org', false, '15407910133', 'f56d53bcd8c6c6a49331b4d4e52b6bf9', 'Lfrilvv7R3', 'normal', '江西省口腔医学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省免疫学会@jict.org', false, '15407910134', '99287b55d5c0035c3e5b303f0cc15d5f', 'eixvjyjyNg', 'normal', '江西省免疫学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省睡眠医学会@jict.org', false, '15407910135', '96a540a29672051a984492c370152a04', 'V8poaM87Yt', 'normal', '江西省睡眠医学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省科学养生研究会@jict.org', false, '15407910136', '4cc95717f0c2e0e98539a68c34ac2f9a', 'JNP9AMkC5W', 'normal', '江西省科学养生研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省研究型医院学会@jict.org', false, '15407910137', '9ccd4e1748663443cbf68565277db129', 'eNdpocs1U4', 'normal', '江西省研究型医院学会',nextval ('seq_sec_user_id'))
, (false, false, '江西省传统中医中药研究会@jict.org', false, '15407910138', '96ef867237b3c3ecb49a524c2a0f23b2', 'D1Wmd1PG9n', 'normal', '江西省传统中医中药研究会',nextval ('seq_sec_user_id'))
, (false, false, '江西省老年科普保健协会@jict.org', false, '15407910139', '9c223f5d134241aadcde6b54a469be84', 'kwsYPG49t9', 'normal', '江西省老年科普保健协会',nextval ('seq_sec_user_id'))
, (false, false, '东华理工大学科协@jict.org', false, '15407910140', '8dab12d155d8049cbfbe84a6c2a94ba0', 'XAbPtSOJSh', 'normal', '东华理工大学科协',nextval ('seq_sec_user_id')) 
, (false, false, '南昌师范学院科协@jict.org', false, '15407910141', 'e5a3aa761551b17ea2a3c7e3176cbf49', 'PFdppaWjPm', 'normal', '南昌师范学院科协',nextval ('seq_sec_user_id')) 
, (false, false, '景德镇学院科协@jict.org', false, '15407910142', '0aa513d989e4b79c34e82cbbdf2ab95e', 'zeFUs8DGLS', 'normal', '景德镇学院科协',nextval ('seq_sec_user_id')) 
, (false, false, '南昌航空大学科协@jict.org', false, '15407910143', '94744818c1534ae5527cf1293641d6b0', '0Na9gmiLz7', 'normal', '南昌航空大学科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西师范大学科协@jict.org', false, '15407910144', '254f2a9b2c9245138719aed10f4bf456', 'OL1e56Hy83', 'normal', '江西师范大学科协',nextval ('seq_sec_user_id')) 
, (false, false, '江西省抗癫痫病协会@jict.org', false, '15407910145', 'b5f64b82a7512e2c7ced835cf399f5d6', 'hglpuUK2lJ', 'normal', '江西省抗癫痫病协会',nextval ('seq_sec_user_id'));



