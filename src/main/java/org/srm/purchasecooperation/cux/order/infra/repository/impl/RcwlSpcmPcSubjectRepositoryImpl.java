package org.srm.purchasecooperation.cux.order.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.order.domain.repository.RcwlSpcmPcSubjectRepository;
import org.srm.purchasecooperation.cux.order.infra.mapper.RcwlSpcmPcSubjectMapper;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author Zhouzy 2021/05/22 15:25
 */
@Component
public class RcwlSpcmPcSubjectRepositoryImpl implements RcwlSpcmPcSubjectRepository {

    @Autowired
    RcwlSpcmPcSubjectMapper rcwlSpcmPcSubjectMapper;

    @Override
    public List<Map<String, String>> querySubjectByKey(Long subjectId) {
        return rcwlSpcmPcSubjectMapper.querySubjectByKey(subjectId);
    }

    @Override
    public List<Map<String, String>> queryPrLineByKey(Long prLineId) {
        return rcwlSpcmPcSubjectMapper.queryPrLineByKey(prLineId);
    }
}
