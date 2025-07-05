SELECT
      jobType.id 				AS  id
	, jobType.code				AS	code
	, subLang.name		     	AS  name
    , jobType.description		AS	description
  	, jobType.create_date		AS  create_date
FROM
		m_job_type_sub jobType
	LEFT JOIN m_job_type_sub_language subLang ON (subLang.m_type_sub_id = jobType.id)
WHERE
	jobType.delete_by IS NULL
	AND (subLang.m_language_code) = /*searchCond.languageCode*/
	/*BEGIN*/ AND ( 
	/*IF searchCond.code != null && searchCond.code != ''*/
	OR jobType.code LIKE concat('%',  /*searchCond.code*/, '%')
	/*END*/
	/*IF searchCond.title != null && searchCond.title != ''*/
    OR subLang.name LIKE CONCAT('%',/*searchCond.title*/,'%')
    /*END*/
    
	/*IF searchCond.description != null && searchCond.description != ''*/
	OR jobType.description LIKE concat('%',  /*searchCond.description*/, '%')
	/*END*/
    ) /*END*/
    
ORDER BY jobType.create_date DESC
    
LIMIT /*sizeOfPage*/ OFFSET /*offset*/