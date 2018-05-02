来源：https://www.2cto.com/kf/201612/583211.html

介绍：
spring分为6个部分：core、aop、data access、web、test、other

core核心包含4个模块：
spring-core：依赖注入IoC与DI的最基本实现
    依赖：comms-logging > comms-logging
spring-beans：Bean工厂与bean的装配
    依赖：spring-core
spring-context：spring的context上下文即IoC容器
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-aop
    依赖：spring-expression
spring-expression：spring表达式语言
    依赖：spring-core
    
core核心块依赖：
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>3.2.17.RELEASE</version>
    <!-- 日志实现了log4j,排除对commons-logging的依赖 -->
    <exclusions>
        <exclusion>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

aop包含4个模块：
spring-aop：面向切面编程
    依赖：spring-core
    依赖：spring-beans
    依赖：aopalliance > aopalliance
spring-aspects：集成AspectJ
    依赖：org.aspectj > aspectjweaver
spring-instrument：提供一些类级的工具支持和ClassLoader级的实现，用于服务器
spring-instrument-tomcat：针对tomcat的instrument实现

data access包含5个模块：
spring-jdbc：jdbc的支持
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-tx
spring-tx：事务控制
    依赖：spring-core
    依赖：spring-beans
spring-orm：对象关系映射，集成orm框架
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-jdbc
    依赖：spring-tx
spring-oxm：对象xml映射
    依赖：spring-core
    依赖：spring-beans
spring-jms：java消息服务
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-aop
    依赖：spring-tx
    依赖：spring-context
    
web包含3个模块
spring-web：基础web功能，如文件上传
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-aop
    依赖：spring-context
spring-webmvc：mvc实现
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-web
    依赖：spring-expression
    依赖：spring-context
spring-webmvc-portlet：基于portlet的mvc实现
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-web
    依赖：spring-webmvc
    依赖：spring-context
    
test包含1个模块
spring-test：spring测试，提供junit与mock测试功能
    依赖：spring-core
    
其他模块：
spring-context-support：spring额外支持包，比如邮件服务、视图解析等
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-context
spring-websocket：为web应用提供的高效通信工具
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-context
spring-messaging：用于构建基于消息的应用程序
    依赖：spring-core
    依赖：spring-beans
    依赖：spring-context