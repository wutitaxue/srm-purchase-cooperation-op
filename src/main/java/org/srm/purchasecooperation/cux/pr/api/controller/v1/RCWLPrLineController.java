package org.srm.purchasecooperation.cux.pr.api.controller.v1;

import org.hzero.core.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.pr.domain.repository.RCWLPrLineRepository;

/**
 * 采购申请行 管理 API
 *
 * @author bin.zhang06@hand-china.com 2021-03-16 15:49:15
 */
@RestController("RcwlPrLineController.v1")
@RequestMapping("/v1/{organizationId}/pr-lines")
public class RCWLPrLineController extends BaseController {

    @Autowired
    private RCWLPrLineRepository RCWLPrLineRepository;


}
