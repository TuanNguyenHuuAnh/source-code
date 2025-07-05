SELECT
      jobType.id 				AS  id
	, jobType.code				AS	code
	, jobTypeLang.name			AS  name
    , jobType.description		AS	description
  	, jobType.create_date		AS  create_date
  	
FROM
		m_job_type jobType
	LEFT JOIN m_job_type_language jobTypeLang ON (jobTypeLang.m_type_id = jobType.id)
WHERE
	jobType.delete_by IS NULL
	AND jobTypeLang.m_language_code = UPPER(/*searchCond.languageCode*/)
	/*BEGIN*/ AND ( 
	/*IF searchCond.code != null && searchCond.code != ''*/
	OR jobType.code LIKE concat('%',  /*searchCond.code*/, '%')
	/*END*/
	/*IF searchCond.name != null && searchCond.name != ''*/
    OR jobTypeLang.name LIKE CONCAT('%',/*searchCond.name*/,'%')
    /*END*/
    
	/*IF searchCond.description != null && searchCond.description != ''*/
	OR jobType.description LIKE concat('%',  /*searchCond.description*/, '%')
	/*END*/
    ) /*END*/
    
ORDER BY jobType.create_date DESC
    
LIMIT /*sizeOfPage*/ OFFSET /*offset*/