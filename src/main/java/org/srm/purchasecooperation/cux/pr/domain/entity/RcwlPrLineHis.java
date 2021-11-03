package org.srm.purchasecooperation.cux.pr.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.srm.purchasecooperation.pr.domain.entity.PrLine;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购申请行(RcwlPrLineHis)实体类
 *
 * @author jie.wang05@hand-china.com
 * @since 2021-11-02 14:04:38
 */
@ApiModel("采购申请行")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "scux_rcwl_pr_line_his")
public class RcwlPrLineHis extends PrLine {
    private static final long serialVersionUID = 700181099556282411L;

    public static final String FIELD_VERSION = "version";

    @ApiModelProperty(value = "版本")
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
