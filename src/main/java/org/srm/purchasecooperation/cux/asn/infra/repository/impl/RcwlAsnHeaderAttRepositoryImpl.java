package org.srm.purchasecooperation.cux.asn.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.asn.domain.entity.AsnAction;
import org.srm.purchasecooperation.asn.infra.repository.impl.AsnHeaderRepositoryImpl;
import org.srm.purchasecooperation.cux.asn.domain.entity.RcwlAsnHeader;
import org.srm.purchasecooperation.cux.asn.domain.repository.RcwlAsnHeaderAttRepository;
import org.srm.purchasecooperation.cux.asn.domain.repository.RcwlAsnHeaderRepository;
import org.srm.purchasecooperation.cux.asn.domain.vo.RcwlAsnHeaderVO;
import org.srm.purchasecooperation.cux.asn.infra.mapper.RcwlAsnHeaderMapper;
import org.srm.web.annotation.Tenant;

@Component
@Tenant("SRM-RCWL")
public class RcwlAsnHeaderAttRepositoryImpl extends BaseRepositoryImpl<RcwlAsnHeader> implements RcwlAsnHeaderAttRepository {

}
