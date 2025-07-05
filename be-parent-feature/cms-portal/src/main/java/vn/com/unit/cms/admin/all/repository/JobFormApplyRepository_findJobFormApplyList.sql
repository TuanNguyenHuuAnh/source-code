SELECT
      jobFormApply.id 				AS  id
	, jobFormApply.name			AS  name
	, jobFormApply.telephone	AS telephone
	, jobFormApply.email	AS email
    , jobFormApply.title		AS	title
    , jobFormApply.status	AS status
    ,jobFormApply.content	AS content
    , jobFormApply.position AS position
  	, jobFormApply.create_date		AS  create_date
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
    
ORDER BY jobFormApply.create_date DESC
    
LIMIT /*sizeOfPage*/ OFFSET /*offset*/