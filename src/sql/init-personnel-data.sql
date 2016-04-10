/**
 * 民族初始化
 */
delete from pel_nation;

insert into pel_nation(id,name) values 
(1,'汉族')
,(2,'蒙古族')
,(3,'回族')
,(4,'藏族')
,(5,'维吾尔族')
,(6,'苗族')
,(7,'彝族')
,(8,'壮族')
,(9,'布依族')
,(10,'朝鲜族')
,(11,'满族')
,(12,'侗族')
,(13,'瑶族')
,(14,'白族')
,(15,'土家族')
,(16,'哈尼族')
,(17,'哈萨克族')
,(18,'傣族')
,(19,'黎族')
,(20,'傈僳族')
,(21,'佤族')
,(22,'畲族')
,(23,'高山族')
,(24,'拉祜族')
,(25,'水族')
,(26,'东乡族')
,(27,'纳西族')
,(28,'景颇族')
,(29,'柯尔克孜族')
,(30,'土族')
,(31,'达斡尔族')
,(32,'仫佬族')
,(33,'羌族')
,(34,'布朗族')
,(35,'撒拉族')
,(36,'毛难族')
,(37,'仡佬族')
,(38,'锡伯族')
,(39,'阿昌族')
,(40,'普米族')
,(41,'塔吉克族')
,(42,'怒族')
,(43,'乌孜别克族')
,(44,'俄罗斯族')
,(45,'鄂温克族')
,(46,'德昂族')
,(47,'保安族')
,(48,'裕固族')
,(49,'京族')
,(50,'塔塔尔族')
,(51,'独龙族')
,(52,'鄂伦春族')
,(53,'赫哲族')
,(54,'门巴族')
,(55,'珞巴族')
,(56,'基诺族')
,(57,'其它')
,(58,'外国血统中国籍人士')
;

select setval('seq_pel_nation_id', 58);

/**
 * 享受津贴初始化
 */
delete from pel_allowance;

insert into pel_allowance(id, name) values 
(1, '国贴')
,(2, '省贴')
,(3, '市贴')
;

select setval('seq_pel_allowance_id', 3);

/**
 * 目前情况初始化
 */
delete from pel_current_state;

insert into pel_current_state(id, name) values 
(1, '在职')
,(2, '离退体')
,(3, '出国')
,(4, '其他')
;

select setval('seq_pel_current_state_id', 4);