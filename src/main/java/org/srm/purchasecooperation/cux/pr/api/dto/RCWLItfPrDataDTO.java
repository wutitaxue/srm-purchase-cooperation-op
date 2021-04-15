package org.srm.purchasecooperation.cux.pr.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @description:预算占用/释放 同步数据列表
 * @author: bin.zhang
 * @createDate: 2021/4/10 10:34
 */
public class RCWLItfPrDataDTO {
    @JSONField(ordinal = 1)
    private RCWLItfPrLineDTO yszy;
    @JSONField(ordinal = 2)
    private List<RCWLItfPrLineDetailDTO> yszyzb;

    public RCWLItfPrLineDTO getYszy() {
        return yszy;
    }

    public void setYszy(RCWLItfPrLineDTO yszy) {
        this.yszy = yszy;
    }

    public List<RCWLItfPrLineDetailDTO> getYszyzb() {
        return yszyzb;
    }

    public void setYszyzb(List<RCWLItfPrLineDetailDTO> yszyzb) {
        this.yszyzb = yszyzb;
    }

    @Override
    public String toString() {
        return "RCWLItfPrDataDTO{" +
                "yszy=" + yszy +
                ", yszyzb=" + yszyzb +
                '}';
    }
}
