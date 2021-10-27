package org.srm.purchasecooperation.cux.po.itf.app.service;

import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoHeader;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlSodrHzpoHeaderDTO;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/10/25 16:15
 * @Description:
 */
public interface RcwlSodrHzpoItfService {

    RcwlSodrHzpoHeader handleItfData(RcwlSodrHzpoHeaderDTO itfData);
}
