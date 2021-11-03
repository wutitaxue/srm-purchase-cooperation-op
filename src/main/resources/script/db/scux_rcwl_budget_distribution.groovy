package script.db

databaseChangeLog(logicalFilePath: 'script/db/scux_rcwl_budget_distribution.groovy') {
    changeSet(author: "jie.wang05@hand-china.com", id: "2021-10-27-scux_rcwl_budget_distribution") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'scux_rcwl_budget_distribution_s', startValue: "1")
        }
        createTable(tableName: "scux_rcwl_budget_distribution", remarks: "预算分配") {
            column(name: "budget_line_id", type: "bigint(20)", autoIncrement: true, remarks: "表ID，主键") { constraints(primaryKey: true) }
            column(name: "po_header_id", type: "bigint(20)", remarks: "订单头id,sodr_po_header.po_header_id")
            column(name: "po_line_id", type: "bigint(20)", remarks: "订单行id,sodr_po_line,po_line_id")
            column(name: "pr_header_id", type: "bigint(20)", remarks: "申请头id,sprm_pr_header.pr_header_id")
            column(name: "pr_line_id", type: "bigint(20)", remarks: "申请行id,sprm_pr_line.pr_line_id")
            column(name: "budget_dis_year", type: "int(11)", remarks: "预算占用年份") { constraints(nullable: "false") }
            column(name: "budget_dis_amount", type: "decimal(20,6)", remarks: "预算占用金额（四舍五入）") { constraints(nullable: "false") }
            column(name: "budget_dis_gap", type: "bigint(20)", remarks: "预算总时长（月）") { constraints(nullable: "false") }
            column(name: "tenant_id", type: "bigint(20)", remarks: "租户ID") { constraints(nullable: "false") }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") { constraints(nullable: "false") }
            column(name: "created_by", type: "bigint(20)", defaultValue: "-1", remarks: "") { constraints(nullable: "false") }
            column(name: "last_updated_by", type: "bigint(20)", defaultValue: "-1", remarks: "") { constraints(nullable: "false") }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") { constraints(nullable: "false") }
        }
        createIndex(tableName: "scux_rcwl_budget_distribution", indexName: "scux_rcwl_budget_distribution_n1") {
            column(name: "po_header_id")
            column(name: "po_line_id")
        }

    }
}