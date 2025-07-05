SELECT schedule.* 
		, company.name	AS COMPANY_NAME 
FROM qrtz_m_schedule schedule 
left join jca_company company ON  schedule.COMPANY_ID = company.ID 
where schedule.DELETED_ID = 0
AND schedule.validflag = 1 
/*IF schedSearch.companyId != null && schedSearch.companyId != 0*/
AND schedule.company_id = /*schedSearch.companyId*/1
/*END*/
/*IF schedSearch.companyId == null*/
AND schedule.company_id IS NULL
/*END*/
/*IF !schedSearch.companyAdmin && schedSearch.companyId == 0*/
AND (schedule.company_id  IN /*schedSearch.companyIdList*/()
OR schedule.company_id IS NULL)
/*END*/
/*BEGIN*/
and(
		/*IF schedSearch.schedCode != null && schedSearch.schedCode != ''*/
		or UPPER(schedule.sched_code) LIKE CONCAT(CONCAT('%',UPPER(/*schedSearch.schedCode*/'')),'%') 
		/*END*/ 		
		/*IF schedSearch.schedName != null && schedSearch.schedName != ''*/
		or UPPER(schedule.sched_name) LIKE CONCAT(CONCAT('%',UPPER(/*schedSearch.schedName*/'')),'%') 
		/*END*/	
		/*IF schedSearch.cronExpression != null && schedSearch.cronExpression != ''*/
		or UPPER(schedule.cron_expression) like CONCAT(CONCAT('%',UPPER(/*schedSearch.cronExpression*/'')),'%') 
		/*END*/ 		
		/*IF schedSearch.description != null && schedSearch.description != ''*/
		or UPPER(schedule.description) like CONCAT(CONCAT('%',UPPER(/*schedSearch.description*/'')),'%') 
		/*END*/
)
/*END*/ 
/*IF orders != null*/
ORDER BY /*$orders*/schedule.ID
-- ELSE ORDER BY schedule.ID DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/