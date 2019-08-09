package com.pig4cloud.pigx.mp.config;

import com.google.common.collect.Maps;
import com.pig4cloud.pigx.mp.handler.*;
import com.pig4cloud.pigx.mp.service.WxAccountService;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

import static me.chanjar.weixin.common.api.WxConsts.*;

/**
 * @author Binary Wang
 * @author lengleng
 * <p>
 * 微信公众号基础配置，初始化配置
 */
@Configuration
public class WxMpConfiguration {
	public static Map<String, WxMpMessageRouter> getRouters() {
		return routers;
	}

	public static Map<String, WxMpService> getMpServices() {
		return mpServices;
	}

	private WxAccountService accountService;
	private LogHandler logHandler;
	private NullHandler nullHandler;
	private KfSessionHandler kfSessionHandler;
	private StoreCheckNotifyHandler storeCheckNotifyHandler;
	private LocationHandler locationHandler;
	private MenuHandler menuHandler;
	private MsgHandler msgHandler;
	private UnsubscribeHandler unsubscribeHandler;
	private SubscribeHandler subscribeHandler;
	private ScanHandler scanHandler;
	private RedisTemplate redisTemplate;

	private static Map<String, WxMpMessageRouter> routers = Maps.newHashMap();
	private static Map<String, WxMpService> mpServices = Maps.newHashMap();

	@Autowired
	public WxMpConfiguration(WxAccountService accountService, LogHandler logHandler, NullHandler nullHandler
			, KfSessionHandler kfSessionHandler, StoreCheckNotifyHandler storeCheckNotifyHandler
			, LocationHandler locationHandler, MenuHandler menuHandler
			, MsgHandler msgHandler, UnsubscribeHandler unsubscribeHandler
			, SubscribeHandler subscribeHandler, ScanHandler scanHandler
			, RedisTemplate redisTemplate) {
		this.accountService = accountService;
		this.logHandler = logHandler;
		this.nullHandler = nullHandler;
		this.kfSessionHandler = kfSessionHandler;
		this.storeCheckNotifyHandler = storeCheckNotifyHandler;
		this.locationHandler = locationHandler;
		this.menuHandler = menuHandler;
		this.msgHandler = msgHandler;
		this.unsubscribeHandler = unsubscribeHandler;
		this.subscribeHandler = subscribeHandler;
		this.scanHandler = scanHandler;
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	public void initServices() {
		mpServices = accountService.list().stream().map(a -> {
			WxMpInRedisConfigStorage configStorage = new WxMpInRedisConfigStorage(redisTemplate);
			configStorage.setAppId(a.getAppid());
			configStorage.setSecret(a.getAppsecret());
			configStorage.setToken(a.getToken());
			configStorage.setAesKey(a.getAeskey());

			WxMpService service = new WxMpServiceImpl();
			service.setWxMpConfigStorage(configStorage);
			routers.put(a.getAppid(), this.newRouter(service));
			return service;
		}).collect(Collectors.toMap(s -> s.getWxMpConfigStorage().getAppId(), a -> a));
	}

	private WxMpMessageRouter newRouter(WxMpService wxMpService) {
		final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

		// 记录所有事件的日志 （异步执行）
		newRouter.rule().handler(this.logHandler).next();

		// 接收客服会话管理事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
				.handler(this.kfSessionHandler).end();
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
				.handler(this.kfSessionHandler)
				.end();
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
				.handler(this.kfSessionHandler).end();

		// 门店审核事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(WxMpEventConstants.POI_CHECK_NOTIFY)
				.handler(this.storeCheckNotifyHandler).end();

		// 自定义菜单事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(MenuButtonType.CLICK).handler(this.menuHandler).end();

		// 点击菜单连接事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(MenuButtonType.VIEW).handler(this.nullHandler).end();

		// 关注事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(EventType.SUBSCRIBE).handler(this.subscribeHandler)
				.end();

		// 取消关注事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(EventType.UNSUBSCRIBE)
				.handler(this.unsubscribeHandler).end();

		// 上报地理位置事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(EventType.LOCATION).handler(this.locationHandler)
				.end();

		// 接收地理位置消息
		newRouter.rule().async(false).msgType(XmlMsgType.LOCATION)
				.handler(this.locationHandler).end();

		// 扫码事件
		newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
				.event(EventType.SCAN).handler(this.scanHandler).end();

		// 默认
		newRouter.rule().async(false).handler(this.msgHandler).end();

		return newRouter;
	}

}
