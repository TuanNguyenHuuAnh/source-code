SELECT
	   COUNT(DISTINCT m_intro_category.id)
	FROM
	    m_introduction_category m_intro_category LEFT JOIN m_introduction_category_language m_language 
	    										ON (
	    											m_language.m_introduce_category_id = m_intro_category.id 
	    											AND m_language.m_language_code = /*languageCode*/
	    											AND 
	    											m_language.delete_by is NULL
	    										)
	WHERE 
		m_intro_category.delete_by is NULL
	/*BEGIN*/
		AND
		(
			/*IF condition.name != null && condition.name != ''*/
			OR
			replace(m_intro_category.name,' ','') like CONCAT('%',/*condition.name*/,'%')
			/*END*/
			/*IF condition.code != null && condition.code != ''*/
			OR
			replace(m_intro_category.code,' ','') like CONCAT('%',/*condition.code*/,'%')
			/*END*/
			/*IF condition.note != null && condition.note != ''*/
			OR
			replace(m_intro_category.note,' ','') like CONCAT('%',/*condition.note*/,'%')
			/*END*/
			/*IF condition.description != null && condition.description != ''*/
			OR
			replace(m_intro_category.description,' ','') like CONCAT('%',/*condition.description*/,'%')
			/*END*/
			/*IF condition.label != null && condition.label != ''*/
			OR
			replace(m_language.label,' ','') like CONCAT('%',/*condition.label*/,'%')
			/*END*/
		)
	/*END*/




