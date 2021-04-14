package org.srm.purchasecooperation.cux.gxbpm.app.service;

import org.apache.poi.ss.formula.functions.T;
import org.srm.purchasecooperation.cux.gxbpm.api.dto.RCWLGxBpmRequestDataDTO;
import org.srm.purchasecooperation.cux.gxbpm.api.dto.RCWLGxBpmStartDataDTO;

/**
 * @author lu.cheng01@hand-china.com
 * @description
 * @date 2021/4/12 22:08
 * @version:1.0
 */
public interface RCWLGxBpmInterfaceService {
    /**
     * 获取数据封装数据
     * @return
     */
    public RCWLGxBpmRequestDataDTO RcwlGxBpmInterfaceRequestData(RCWLGxBpmStartDataDTO rcwlGxBpmStartDataDTO);
}
