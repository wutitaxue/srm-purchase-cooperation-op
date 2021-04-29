package org.srm.purchasecooperation.cux.asn.domain.service;

import org.srm.purchasecooperation.cux.asn.api.dto.RcwlAsnAcceptOrRcvDTO;

import java.util.List;


public interface RcwlAsnInterfaceService {

    List<RcwlAsnAcceptOrRcvDTO> returnAcceptOrRcvBack (List<RcwlAsnAcceptOrRcvDTO> list);

}
