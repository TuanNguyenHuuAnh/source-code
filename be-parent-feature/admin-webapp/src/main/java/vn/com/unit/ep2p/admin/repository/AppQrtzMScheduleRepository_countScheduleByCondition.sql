SELECT count(*)
FROM qrtz_m_schedule
where DELETED_ID = 0
AND validflag = 1 
/*IF schedSearch.companyId != null && schedSearch.companyId != 0*/
AND company_id = /*schedSearch.companyId*/1
/*END*/
/*IF schedSearch.companyId == null*/
AND company_id IS NULL
/*END*/
/*IF !schedSearch.companyAdmin && schedSearch.companyId == 0 && schedSearch.companyIdList != null*/
AND (company_id  IN /*schedSearch.companyIdList*/()
OR company_id IS NULL)
/*END*/
/*BEGIN*/
and(
		/*IF schedSearch.schedCode != null && schedSearch.schedCode != ''*/
		or sched_code like CONCAT(CONCAT('%', UPPER(/*schedSearch.schedCode*/)), '%')
		/*END*/
		/*IF schedSearch.schedName != null && schedSearch.schedName != ''*/
		or sched_name like CONCAT(CONCAT('%', UPPER(/*schedSearch.schedName*/)), '%')
		/*END*/
		/*IF schedSearch.cronExpression != null && schedSearch.cronExpression != ''*/
		or cron_expression like CONCAT(CONCAT('%', UPPER(/*schedSearch.cronExpression*/)), '%')
		/*END*/
		/*IF schedSearch.description != null && schedSearch.description != ''*/
		or description like CONCAT(CONCAT('%', UPPER(/*schedSearch.description*/)), '%')
		/*END*/
)
/*END*/