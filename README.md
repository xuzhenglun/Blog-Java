# J2EE报告
---
##需求分析
 - 任务概述
    1. 目标
    实现一个博客程序，能够动态的添加删除博文，并且博文具有分类，标签，评论功能。  
    2. 系统/用户特点
    - 运行平台为Linux操作系统，基于JVM虚拟机，保留向Windows移植的潜力。
    - 主要客户面向普通人，对博客的操作要求方便易管理。
 - 假定和约束
    项目基于个人兴趣处于学习目的开发，源代码通过GNUv3协议开源。
 - 需求规定
    1. 博客系统大概分为三个功能：
        1. 首页：
        - 列表化展示当前已有博文，并显示创建时间，所属分类，等信息。
        - 右侧提供分类筛选功能。
    2. 分类：
    分类统计当前博文，并可以手动添加删除分类。
    3. 博文：
    - 添加修改博文，支持Markdown语法。
    - 并提供附件上传下载功能，已便于插入图片和超链接等。
    - 提供评论添加删除功能，显示评论用户，创建时间等。
 - 安全问题：
    1. 对评论进行SQL过滤，防止SQL注入攻击。
    2. 评论采取注册实名方式，避免恶意评论洪水攻击。
    3. 评论用户注册添加验证码，防止机器人。
    4. 密码只保存Hash，避免被攻击后密码泄露问题。
 - 运行环境规定
    - JAVA 1.8或以上
    - MariaDB
    - Linux 测试基于Archlinux 2016.2.20
 - 尚需解决问题
    - 访问量客观不排除插入广告
    - JAVA太重了，不适合博客系统

---
##设计
 1. 架构设计
系统为B/S架构
    1. 服务端：
    - 总体框架采用Spring Boot，内嵌Spring MVC
    - 表示层框架采用Thymeleaf
    - 持久层框架采用Mybatis
    2. 浏览器端：
    - Jquery库
    - Bootstrap框架
 2. 数据库设计
    项目分为三张表：
    1. 用户表[User]:
    需要字段：
        - 用户Id，主键自增
        - 用户名，char(10)
        - 用户密码，char(32)
        - 用户组，int(4)
    2. 博文表[Topic]：
    需要字段：
        - 博文Id，主键自增
        - 博文标题，char(20)
        - 博文分类，char(20)
        - 博文标签，char(10)
        - 博文内容，text
        - 博文附件名，char(20)
        - 博文作者Id，int(4)
        - 博文创建时间，datetime
        - 博文修改时间，timestamp
        - 博文最后回复时间，datetime
        - 博文浏览数，int(4)
        - 博文评论数，int(4)
        - 博文回复数，int(4)
    3. 评论表[Comment]：
    需要字段：
        - 评论Id,主键自增
        - 评论所属博文Id，int(4)
        - 创建评论用户Id，int(4)
        - 评论内容，text
        - 评论创建时间，timestamp
    4. 分类表[Category]：
    需要字段：
        - 分类Id，主键自增
        - 分类名，char(20)唯一
        - 分类包含有博文数量，int(4)
 3. 开发构建工具：
 - IntelliJ IDEA
 - Gradle
 - Git

---
##实现
1. Spring Boot
    新建一个Spring Boot项目，选用Gradle管理，组建勾选Web，Thymeleaf，JDBC与MySQL。等新建完成后，编辑`build.gradle`，在依赖中插入以下代码以支持Mybatis框架。

    ```
    compile('org.mybatis.spring.boot:mybatis-spring-boot-starter:1.0.0')
    ```

