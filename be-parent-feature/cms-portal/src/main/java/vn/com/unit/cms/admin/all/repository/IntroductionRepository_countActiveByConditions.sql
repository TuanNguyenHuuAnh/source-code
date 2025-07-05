SELECT
	   COUNT(DISTINCT m_intro.id)
	FROM
	    (m_introduction m_intro LEFT JOIN m_introduction_category m_category 
	    						ON (m_intro.m_introduction_category_id = m_category.id 
	    							AND
	    							m_category.delete_by is NULL))
	    						LEFT JOIN m_introduction_language m_language 
	    						ON (m_language.m_introduction_id = m_intro.id 
	    							AND
	    							UPPER(m_language.m_language_code) = UPPER(/*languageCode*/)
	    							AND 
	    							m_language.delete_by is NULL)
	WHERE 
	m_intro.delete_by is NULL 
	/*IF condition.name != null && condition.name != ''*/
	AND m_language.TITLE LIKE concat('%',/*condition.name*/,'%')
	/*END*/
	/*IF condition.code != null && condition.code != ''*/
	AND m_intro.code like concat('%',/*condition.code*/,'%')
	/*END*/
	/*IF condition.status != null*/
	AND m_intro.status = /*condition.status*/
	/*END*/
	/*IF condition.enabled != null*/
	AND m_intro.enabled = /*condition.enabled*/
	/*END*/
	/*IF condition.categoryId != null*/
	AND m_intro.M_INTRODUCTION_CATEGORY_ID = /*condition.categoryId*/
	/*END*/