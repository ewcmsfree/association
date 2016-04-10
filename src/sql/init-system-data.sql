/**
 * 图标初始化
 */

delete from sys_icon;

insert into sys_icon(id, css_class, description, icon_height, icon_type, identity, img_src, icon_left, sprite_src, icon_style, icon_top, icon_width) values
(1,'','系统管理-图标管理',48,'css_sprite','ewcms-system-icon','',0,'static/image/menu/system/icon.png','',0,48)
,(2,'','系统管理-图标管理',18,'css_sprite','ewcms-system-icon-tree','',0,'static/image/menu/system/icon.png','background-size:100%;',0,16)
,(3,'','系统管理-历史记录',48,'css_sprite','ewcms-system-history','',0,'static/image/menu/system/history.png','',0,48)
,(4,'','系统管理-历史记录',18,'css_sprite','ewcms-system-history-tree','',0,'static/image/menu/system/history.png','background-size:100%;',0,16)
,(5,'','系统管理-数据源',48,'css_sprite','ewcms-system-externalds','',0,'static/image/menu/system/externalds.png','',0,48)
,(6,'','系统管理-数据源',18,'css_sprite','ewcms-system-externalds-tree','',0,'static/image/menu/system/externalds.png','background-size:100%;',0,16)
,(7,'','系统管理-定时任务-任务设置',48,'css_sprite','ewcms-system-scheduling-jobinfo','',0,'static/image/menu/system/schedulingjobinfo.png','',0,48)
,(8,'','系统管理-定时任务-任务设置',18,'css_sprite','ewcms-system-scheduling-jobinfo-tree','',0,'static/image/menu/system/schedulingjobinfo.png','background-size:100%;',0,16)
,(9,'','系统管理-定时任务-作业设置',48,'css_sprite','ewcms-system-scheduling-jobclass','',0,'static/image/menu/system/schedulingjobclass.png','',0,48)
,(10,'','系统管理-定时任务-作业设置',18,'css_sprite','ewcms-system-scheduling-jobclass-tree','',0,'static/image/menu/system/schedulingjobclass.png','background-size:100%;',0,16)
,(11,'','系统管理-报表管理-文字报表',48,'css_sprite','ewcms-system-report-text','',0,'static/image/menu/system/textreport.png','',0,48)
,(12,'','系统管理-报表管理-文字报表',18,'css_sprite','ewcms-system-report-text-tree','',0,'static/image/menu/system/textreport.png','background-size:100%;',0,16)
,(13,'','系统管理-报表管理-图形报表',48,'css_sprite','ewcms-system-report-chart','',0,'static/image/menu/system/chartreport.png','',0,48)
,(14,'','系统管理-报表管理-图形报表',18,'css_sprite','ewcms-system-report-chart-tree','',0,'static/image/menu/system/chartreport.png','background-size:100%;',0,16)
,(15,'','系统管理-报表管理-报表分类',48,'css_sprite','ewcms-system-report-category','',0,'static/image/menu/system/categoryreport.png','',0,48)
,(16,'','系统管理-报表管理-报表分类',18,'css_sprite','ewcms-system-report-category-tree','',0,'static/image/menu/system/categoryreport.png','background-size:100%;',0,16)
,(17,'','系统管理-报表管理-报表存储',48,'css_sprite','ewcms-system-report-repository','',0,'static/image/menu/system/repositoryreport.png','',0,48)
,(18,'','系统管理-报表管理-报表存储',18,'css_sprite','ewcms-system-report-repository-tree','',0,'static/image/menu/system/repositoryreport.png','background-size:100%;',0,16)
,(19,'','系统管理-报表管理-报表集',48,'css_sprite','ewcms-system-report-show','',0,'static/image/menu/system/showreport.png','',0,48)
,(20,'','系统管理-报表管理-报表集',18,'css_sprite','ewcms-system-report-show-tree','',0,'static/image/menu/system/showreport.png','background-size:100%;',0,16)
,(21,'','权限管理-控制列表',48,'css_sprite','ewcms-security-resource','',0,'static/image/menu/security/resource.png','',0,48)
,(22,'','权限管理-控制列表',18,'css_sprite','ewcms-security-resource-tree','',0,'static/image/menu/security/resource.png','background-size:100%;',0,16)
,(23,'','权限管理-分组列表',48,'css_sprite','ewcms-security-group','',0,'static/image/menu/security/group.png','',0,48)
,(24,'','权限管理-分组列表',18,'css_sprite','ewcms-security-group-tree','',0,'static/image/menu/security/group.png','background-size:100%;',0,16)
,(25,'','权限管理-用户管理-用户列表',48,'css_sprite','ewcms-security-user-user','',0,'static/image/menu/security/user.png','',0,48)
,(26,'','权限管理-用户管理-用户列表',18,'css_sprite','ewcms-security-user-user-tree','',0,'static/image/menu/security/user.png','background-size:100%;',0,16)
,(27,'','权限管理-用户管理-在线用户列表',48,'css_sprite','ewcms-security-user-online','',0,'static/image/menu/security/online.png','',0,48)
,(28,'','权限管理-用户管理-在线用户列表',18,'css_sprite','ewcms-security-user-online-tree','',0,'static/image/menu/security/online.png','background-size:100%;',0,16)
,(29,'','权限管理-用户管理-状态变更历史列表',48,'css_sprite','ewcms-security-user-statushistory','',0,'static/image/menu/security/statusHistory.png','',0,48)
,(30,'','权限管理-用户管理-状态变更历史列表',18,'css_sprite','ewcms-security-user-statushistory-tree','',0,'static/image/menu/security/statusHistory.png','background-size:100%;',0,16)
,(31,'','权限管理-用户管理-用户最后在线历史列表',48,'css_sprite','ewcms-security-user-lastonline','',0,'static/image/menu/security/lastOnline.png','',0,48)
,(32,'','权限管理-用户管理-用户最后在线历史列表',18,'css_sprite','ewcms-security-user-lastonline-tree','',0,'static/image/menu/security/lastOnline.png','background-size:100%;',0,16)
,(33,'','权限管理-组织机构管理-组织机构列表',48,'css_sprite','ewcms-security-organization-organiation','',0,'static/image/menu/security/organization.png','',0,48)
,(34,'','权限管理-组织机构管理-组织机构列表',18,'css_sprite','ewcms-security-organization-organiation-tree','',0,'static/image/menu/security/organization.png','background-size:100%;',0,16)
,(35,'','权限管理-组织机构管理-工作职务列表',48,'css_sprite','ewcms-security-organization-job','',0,'static/image/menu/security/job.png','',0,48)
,(36,'','权限管理-组织机构管理-工作职务列表',18,'css_sprite','ewcms-security-organization-job-tree','',0,'static/image/menu/security/job.png','background-size:100%;',0,16)
,(37,'','权限管理-权限管理-权限列表',48,'css_sprite','ewcms-security-permission-permission','',0,'static/image/menu/security/permission.png','',0,48)
,(38,'','权限管理-权限管理-权限列表',18,'css_sprite','ewcms-security-permission-permission-tree','',0,'static/image/menu/security/permission.png','background-size:100%;',0,16)
,(39,'','权限管理-权限管理-授权权限给角色',48,'css_sprite','ewcms-security-permission-role','',0,'static/image/menu/security/role.png','',0,48)
,(40,'','权限管理-权限管理-授权权限给角色',18,'css_sprite','ewcms-security-permission-role-tree','',0,'static/image/menu/security/role.png','background-size:100%;',0,16)
,(41,'','权限管理-权限管理-授权角色给实体',48,'css_sprite','ewcms-security-permission-auth','',0,'static/image/menu/security/auth.png','',0,48)
,(42,'','权限管理-权限管理-授权角色给实体',18,'css_sprite','ewcms-security-permission-auth-tree','',0,'static/image/menu/security/auth.png','background-size:100%;',0,16)
,(43,'','专家管理-津贴管理',48,'css_sprite','ewcms-personnel-allowance','',0,'static/image/menu/personnel/allowance.png','',0,48)
,(44,'','专家管理-津贴管理',18,'css_sprite','ewcms-personnel-allowance-tree','',0,'static/image/menu/personnel/allowance.png','background-size:100%;',0,16)
,(45,'','专家管理-学会分类',48,'css_sprite','ewcms-personnel-category','',0,'static/image/menu/personnel/category.png','',0,48)
,(46,'','专家管理-学会分类',18,'css_sprite','ewcms-personnel-category-tree','',0,'static/image/menu/personnel/category.png','background-size:100%;',0,16)
,(47,'','专家管理-民族管理',48,'css_sprite','ewcms-personnel-nation','',0,'static/image/menu/personnel/nation.png','',0,48)
,(48,'','专家管理-民族管理',18,'css_sprite','ewcms-personnel-nation-tree','',0,'static/image/menu/personnel/nation.png','background-size:100%;',0,16)
,(49,'','专家管理-登记表管理',48,'css_sprite','ewcms-personnel-archive','',0,'static/image/menu/personnel/archive.png','',0,48)
,(50,'','专家管理-登记表管理',18,'css_sprite','ewcms-personnel-archive-tree','',0,'static/image/menu/personnel/archive.png','background-size:100%;',0,16)
,(51,'','个人中心-登记表',48,'css_sprite','ewcms-personal-archive','',0,'static/image/menu/personal/archive.png','',0,48)
,(52,'','个人中心-登记表',18,'css_sprite','ewcms-personal-archive-tree','',0,'static/image/menu/personal/archive.png','background-size:100%;',0,16)
,(53,'','个人中心-切换身份',48,'css_sprite','ewcms-security-switch','',0,'static/image/menu/security/switch.png','',0,48)
,(54,'','个人中心-切换身份',18,'css_sprite','ewcms-security-switch-tree','',0,'static/image/menu/security/switch.png','background-size:100%;',0,16)
,(55,'','个人中心-备忘录',48,'css_sprite','ewcms-personal-memoranda','',0,'static/image/menu/personal/memoranda.png','',0,48)
,(56,'','个人中心-备忘录',18,'css_sprite','ewcms-personal-memoranda-tree','',0,'static/image/menu/personal/memoranda.png','background-size:100%;',0,16)
,(57,'','个人中心-个人消息',48,'css_sprite','ewcms-personal-message','',0,'static/image/menu/personal/message.png','',0,48)
,(58,'','个人中心-个人消息',18,'css_sprite','ewcms-personal-message-tree','',0,'static/image/menu/personal/message.png','background-size:100%;',0,16)
,(59,'','专家管理-当前情况管理',48,'css_sprite','ewcms-personnel-currentstate','',0,'static/image/menu/personnel/currentstate.png','',0,48)
,(60,'','专家管理-当前情况管理',18,'css_sprite','ewcms-personnel-currentstate-tree','',0,'static/image/menu/personnel/currentstate.png','background-size:100%;',0,16)
,(62,'','监控管理-数据库监控',48,'css_sprite','ewcms-monitor-db','',0,'static/image/menu/monitor/db.png','',0,48)
,(63,'','监控管理-数据库监控',18,'css_sprite','ewcms-monitor-db-tree','',0,'static/image/menu/monitor/db.png','background-size:100%;',0,16)
,(64,'','监控管理-Ehcache监控',48,'css_sprite','ewcms-monitor-ehcache','',0,'static/image/menu/monitor/ehcache.png','',0,48)
,(65,'','监控管理-Ehcache监控',18,'css_sprite','ewcms-monitor-ehcache-tree','',0,'static/image/menu/monitor/ehcache.png','background-size:100%;',0,16)
,(66,'','监控管理-JVM监控',48,'css_sprite','ewcms-monitor-jvm','',0,'static/image/menu/monitor/jvm.png','',0,48)
,(67,'','监控管理-JVM监控',18,'css_sprite','ewcms-monitor-jvm-tree','',0,'static/image/menu/monitor/jvm.png','background-size:100%;',0,16)
,(68,'','监控管理-SQL操作',48,'css_sprite','ewcms-monitor-sql','',0,'static/image/menu/monitor/sql.png','',0,48)
,(69,'','监控管理-SQL操作',18,'css_sprite','ewcms-monitor-sql-tree','',0,'static/image/menu/monitor/sql.png','background-size:100%;',0,16)
,(70,'','监控管理-JPAQL操作',48,'css_sprite','ewcms-monitor-jpaql','',0,'static/image/menu/monitor/jpaql.png','',0,48)
,(71,'','监控管理-JPAQL操作',18,'css_sprite','ewcms-monitor-jpaql-tree','',0,'static/image/menu/monitor/jpaql.png','background-size:100%;',0,16)
,(72,'','监控管理-Hibernate监控',48,'css_sprite','ewcms-monitor-hibernate','',0,'static/image/menu/monitor/hibernate.png','',0,48)
,(73,'','监控管理-Hibernate监控',18,'css_sprite','ewcms-monitor-hibernate-tree','',0,'static/image/menu/monitor/hibernate.png','background-size:100%;',0,16)
;

select setval('seq_sys_icon_id', 73);