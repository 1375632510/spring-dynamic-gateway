# spring-dynamic-gateway

基于SpringCloud Gateway的动态路由

### 技术栈

> SpringBoot、SpringCloud GateWay、SpringCloud Bus、SpringCloud Stream、SpringCloud Alibaba Nacos.
> 消息队列kafka(RabbitMq)、Redis、持久化存储MySQL、通用API文档Springfox Swagger.

### 项目结构

```
|--spring-dynamic-gateway
    |--livk-api-dynamic(动态路由操作服务)
        |--java(代码)
        |--resource
            |--http(idea Http Client测试请求)
            |--mapper(Mybatis xml文件)
            |--sql(初始化建表SQL与初始化表数据JSON)
    |--livk-api-gateway(网关服务)
    |--livk-api-monitor(SpringBoot监控服务)
    |--livk-cloud-common
        |--livk-common-core(通用核心基础包、内嵌Bus，根据Bus starter导入自动激活)
        |--livk-common-gateway(Gateway路由信息整合Redis处理)
        |--livk-common-log(日志纪录操作相关)
        |--livk-common-pom(common聚合POM依赖)
        |--livk-common-redis(edis与Spring Cache操作相关)
        |--livk-common-swagger(Swagger与Gateway聚合配置，Swagger单服务配置)
```

### 0.0.1

> 表SQL详见[SQL](./table.sql).
> 每一个XXXAutoConfiguration，请注意bean生成的条件，非必要不要修改，以免服务启动报错.
> Spring Cloud Bus需要与Spring Cloud Stream结合使用，同时需要一个Message Queue(官方使用RabbitMQ或者Kafka).
> [Gateway路由信息与Redis操作结合](./livk-cloud-common/livk-common-gateway/src/main/java/com/livk/common/gateway/support/RedisRouteDefinitionWriter.java).
> [Bus操作远程Event](./livk-cloud-common/livk-common-core/src/main/java/com/livk/common/core/event/LivkRemoteApplicationEvent.java).
> [Mapstruct转化器通用接口](./livk-cloud-common/livk-common-core/src/main/java/com/livk/common/core/converter/BaseConverter.java).
> [Swagger整合Gateway](./livk-cloud-common/livk-common-swagger/src/main/java/com/livk/common/swagger/support/GatewaySwaggerResourcesProvider.java).

