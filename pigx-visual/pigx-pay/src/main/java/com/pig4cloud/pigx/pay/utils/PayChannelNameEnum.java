package com.pig4cloud.pigx.pay.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lengleng
 * @date 2019-05-30
 * <p>
 * 支付渠道名称
 */
@AllArgsConstructor
public enum PayChannelNameEnum {
	/**
	 * 支付宝wap支付
	 */
	ALIPAY_WAP("ALIPAY_WAP", "支付宝手机支付"),

	/**
	 * 微信H5支付
	 */
	WEIXIN_WAP("WEIXIN_WAP", "微信H5支付"),

	/**
	 * 微信公众号支付
	 */
	WEIXIN_MP("WEIXIN_MP", "微信公众号支付");

	/**
	 * 名称
	 */
	@Getter
	private String name;
	/**
	 * 描述
	 */
	private String description;

	/**
	 * 通过ua 判断所属渠道
	 *
	 * @param ua 浏览器类型
	 * @return
	 */
	public static Enum getChannel(String ua) {
		if (ua.contains(PayConstants.ALIPAY)) {
			return PayChannelNameEnum.ALIPAY_WAP;
		} else if (ua.contains(PayConstants.MICRO_MESSENGER)) {
			return PayChannelNameEnum.WEIXIN_MP;
		} else {
			return PayChannelNameEnum.WEIXIN_WAP;
		}
	}
}
