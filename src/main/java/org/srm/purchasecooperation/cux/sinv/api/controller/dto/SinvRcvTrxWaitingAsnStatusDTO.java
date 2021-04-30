package org.srm.purchasecooperation.cux.sinv.api.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hzero.export.annotation.ExcelSheet;
import org.srm.purchasecooperation.cux.sinv.infra.util.TenantValue;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxWaitingDTO;
import org.srm.web.annotation.Tenant;

/**
 * description
 *
 * @author Penguin 2021/04/30 14:44
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tenant(TenantValue.tenantV)
public class SinvRcvTrxWaitingAsnStatusDTO  {

    @ApiModelProperty("送货单头状态 SINV.ASN_HEADERS_STATUS")
    private String asnStatus;

}
