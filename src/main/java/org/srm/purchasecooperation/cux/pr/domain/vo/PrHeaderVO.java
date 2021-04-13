package org.srm.purchasecooperation.cux.pr.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import org.srm.common.mybatis.domain.ExpandDomain;

/**
 * @author bin.zhang
 */
public class PrHeaderVO extends ExpandDomain {
    @ApiModelProperty(value = "采购申请头id")
    private Long prHeaderId;
    @ApiModelProperty(value = "采购申请编号")
    private String prNum;
    @ApiModelProperty(value = "行号")
    private String lineNum;
    @ApiModelProperty(value = "采购申请行id")
    private Long prLineId;

    public Long getPrHeaderId() {
        return prHeaderId;
    }

    public void setPrHeaderId(Long prHeaderId) {
        this.prHeaderId = prHeaderId;
    }

    public String getPrNum() {
        return prNum;
    }

    public void setPrNum(String prNum) {
        this.prNum = prNum;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public Long getPrLineId() {
        return prLineId;
    }

    public void setPrLineId(Long prLineId) {
        this.prLineId = prLineId;
    }
}
