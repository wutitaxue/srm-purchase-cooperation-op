package org.srm.purchasecooperation.cux.pr.app.service;

import org.srm.purchasecooperation.cux.pr.domain.entity.PlanHeader;
import org.srm.purchasecooperation.cux.pr.domain.vo.PlanHeaderVO;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/27 16:46
 */
public interface RCWLPlanHeaderDataToBpmService {
    /**
     * 触发bpm接口
     * @param list
     * @param organizationId
     * @throws IOException
     */
    void sendDataToBpm(List<PlanHeaderVO> list, Long organizationId,String processNum) throws IOException;
}
