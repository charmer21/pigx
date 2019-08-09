## PigX Gateway

### 网关跨域支持

> 如果不知道什么是跨域(CORS)，建议先阅读以下内容
>
> [跨域资源共享 CORS 详解](http://www.ruanyifeng.com/blog/2016/04/cors.html)
>
> [HTTP访问控制（CORS）](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS)



#### 配置方式

在网关的配置文件中加入以下内容(请根据实际情况修改)

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]':
            allowCredentials: true
            exposedHeaders: "Content-Disposition,Content-Type,Cache-Control"
            allowedHeaders: "*"
            allowedOrigins: "*"
            allowedMethods: "*"
```

> - 以上配置可作为开发环境使用，生成环境建议你根据实际情况修改（请先理解CORS，并参考`org.springframework.cloud.gateway.config.GlobalCorsProperties`）
> - 跨域时会产生预检请求(Pre-Flight Request)，这样就会对你的服务器产生额外的网络请求。如果可以通过部署手段解决跨域，则可以关闭跨域支持，方法是把以上配置信息清理掉。



#### 测试

为了演示跨域支持效果，可以修改一下前端项目(`PigX-ui`)的登录代码

```javascript
export const loginByUsername = (username, password, code, randomStr) => {
  const grant_type = 'password'

  return request({
    // 原来的API接口地址
    //url: '/auth/oauth/token',
    // 为了触发造跨域，直接修改为网关的url,这里的IP需要改成你自己的，要用真实IP
    url: 'http://10.11.12.7:9999/auth/oauth/token',
    headers: {
      isToken:false,
      'TENANT_ID': '1',
      'Authorization': 'Basic cGlnOnBpZw=='
    },
    method: 'post',
    params: { username, password, randomStr, code, grant_type, scope }
  })
}
```



将前端项目跑起来，打开登录页面，观察点击登录按钮的XHR请求，你会发现浏览器发送了两次请求：

- 第一次是`OPTIONS`方式，这是因为浏览器检测到有一个请求跨域了，于是发出一个预检请求，问一问服务端"有啥处理意见"。
- 第二次是`POST`方式，也就是真正的需要执行的请求，这是因为“服务端的回答让浏览器满意”了，所以浏览器执行真正的请求。如果服务端（也就是`PigX Gateway`）没有进行相关配置，浏览器就没法得到它期望的答复，这一步便不会执行。