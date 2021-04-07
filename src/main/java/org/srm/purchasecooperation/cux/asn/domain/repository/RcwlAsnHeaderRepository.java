//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.srm.purchasecooperation.cux.asn.domain.repository;

import org.srm.purchasecooperation.asn.domain.repository.AsnHeaderRepository;
import org.srm.purchasecooperation.cux.asn.domain.vo.RcwlAsnHeaderVO;

public interface RcwlAsnHeaderRepository extends AsnHeaderRepository {

    RcwlAsnHeaderVO selectRcwlAsnHeaderByHeaderId(Long asnHeaderId);

}
