package org.srm.purchasecooperation.cux.pr.app.service;

import org.srm.purchasecooperation.cux.pr.domain.entity.PlanHeader;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: bin.zhang
 * @createDate: 2021/4/27 16:46
 */
public interface RCWLPlanHeaderDataToBpmService {
    /**
     * 传输数据给bpm
     * @param list
     * @param organizationId
     */
    void sendDataToBpm(List<PlanHeader> list, Long organizationId) throws IOException;
}
