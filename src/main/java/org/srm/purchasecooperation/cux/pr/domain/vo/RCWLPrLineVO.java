package org.srm.purchasecooperation.cux.pr.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.srm.purchasecooperation.pr.domain.vo.PrLineVO;

/**
 * @author jie.wang05@hand-china.com 2021/10/29 11:06
 * @description 采购订单申请行拓展
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RCWLPrLineVO extends PrLineVO {
    @ApiModelProperty(value = "采购申请行物料品类-规格型号可编辑")
    private String  categoryAttributeVarchar15;
}
