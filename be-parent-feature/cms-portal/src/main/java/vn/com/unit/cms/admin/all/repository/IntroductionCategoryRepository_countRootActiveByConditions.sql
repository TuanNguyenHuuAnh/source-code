SELECT
	   COUNT(DISTINCT m_intro_category.id)
	FROM
	    m_introduction_category m_intro_category
	LEFT JOIN m_introduction_category_language m_language ON (m_language.m_introduce_category_id = m_intro_category.id
	    	AND UPPER(m_language.m_language_code) = UPPER(/*languageCode*/)
	    	AND m_language.delete_by is NULL)
	WHERE m_intro_category.delete_by is NULL
	AND m_intro_category.delete_date is NULL
	AND parent_id is NULL
	/*IF condition.name != null && condition.name != ''*/
	AND m_language.label like concat('%',/*condition.name*/ ,'%')
	/*END*/
	/*IF condition.code != null && condition.code != ''*/
	AND m_intro_category.code like concat('%',/*condition.code*/,'%')
	/*END*/
	/*IF condition.enabled != null*/
	AND m_intro_category.enabled = /*condition.enabled*/
	/*END*/
	/*IF condition.status != null && condition.status != ''*/
	AND m_intro_category.status like concat('%',/*condition.status*/,'%')
	/*END*/
	/*IF condition.typeOfMain != null && condition.typeOfMain == 1*/
	AND m_intro_category.TYPE_OF_MAIN = /*condition.typeOfMain*/
	/*END*/
	/*IF condition.typeOfMain != null && condition.typeOfMain == 0*/
	AND m_intro_category.TYPE_OF_MAIN is NULL
	/*END*/
	/*IF condition.pictureIntroduction != null && condition.pictureIntroduction == 1*/
	AND m_intro_category.PICTURE_INTRODUCTION = /*condition.pictureIntroduction*/
	/*END*/
	/*IF condition.pictureIntroduction != null && condition.pictureIntroduction == 0*/
	AND m_intro_category.PICTURE_INTRODUCTION is NULL
	/*END*/