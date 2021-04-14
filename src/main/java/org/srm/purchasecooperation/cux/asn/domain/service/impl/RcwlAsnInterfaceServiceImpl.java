package org.srm.purchasecooperation.cux.asn.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.asn.api.dto.RcwlAsnAcceptOrRcvDTO;
import org.srm.purchasecooperation.cux.asn.domain.service.RcwlAsnInterfaceService;
import org.srm.purchasecooperation.cux.sinv.infra.mapper.RcwlAsnInterfaceMapper;

import java.util.ArrayList;
import java.util.List;


@Component
public class RcwlAsnInterfaceServiceImpl implements RcwlAsnInterfaceService {

    @Autowired
    private RcwlAsnInterfaceMapper rcwlAsnInterfaceMapper;

    /**
    * 构建返回报文实体
    * */
    public void addList(List<RcwlAsnAcceptOrRcvDTO> returnlist, Integer errorFlag, String errorMsg, String returnFlag){
        RcwlAsnAcceptOrRcvDTO rcwlAsnAcceptOrRcvDTO = new RcwlAsnAcceptOrRcvDTO(null,errorFlag,errorMsg,null,returnFlag);
        returnlist.add(rcwlAsnAcceptOrRcvDTO);
    }

    /**
     * 根据业务逻辑判断返回不同的信息
     * */
    @Override
    public List<RcwlAsnAcceptOrRcvDTO> returnAcceptOrRcvBack(List<RcwlAsnAcceptOrRcvDTO> list) {
        List<RcwlAsnAcceptOrRcvDTO> returnlist = new ArrayList<RcwlAsnAcceptOrRcvDTO>();
        for (RcwlAsnAcceptOrRcvDTO item : list) {
            //业务类型 1回传数据 2单据反审核
            if (item.getBusinessType().equals("1")) {
                //采购平台单据类型 01接收 02验收
                if (item.getPlfmDocumentType().equals("01")) {
                    rcwlAsnInterfaceMapper.updateSinvLineReturn(item);
                    addList(returnlist,0,"单据:" + item.getAcceptanceNumber() + "回传成功",null);
                } else if (item.getPlfmDocumentType().equals("02")) {
                    rcwlAsnInterfaceMapper.updateSpucLineReturn(item);
                    addList(returnlist,0,"单据:" + item.getAcceptanceNumber() + "回传成功",null);
                } else {
                    addList(returnlist,1,"单据:" + item.getAcceptanceNumber() + "采购平台单据类型plfmDocumentType无效！",null);
                }
            } else if (item.getBusinessType().equals("2")) {
                //查询是否生成对账单表sinv_rcv_trx_line字段invoice_matched_status值是否为UNINVOICED是给Y 否给N
                if(item.getPlfmDocumentType().equals("01")){
                    Long aLong1 = rcwlAsnInterfaceMapper.selectSinvStatusCount(item);
                    if (aLong1 == 0) { //N
                        addList(returnlist,0,"单据:" + item.getAcceptanceNumber() + "已生成对账单","N");
                    }else { //Y
                        rcwlAsnInterfaceMapper.deleteSinvLineReturn(item);
                        addList(returnlist,0,"单据:" + item.getAcceptanceNumber() + "未生成对账单，已清空接收单回写值","Y");
                    }
                //查询是否生成对账单表sfin_bill_detail_accept根据验收单行ID查询这表是否有值 有给N 没有Y
                }else if(item.getPlfmDocumentType().equals("02")){
                    Long aLong2 = rcwlAsnInterfaceMapper.selectSpucCount(item);
                    if (aLong2 == 0) { //Y
                        rcwlAsnInterfaceMapper.deleteSpucLineReturn(item);
                        addList(returnlist,0,"单据:" + item.getAcceptanceNumber() + "未生成验收单，已清空验收单回写值","Y");
                    }else { //N
                        addList(returnlist,0,"单据:" + item.getAcceptanceNumber() + "已生成验收单","N");
                    }
                }else {
                    addList(returnlist,1,"单据:" + item.getAcceptanceNumber() + "采购平台单据类型plfmDocumentType无效！",null);
                }
            } else {
                addList(returnlist,1,"单据:" + item.getAcceptanceNumber() + "业务类型businessType无效！",null);
            }
        }
        return returnlist;
    }
}
