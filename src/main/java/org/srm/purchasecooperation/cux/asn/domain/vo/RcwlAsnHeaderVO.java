//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.srm.purchasecooperation.cux.asn.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.mybatis.domian.SecurityToken;
import org.srm.purchasecooperation.asn.domain.entity.AsnHeader;
import org.srm.purchasecooperation.asn.domain.vo.AsnHeaderVO;
import org.srm.purchasecooperation.cux.asn.infra.constant.Constants;

@JsonInclude(Include.NON_NULL)
@ExcelSheet(
        promptKey = Constants.ExcelSheet.promptKey,
        promptCode = Constants.ExcelSheet.promptCode
)
public class RcwlAsnHeaderVO extends AsnHeaderVO {


    @ApiModelProperty("送货完成相关附件")
    private String deliveredAttaUuid;

    public String getDeliveredAttaUuid() {
        return deliveredAttaUuid;
    }

    public void setDeliveredAttaUuid(String deliveredAttaUuid) {
        this.deliveredAttaUuid = deliveredAttaUuid;
    }

    @Override
    public Class<? extends SecurityToken> associateEntityClass() {
        return AsnHeader.class;
    }
}
