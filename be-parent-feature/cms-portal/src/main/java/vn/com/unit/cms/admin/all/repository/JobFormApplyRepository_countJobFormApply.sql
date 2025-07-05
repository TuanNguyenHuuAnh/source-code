SELECT
    count(*)
FROM
	m_job_form_apply jobFormApply
WHERE
	jobFormApply.delete_by IS NULL
	
	/*BEGIN*/ AND ( 
	/*IF searchCond.name != null && searchCond.name != ''*/
    OR jobFormApply.name LIKE CONCAT('%',/*searchCond.name*/,'%')
    /*END*/
	/*IF searchCond.title != null && searchCond.title != ''*/
	OR jobFormApply.title LIKE CONCAT('%',/*searchCond.title*/, '%')
	/*END*/
	/*IF searchCond.email != null && searchCond.email != ''*/
	OR jobFormApply.email LIKE CONCAT('%',/*searchCond.email*/, '%')
	/*END*/
	/*IF searchCond.telephone != null && searchCond.telephone != ''*/
	OR jobFormApply.telephone LIKE CONCAT('%',/*searchCond.telephone*/, '%')
	/*END*/
    ) /*END*/
