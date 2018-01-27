# sharding-db
springboot+jpa+Druid 实现分库分表
分库分表在数据量大的系统中比较常用，解决方案有Cobar，TDDL等，这次主要是拿当当网开源的Sharding-JDBC来做个小例子。 
它的github地址为：https://github.com/dangdangdotcom/sharding-jdbc 
简介： 
Sharding-JDBC直接封装JDBC API，可以理解为增强版的JDBC驱动，旧代码迁移成本几乎为零： 
可适用于任何基于java的ORM框架，如：JPA, Hibernate, Mybatis, Spring JDBC Template或直接使用JDBC。 
可基于任何第三方的数据库连接池，如：DBCP, C3P0, BoneCP, Druid等。 
理论上可支持任意实现JDBC规范的数据库。虽然目前仅支持MySQL，但已有支持Oracle，SQLServer，DB2等数据库的计划。 
Sharding-JDBC定位为轻量级java框架，使用客户端直连数据库，以jar包形式提供服务，未使用中间层，无需额外部署，无其他依赖，DBA也无需改变原有的运维方式。SQL解析使用Druid解析器，是目前性能最高的SQL解析器。 
具体的介绍可以上它的文档那里看看，简单归纳起来就是，它是一个增强版的JDBC，对使用者透明，逻辑代码什么的都不用动，它来完成分库分表的操作；然后它还支持分布式事务（不完善）。看起来很不错的样子。 
下面用个小例子来看一下分库分表的使用。使用的是SpringBoot，JPA（hibernate），druid连接池。
http://blog.csdn.net/wusd1256/article/details/79183970
