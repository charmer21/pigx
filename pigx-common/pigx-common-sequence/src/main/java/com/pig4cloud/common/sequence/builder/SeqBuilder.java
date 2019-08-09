package com.pig4cloud.common.sequence.builder;


import com.pig4cloud.common.sequence.sequence.Sequence;

/**
 * 序列号生成器构建者
 * @author xuan on 2018/5/30.
 */
public interface SeqBuilder {

	/**
	 * 构建一个序列号生成器
	 *
	 * @return 序列号生成器
	 */
	Sequence build();

}
