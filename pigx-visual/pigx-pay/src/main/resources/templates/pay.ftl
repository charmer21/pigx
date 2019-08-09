<!DOCTYPE html>
<html lang="en">
<head>
  <meta name="viewport"
        content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
  <meta charset="UTF-8">
  <title>Pigx Pro 支付模块</title>
</head>
<script src="//cdn.jsdelivr.net/jquery/1.12.1/jquery.min.js"></script>

<body>
<#if channel == 'WEIXIN_WAP'>
  <a href="javascript:void(0)" onclick="pay()">确认购买</a>
</#if>
</body>
</html>
<#if channel == 'WEIXIN_MP'>
  <script>
    function onBridgeReady() {
      WeixinJSBridge.invoke(
        'getBrandWCPayRequest', {
          "appId": "${params.appId}",     //公众号名称，由商户传入
          "timeStamp": "${params.timeStamp}",         //时间戳，自1970年以来的秒数
          "nonceStr": "${params.nonceStr}", //随机串
          "package": "${params.package}",
          "signType": "${params.signType}",         //微信签名方式：
          "paySign": "${params.paySign}" //微信签名,paySign 采用统一的微信支付 Sign 签名生成方法，注意这里 appId 也要参与签名，appId 与 config 中传入的 appId 一致，即最后参与签名的参数有appId, timeStamp, nonceStr, package, signType。
        },
        function (res) {
          if (res.err_msg == "get_brand_wcpay_request:ok") {     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            alert('支付成功！');
          } else {
            alert('支付失败：' + res.err_msg);
          }
          WeixinJSBridge.call('closeWindow');
        }
      );
    }

    if (typeof WeixinJSBridge == "undefined") {
      if (document.addEventListener) {
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
      } else if (document.attachEvent) {
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
      }
    } else {
      onBridgeReady();
    }
  </script>
</#if>
<#if channel == 'WEIXIN_WAP'>
  <script>
    function pay() {
      window.location.href = "${params}"
    }
  </script>
</#if>
