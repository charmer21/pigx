package com.pig4cloud.common.sequence.sequence;


import com.pig4cloud.common.sequence.range.BizName;
import com.pig4cloud.common.sequence.range.SeqRangeMgr;

/**
 * 序列号区间生成器接口
 * @author xuan on 2018/5/6.
 */
public interface RangeSequence extends Sequence {

	/**
	 * 设置区间管理器
	 *
	 * @param seqRangeMgr 区间管理器
	 */
	void setSeqRangeMgr(SeqRangeMgr seqRangeMgr);

	/**
	 * 设置获取序列号名称
	 *
	 * @param name 名称
	 */
	void setName(BizName name);
}
