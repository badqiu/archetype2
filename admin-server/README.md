
# spring 多数据源使用

1. 需要使用依赖  

```
		<!-- 
	    mybatis支持多数据源: https://baomidou.com/guides/dynamic-datasource/ 
	    -->
		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>dynamic-datasource-spring-boot3-starter</artifactId>
			<version>4.3.1</version>
		</dependency>
```

2. Dao层接口上增加  

```
@DS("dataSourceName")
public interface DemoUserDao{
}
```

3. yaml配置数据源  

```
spring:
  datasource:
    dynamic:
      primary: master #设置默认的数据源或者数据源组,默认值即为master
      strict: false #设置严格模式,默认false不启动. 启动后在未匹配到指定数据源时候会抛出异常,不启动则使用默认数据源.
      datasource:
        master:
          url: jdbc:postgresql://localhost:5432/test_db
          username: 
          password: 
          driver-class-name: org.postgresql.Driver
        slaver:
          url: jdbc:mysql://localhost:9030/test_db
          username: 
          password: 
          driver-class-name: com.mysql.jdbc.Driver
```
 
