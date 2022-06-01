## servlet-nexus

1.	访问地址：http://localhost:8080/
2.	修改maven配置
```
<!--nexus服务器,id为组仓库name-->
  <servers>  
    <server>  
        <id>xzh-group</id>  
        <username>admin</username>  
        <password>admin</password>  
    </server>
    <server>  
        <id>xzh-hosted</id>  
        <username>admin</username>  
        <password>admin</password>  
    </server>
    <server>  
        <id>xzh-snapshots</id>  
        <username>admin</username>  
        <password>admin</password>  
    </server>
    <server>  
        <id>xzh-releases</id>  
        <username>admin</username>  
        <password>admin</password>  
    </server>   
  </servers>  
<!--仓库组的url地址，id和name可以写组仓库name，mirrorOf的值设置为central-->  
  <mirrors>     
    <mirror>  
        <id>xzh-group</id>  
        <name>xzh-group</name>  
        <url>http://172.17.17.200:8081/repository/xzh-group/</url>  
        <mirrorOf>central</mirrorOf>  
    </mirror>     
  </mirrors>
```



