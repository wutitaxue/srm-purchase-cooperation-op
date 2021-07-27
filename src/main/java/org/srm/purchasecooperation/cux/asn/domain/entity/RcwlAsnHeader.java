//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.srm.purchasecooperation.cux.asn.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hzero.mybatis.domian.SecurityToken;
import org.srm.purchasecooperation.asn.domain.entity.AsnHeader;
import org.srm.web.annotation.Tenant;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@ApiModel("送货单头")
@VersionAudit
@ModifyAudit
@Table(
        name = "sinv_asn_header"
)
@JsonInclude(Include.NON_NULL)
@Tenant("SRM-RCWL")
public class RcwlAsnHeader extends AsnHeader {
    public static final String FIELD_DELIVERED_ATTA_UUID = "deliveredAttaUuid";

    @ApiModelProperty("送货完成相关附件")
    private String deliveredAttaUuid;
    @ApiModelProperty(
            value = "送货单头状态 SINV.ASN_HEADERS_STATUS",
            required = true
    )
    @Length(
            max = 30
    )
    @NotBlank
    private String asnStatus;


    public String getDeliveredAttaUuid() {
        return deliveredAttaUuid;
    }

    public void setDeliveredAttaUuid(String deliveredAttaUuid) {
        this.deliveredAttaUuid = deliveredAttaUuid;
    }

    @Override
    public String getAsnStatus() {
        return asnStatus;
    }

    @Override
    public void setAsnStatus(String asnStatus) {
        this.asnStatus = asnStatus;
    }

    @Override
    public Class<? extends SecurityToken> associateEntityClass() {
        return AsnHeader.class;
    }
    @Override
    public void validAsnShippedStatus() {
        if (!"SHIPPED".equals(this.asnStatus)||!"ARRIVED".equals(this.asnStatus)) {
            throw new CommonException("error.sinv.asn_approve_status_invalid", new Object[0]);
        }
    }
}
