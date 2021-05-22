package org.srm.purchasecooperation.cux.order.domain.repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author Zhouzy 2021/05/22 15:21
 */
public interface RcwlSpcmPcSubjectRepository {

    /**
     *
     *根据ID 获取SpcmPcSubject表的 三个字段
     * budget_account_id --> attribute_varchar21
     *      * 	 cost_id --> attribute_varchar22
     *      * 	 WBS     --> attribute_varchar23
     * @param subjectId
     * @author Zhouzy 2021-05-22 15:22
     * @return
     */
    List<Map<String,String>> querySubjectByKey(Long subjectId);

    /**
     *
     *根据ID 获取sprm_pr_line表的 三个字段
     * budget_account_id --> attribute_varchar21
     *      * 	 cost_id --> attribute_varchar22
     *      * 	 WBS     --> attribute_varchar23
     * @param prLineId
     * @author Zhouzy 2021-05-22 15:22
     * @return
     */
    List<Map<String,String>> queryPrLineByKey(Long prLineId);
}
