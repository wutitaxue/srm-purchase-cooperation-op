package org.srm.purchasecooperation.cux.order.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author Zhouzy 2021/05/22 15:27
 */
public interface RcwlSpcmPcSubjectMapper {
    List<Map<String,String>> querySubjectByKey(@Param("subjectId") Long subjectId);
    List<Map<String,String>> queryPrLineByKey(@Param("prLineId") Long prLineId);
}
