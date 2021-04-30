package org.srm.purchasecooperation.cux.sinv.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.srm.purchasecooperation.sinv.domain.entity.SinvRcvTrxHeader;
import org.srm.purchasecooperation.transaction.infra.annotation.FieldIgnore;

/**
 * description
 *
 * @author Penguin 2021/04/30 14:34
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SinvRcvTrxAddAsnStatusHeader extends SinvRcvTrxHeader {

    @ApiModelProperty("送货单头状态 SINV.ASN_HEADERS_STATUS")
    @FieldIgnore
    private String asnStatus;

}
