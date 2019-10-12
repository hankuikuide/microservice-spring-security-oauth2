
# 项目介绍
该项目是一个演示项目，主要演示了，基于spring boot2.0+spring security +oauth2.0+ jwt构建的，单点登录SSO和统一认证和授权的微服务架构项目

该项目是一个多模块项目：
## authorization-server:是一个认证服务器
* 通常情况下，认证服务器也是一个资源服务器用于向其他服务提供用户及权限信息。
* 标准的OAuth2.0提供了四种认证模式，所以认证服务器也应该支持这四种认证模式，具体采用何种模式，应由客户端根据业务需求决定，
    如SSO比较适合授权码模式，客户端有自己的登录时，适合密码模式，服务间的认证可以用客户端模式。
* 为了提高token的安全性，及减轻认证服务器的压力，采用token+jwt的方式，演示代码没有用rsa。
  
## client:是客户端的应用程序
网上多数的项目客户端都是采用纯js写，或用postman发请求，和实际项目的应用还是有差距的，这里也是采用spring boot的实现。
主要功能在于：
* 使用授权码模式进行认证。
* 使用OAuth2RestTemplate发送请求给认证服务器和资源服务器，
* 结合Feign实现loadbalance.

## client2:也是客户端的应用程序
网上多数的项目客户端都是采用纯js写，或用postman发请求，和实际项目的应用还是有差距的，这里也是采用spring boot的实现。
主要功能在于：
* 使用授权码模式进行认证。
* 使用OAuth2RestTemplate发送请求给认证服务器和资源服务器，
* 结合Zuul网关实现请求转发给资源服务器.这也是区别于client的地方

##client与client2的主要作用：
* 演示SSO,只要一个客户端登录成功，另一个应用，也就自动登录成功。
* 演示SSO,只要一个客户端退出，另一个应用，也就自动退出。
* 分别用Feign和Zuul网关实现对资源服务器的访问

## resource-server：是资源服务器，也是微服务
资源服务器只实现了一个，另一个同时，只是注意jwt的配置即可。
## resource2-server：是资源服务器，也是微服务
同上

##两个资源服务器的意义在于：
1. 通常微服务架构中，微服务都是多个
2. 微服务之间通过会有访问，这里使用的是feign：resource访问resource2
3. 微服务之间应该也应该是有认证，这里使用是client_credentials模式


# 主要集成了以下技术和工具

* Spring boot2.0
* Spring Security
* OAuth2.0
* JWT
* Feign
* Zuul
* cors





