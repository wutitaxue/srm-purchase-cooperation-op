//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.srm.purchasecooperation.cux.asn.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.mybatis.domian.SecurityToken;
import org.srm.purchasecooperation.asn.domain.entity.AsnHeader;

import javax.persistence.Table;

@ApiModel("送货单头")
@VersionAudit
@ModifyAudit
@Table(
        name = "sinv_asn_header"
)
@JsonInclude(Include.NON_NULL)
public class RcwlAsnHeader extends AsnHeader {
    public static final String FIELD_DELIVERED_ATTA_UUID = "deliveredAttaUuid";

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
