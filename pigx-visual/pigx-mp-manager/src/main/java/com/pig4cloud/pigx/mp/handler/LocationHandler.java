package com.pig4cloud.pigx.mp.handler;

import com.pig4cloud.pigx.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Slf4j
@Component
public class LocationHandler extends AbstractHandler {

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
									Map<String, Object> context, WxMpService wxMpService,
									WxSessionManager sessionManager) {
		//上报地理位置事件
		log.info("上报地理位置，纬度 : {}，经度 : {}，精度 : {}",
				wxMessage.getLatitude(), wxMessage.getLongitude(), wxMessage.getPrecision());

		//TODO  可以将用户地理位置信息保存到本地数据库，以便以后使用

		String content = "感谢反馈，您的的地理位置已收到！";
		return new TextBuilder().build(content, wxMessage, null);
	}

}
