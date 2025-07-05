SELECT
    count(*)
FROM
	m_job_type_sub subType
	LEFT JOIN m_job_type_sub_language subLang ON (subLang.m_type_sub_id = subType.id)
WHERE
	subType.delete_by IS NULL
	AND subLang.m_language_code = /*searchCond.languageCode*/
	/*BEGIN*/ AND ( 
	/*IF searchCond.code != null && searchCond.code != ''*/
	OR subType.code LIKE concat('%',  /*searchCond.code*/, '%')
	/*END*/
	/*IF searchCond.title != null && searchCond.title != ''*/
    OR subLang.name LIKE CONCAT('%',/*searchCond.title*/,'%')
    /*END*/
    
	/*IF searchCond.description != null && searchCond.description != ''*/
	OR subType.description LIKE concat('%',  /*searchCond.description*/, '%')
	/*END*/
    ) /*END*/
