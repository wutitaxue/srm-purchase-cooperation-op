package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.srm.purchasecooperation.cux.pr.domain.vo.RcwlPrLineImportVO;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;
import org.srm.purchasecooperation.pr.domain.vo.PrLineImportVO;
import org.srm.purchasecooperation.pr.infra.mapper.PrImportMapper;

import java.util.List;

public interface RcwlPrImportMapper extends PrImportMapper {
    /**
     * 查询物料信息
     * 查询参数： tenantId       租户ID
     *          itemCode       物料编码
     * @return
     */
    @Override
    List<PrLine> queryItemInfo(PrLineImportVO prLineImportVO);

    /**
     *  查询品类信息
     *  查询参数：  tenantId            租户ID
     *            categoryCode        品类编码
     * @return
     */
    @Override
    PrLine queryCategoryInfo(PrLineImportVO prLineImportVO);

    PrLine queryInvOrganizationInfoByCompanyId(@Param("companyId") Long companyId);
}
