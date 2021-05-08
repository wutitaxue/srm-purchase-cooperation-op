package org.srm.purchasecooperation.cux.act.app.service;

import org.srm.purchasecooperation.cux.act.api.dto.ActListHeaderDto;
import org.srm.purchasecooperation.cux.act.api.dto.RcwlBpmUrlDto;
import org.srm.purchasecooperation.sinv.api.dto.SinvRcvTrxHeaderDTO;

import java.io.IOException;

/**
 * @author lu.cheng01@hand-china.com
 * @description 验收单头service
 * @date 2021/4/6 10:08
 * @version:1.0
 */
public interface ActService {
    public ActListHeaderDto actQuery( Long acceptListHeaderId, Long organizationId ) throws IOException;

    /**
     * 提交bpm
     *
     * @param tenantId
     * @param sinvRcvTrxHeaderDTO
     * @return
     * @throws IOException
     */
    public RcwlBpmUrlDto rcwlActSubmitBpm( Long tenantId, SinvRcvTrxHeaderDTO sinvRcvTrxHeaderDTO ) throws IOException;


    /**
     * 提交成功
     *
     * @param tenantId
     * @param settleNum
     * @return
     */
    public SinvRcvTrxHeaderDTO RcwlBpmSubmitSuccess( Long tenantId, String settleNum, String attributeVarchar18, String attributeVarchar19 );

    /**
     * 通过
     *
     * @param tenantId
     * @param settleNum
     * @return
     */
    public Void RcwlBpmApproved( Long tenantId, String settleNum );

    /**
     * 提拒绝
     *
     * @param tenantId
     * @param settleNum
     * @return
     */
    public Void RcwlBpmReject( Long tenantId, String settleNum );

    /**
     * 置0接口
     * @param tenantId
     * @param settleNum
     * @return
     */
    public Void RcwlBpmReject3( Long tenantId, String settleNum );
}
