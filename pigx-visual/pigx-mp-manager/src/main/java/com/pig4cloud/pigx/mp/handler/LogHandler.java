package com.pig4cloud.pigx.mp.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pigx.mp.entity.WxAccount;
import com.pig4cloud.pigx.mp.entity.WxFansMsg;
import com.pig4cloud.pigx.mp.service.WxAccountService;
import com.pig4cloud.pigx.mp.service.WxFansMsgService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lengleng
 * <p>
 * 保存微信消息
 */
@Slf4j
@Component
@AllArgsConstructor
public class LogHandler extends AbstractHandler {
	private final WxFansMsgService wxFansMsgService;
	private final WxAccountService wxAccountService;

	@Override
	@SneakyThrows
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
									Map<String, Object> context, WxMpService wxMpService,
									WxSessionManager sessionManager) {
		log.debug("接收到请求消息，内容：{}", wxMessage.getContent());

		if (!WxConsts.XmlMsgType.TEXT.equals(wxMessage.getMsgType())) {
			log.debug("消息类型其他类型不保存", wxMessage.getContent());
			return null;
		}

		WxAccount wxAccount = wxAccountService.getOne(Wrappers.<WxAccount>lambdaQuery()
				.eq(WxAccount::getAccount, wxMessage.getToUser()));


		WxMpUser wxmpUser = wxMpService.getUserService()
				.userInfo(wxMessage.getFromUser());

		WxFansMsg wxFansMsg = new WxFansMsg();
		wxFansMsg.setOpenid(wxmpUser.getOpenId());
		wxFansMsg.setNickname(wxmpUser.getNickname());
		wxFansMsg.setHeadimgUrl(wxmpUser.getHeadImgUrl());
		wxFansMsg.setWxAccountId(wxAccount.getId());
		wxFansMsg.setWxAccountName(wxAccount.getName());
		wxFansMsg.setWxAccountAppid(wxAccount.getAppid());
		wxFansMsg.setMsgType(wxMessage.getMsgType());
		wxFansMsg.setMsgId(wxMessage.getMsgId());
		wxFansMsg.setContent(wxMessage.getContent());
		wxFansMsg.setTenantId(wxAccount.getTenantId());
		wxFansMsgService.save(wxFansMsg);

		log.debug("保存微信用户信息成功 {}", wxFansMsg);
		return null;
	}
}
