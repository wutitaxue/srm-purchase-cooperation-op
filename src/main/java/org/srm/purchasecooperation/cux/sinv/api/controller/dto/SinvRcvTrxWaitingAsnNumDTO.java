package org.srm.purchasecooperation.cux.sinv.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description
 *
 * @author Penguin 2021/04/30 16:47
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SinvRcvTrxWaitingAsnNumDTO {

    private String asnNum;
    private Long tenantId;

}