2. 持久层Mybatis
    1. 编辑`application.properties`,插入以下代码用以连接数据库：
    ```
    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/blog?useUnico    de=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull
    spring.datasource.username=root
    spring.datasource.password=123456789
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    ```

    2. 新建实体类，用以将数据库结果映射到对象：
    ```
    Category.java
    Comment.java
    Topic.java
    User.java
    ```

    3. 编写Mapper，以添加新博文为例：
    ```JAVA
    public interface TopicMapper {

        @Insert("INSERT INTO Topic(Uid,Title,Category,Tag,Content,Attachment) "+
               "VALUE (#{Uid},#{Title},#{Category},#{Tag},#{Content},#{Attachment})")
       void insertTopic(@Param("Uid") long Uid,
                        @Param("Title") String Title,
                        @Param("Category") String Category,
                        @Param("Tag") String Tag,
                        @Param("Content") String Content,
                        @Param("Attachment") String Attachment);
    }
     ```

3. 表示层Thymeleaf
    1. 将部分每个页面都有的组建提取出来组成一个Fragment，存入单独的文件中，如HTML头信息：
    ```HTML
<head th:fragment="header">
    <link rel="shortcut icon" href="/img/favicon.png"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>

    <title th:text="${title}"></title>
    <script th:replace="fragment/jslibs::LoginStatus"/>
</head>
    ```
        在需要调用的地方使用标签属性插入，如：
    ```HTML
    <head th:replace="fragment/header :: header"/>
    ```

    2. 模板填充：
    使用标签属性进行填充，支持枚举，内联等：
    ```HTML
    <div class="page-header" th:each="topic:${Topics}">
       <h1>
            <li>
                <a th:href="'/topic/view/'+${topic.Id}" th:text="${topic.Title}"/>
            </li>
        </h1>
        <h6 class="test-muted" th:text="'Created:' + ${topic.Created}+'. Views:' + ${topic.Views}+'. Replies:'+${topic.ReplyCounts}+'.'"/>
        <p th:text="${topic.Content}"/>
    </div>
    ```
4. 编写Controller，事务逻辑，以首页为例(未实现翻页):
    ```java
    @Controller
    public class index {

        @RequestMapping("/")
        public String redirect() {
            return "redirect:/index";
        }

        @Autowired
        private TopicMapper topics;
        @Autowired
        private CategoryMapper category;

        @RequestMapping("/index")
        public String index(Model model,
                            @RequestParam(value = "cate", defaultValue = "null") String cate) {
            model.addAttribute("title", "Home");
            model.addAttribute("isHome", true);
            if (cate.equals("null")) {
                model.addAttribute("Topics", topics.findAllTopics());
            } else {
                model.addAttribute("Topics",     topics.findAllTopicsByCategory(cate));
            }
            model.addAttribute("Categories", category.findAllCategories());
            return "index";
        }
    }
    ```

---
##运行

1. 编译打包单独的Jar文件，并运行：
	1. 数据库建表
	```sql
    CREATE DATABASE blog;
	USE blog;
	CREATE TABLE User(
		Id int(4) primary key auto_increment,
        Name char(30) not null,
        PasswdHash char(32) not null,
        Groups int(4)
	);
	CREATE TABLE Topic(
	    Id int(4) primary key auto_increment,
	    Title char(20) not null,
        Category char(20),
        Tag char(10),
        Content text not null,
        Attachment char(100),
        Uid int(4),
        Views int(4) DEFAULT 0,
        ReplyCounts int(4) DEFAULT 0,
	    Updated datetime,
        Created timestamp,
        LastReply datetime,
        LastReplyUserId int(4)
	);
	CREATE TABLE Comment(
		Id int(4) primary key auto_increment,
        Tid int(4) not null,
        Uid int(4) not null,
        Content text not null,
        Created timestamp
	);
	CREATE TABLE Category(
		Id int(4) primary key auto_increment,
		Category char(20) UNIQUE not null,
			TopicCounts int(4) default 0
	);
	```
	2. 编译安装运行
	```bash
    gradle build
    cd build
    java -jar libs/Blog-0.0.1-SNAPSHOT.jar
    ```

---
##未完成(Todo)
1. 首页分页显示
2. 博文Markdown支持
3. 多附件上传


  [1]: https://github.com/xuzhenglun/Blog-Java.git
