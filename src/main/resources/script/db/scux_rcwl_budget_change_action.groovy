package script.db

databaseChangeLog(logicalFilePath: 'script/db/scux_rcwl_budget_change_action.groovy') {
    changeSet(author: "jie.wang05@hand-china.com", id: "2021-11-03-scux_rcwl_budget_change_action") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'scux_rcwl_budget_change_action_s', startValue:"1")
        }
        createTable(tableName: "scux_rcwl_budget_change_action", remarks: "预算变更记录表") {
            column(name: "budget_change_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户id")  {constraints(nullable:"false")}  
            column(name: "pr_header_id", type: "bigint(20)",  remarks: "申请头id，sprm_pr_header.pr_header_id")  {constraints(nullable:"false")}  
            column(name: "pr_line_id", type: "bigint(20)",  remarks: "申请行id,sprm_pr_line.pr_line_id")  {constraints(nullable:"false")}  
            column(name: "pr_action_id", type: "bigint(20)",  remarks: "变更id，sprm_pr_action.action_id")   
            column(name: "budget_dis_year", type: "int(11)",  remarks: "预算占用年份")  {constraints(nullable:"false")}  
            column(name: "budget_dis_amount", type: "decimal(20,6)",  remarks: "预算占用金额")  {constraints(nullable:"false")}  
            column(name: "budget_dis_gap", type: "bigint(20)",  remarks: "预算总时长（月）")  {constraints(nullable:"false")}  
            column(name: "budget_group", type: "varchar(" + 20 * weight + ")",  remarks: "预算组别 old为旧数据，new为新数据")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint(4)",   defaultValue:"0",   remarks: "变更已提交标识，0为为提交，1为已提交")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}