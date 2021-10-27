package org.srm.purchasecooperation.cux.po.itf.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.srm.purchasecooperation.cux.po.domain.entity.RcwlSodrHzpoHeader;
import org.srm.purchasecooperation.cux.po.itf.api.dto.RcwlSodrHzpoHeaderDTO;
import org.srm.purchasecooperation.cux.po.itf.app.service.RcwlSodrHzpoItfService;

/**
 * @Author: longjunquan 21420
 * @Date: 2021/10/25 16:02
 * @Description:
 */
@RestController("RcwlSodrHzpoItfRcvController.v1")
@RequestMapping("/v1/rest")
public class RcwlSodrHzpoItfRcvController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RcwlSodrHzpoItfRcvController.class);
    @Autowired
    private RcwlSodrHzpoItfService rcwlSodrHzpoItfService;

    @ApiOperation(value = "接收二开订单接口数据")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping("rcwl-sodr-hzpo/receiver-data")
    public RcwlSodrHzpoHeader receivePoRestData(@RequestBody RcwlSodrHzpoHeaderDTO itfData){

          return rcwlSodrHzpoItfService.handleItfData(itfData);


    }
}
