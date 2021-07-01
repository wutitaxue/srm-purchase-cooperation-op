package org.srm.purchasecooperation.cux.pr.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RcwlPrFeignMapper {

    List<Long> selectLineIdByHeadId(@Param("prHeaderId") Long prHeaderId);
    void updatePoLine(@Param("prLineId") Long prLineId);
    void updatePoLineVar21(@Param("prLineId") Long prLineId);
}
