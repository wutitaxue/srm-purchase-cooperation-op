package org.srm.purchasecooperation.cux.asn.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.asn.api.dto.RcwlAsnAcceptOrRcvDTO;
import org.srm.purchasecooperation.cux.asn.domain.service.RcwlAsnInterfaceService;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.RcwlAsnInterfaceMapper;

import java.util.List;


@Component
public class RcwlAsnInterfaceServiceImpl implements RcwlAsnInterfaceService {

    @Autowired
    private RcwlAsnInterfaceMapper rcwlAsnInterfaceMapper;

    /**
     * 根据业务逻辑判断返回不同的信息
     * */
    @Override
    public RcwlAsnAcceptOrRcvDTO returnAcceptOrRcvBack(List<RcwlAsnAcceptOrRcvDTO> list) {
        RcwlAsnAcceptOrRcvDTO returnDto = new RcwlAsnAcceptOrRcvDTO();
        Long tenantId = rcwlAsnInterfaceMapper.selectTenantIdByName("SRM-RCWL");
        for (RcwlAsnAcceptOrRcvDTO item : list) {
            item.setTenantId(tenantId);
            //业务类型 1回传数据 2单据反审核
            if (item.getBusinessType().equals("1")) {
                //为1通过接收验收单号+行号将对应回传单号 净入库数量
                Long aLong = rcwlAsnInterfaceMapper.checkSinvLineReturn(item);
                if (aLong == 0){
                    returnDto.setErrorFlag(1);
                    returnDto.setErrorMessage("单据:" + item.getAcceptanceNumber()
                            + "行号:" + item.getLineNumber() + "不存在，请检查后重传!");
                    return returnDto;
                }
                rcwlAsnInterfaceMapper.updateSinvLineReturn(item);
                returnDto.setErrorFlag(0);
                returnDto.setErrorMessage("单据:" + item.getAcceptanceNumber()
                        + "行号:" + item.getLineNumber() + "回传成功!");
                return returnDto;
            } else if (item.getBusinessType().equals("2")) {
//                为2会回传多个list 先判断所有list是否产生了对账单 如果产生了返回消息 单据编号XXXX 行号XX采购平台已结算，不允许反审核
//                如果都未产生对账单 将这些单据的加上的资产单据号”字段attribute_varchar1和 “入库数量”字段attribute_bigint1，字段清空，返回消息 已清空入库数量及单据编号允许反审核
                Long exists = rcwlAsnInterfaceMapper.selectSinvStatusCount(item);
                if (exists > 0) {
                    returnDto.setErrorFlag(1);
                    returnDto.setErrorMessage("单据:" + item.getAcceptanceNumber()
                            + "行号:" + item.getLineNumber() + "采购平台已结算，不允许反审核!");
                    return returnDto;
                }
            } else {
                returnDto.setErrorFlag(1);
                returnDto.setErrorMessage("单据:" + item.getAcceptanceNumber() + "行号:" + item.getLineNumber() + "业务类型businessType无效!");
                return returnDto;
            }
        }
        for (RcwlAsnAcceptOrRcvDTO item : list) {
            item.setTenantId(tenantId);
            if (item.getBusinessType().equals("2")) {
                rcwlAsnInterfaceMapper.deleteSinvLineReturn(item);
                returnDto.setErrorFlag(0);
                returnDto.setErrorMessage("已清空入库数量及单据编号允许反审核!");
            }
        }
        return returnDto;
    }
}
