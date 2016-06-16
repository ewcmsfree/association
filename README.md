##江西省科协管理系统
* 目前作为基本的人事管理系统

##数据库备份
	pg_dump --host localhost --username postgres --encoding UTF8 --file C:\ewcms_association.dump ewcms_association

##数据库恢复
	psql --host localhost --username postgres --dbname ewcms_association < E:\work\ewcms_association.dump

##修改catalina.sh
	JAVA_OPTS="$JAVA_OPTS -server -Xms800m -Xmx800m -XX:PermSize=64m -XX:MaxNewSize=256m -XX:MaxPermSize=128m -Djava.awt.headless=true"

##在%TOMCAT_HOM%/conf/server.xml其中：
	把
	<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"/>
	替换成
	<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443" URIEncoding="UTF-8"/>       
        
##iReport开发的报表乱码，增加如下设置:
	CATALINA_OPTS='-Djava.awt.headless=true' 

##压力测试:
	C:\apache>ab -n 100 -c 30 http://域名/
	//平台apache 版本2.4.2
	Server Software:        Apache/2.4.2
	//服务器主机名
	Server Hostname:        117.40.173.81
	//服务器端口
	Server Port:            80
	
	//测试的页面文档
	Document Path:          /
	//文档大小
	Document Length:        62389 bytes
	
	//并发数
	Concurrency Level:      30
	//整个测试持续的时间
	Time taken for tests:   14.125 seconds
	//完成的请求数量
	Complete requests:      100
	//失败的请求数量
	Failed requests:        0
	//写入的错误数量
	Write errors:           0
	//整个场景中的网络传输量
	Total transferred:      6263700 bytes
	//整个场景中的HTML内容传输量
	HTML transferred:       6238900 bytes
	//重要的指标之一，相当于 LR 中的每秒事务数，后面括号中的 mean 表示这是一个平均值
	Requests per second:    7.08 [#/sec] (mean)
	//重要的指标之二，相当于 LR 中的平均事务响应时间，后面括号中的 mean 表示这是一个平均值
	Time per request:       4237.500 [ms] (mean)
	//每个请求实际运行时间的平均值
	Time per request:       141.250 [ms] (mean, across all concurrent requests)
	//平均每秒网络上的流量，可以帮助排除是否存在网络流量过大导致响应时间延长的问题
	Transfer rate:          433.05 [Kbytes/sec] received
	
	Connection Times (ms)
	              min  mean[+/-sd] median   max
	Connect:       47  134  59.2    125     359
	Processing:   625 3532 1165.1   3938    4578
	Waiting:       63 1470 945.0   1344    3594
	Total:        719 3666 1161.4   4047    4688
	
	Percentage of the requests served within a certain time (ms)
	  50%   4047
	  66%   4297
	  75%   4406
	  80%   4500
	  90%   4578
	  95%   4641
	  98%   4688
	  99%   4688
	 100%   4688 (longest request)
	 
	由于对于并发请求，cpu实际上并不是同时处理的，而是按照每个请求获得的时间片逐个轮转处理的，所以基本上第一个Time per request时间约等于第二个Time per request时间乘以并发请求数。
	
	13d3e49b5f3aea2572f1c767491a8e60
	
	for var in *_zip.jpg; do mv "$var" "${var%_zip.jpg}_thumb.jpg"; done