# Trans
## 能干什么
* 数据持久层与数据缓存层的数据解耦
* 数据库表和表之间的字段数据冗余同步问题
## 是什么
1. 基于服务端和客户端消息推送和监听。
2. 用于监听数据库表中数据的改变（由服务端监听数据库，并推送客户端），
3. 应用启动客户端接受到了数据库信息数据后，可以做相关的业务实现。
## 架构图
![GitHub set up]()
## 怎么用
### 服务端
1. 拉去代码
2. 配置服务端需要监听的数据库(可以配置多个数据库)，
在trans-server工程中application.yml 配置
     ```js
     trans:
       db: 
         mysql:
           dataSource:
             could:
               host: xxxxx
               port: xxxxx
               username: xxxxx
               password: xxxxx
     ```
    <br> 注意 could 为连接名，由用户自己设置，这个名称需要在客户端配置。
    若多个连接（不同IP的数据库，连接名不同）
 3.运行TransAppRunner类,服务端就可以启动了
 ### 客户端
 1. 拉去代码
 2. 配置监听的相关json文件 
    ```js
    {
      "listenerConfig":[{
        "dbConnectionName": "连接名,是客户端所监听服务端的连接名(上面配置could)",
        "databaseList":[{
          "databaseName": "数据库的名称",
          "tableList": [{
            "tableName": "表名称",
            "op":[{
              "opType":"insert,监听插入操作",
              "opFiled":[
                {"column": "字段名称"},
              ]},
              {
                "opType":"update,监听更新操作",
                "opFiled": [
                  {"column": "id"},
                ]
              },
              {
                "opType":"delete，监听删除操作",
                "opFiled": [
                  {"column": "id"}
                ]
              }]
          }]
        }]
      }]
    }

    ```
3. 启动客户端
    ```js
    Trans trans = new Trans("template.json");
    try {
        trans.start("127.0.0.1",8091);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    ```
