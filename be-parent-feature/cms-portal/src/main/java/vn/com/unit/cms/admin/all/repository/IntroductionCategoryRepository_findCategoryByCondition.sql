SELECT
	   m_intro_category.*,
	   m_language.LABEL						AS	title
	FROM
	    m_introduction_category m_intro_category
	LEFT JOIN m_introduction_category_language m_language ON (m_language.m_introduce_category_id = m_intro_category.id
	    AND m_language.m_language_code = UPPER(/*languageCode*/)
	    AND m_language.delete_by is NULL)
	WHERE m_intro_category.delete_by is NULL
	AND m_intro_category.delete_date is NULL
	/*IF condition.name != null && condition.name != ''*/
	AND UPPER(m_language.label) like UPPER(TRIM('%'|| /*condition.name*/ ||'%'))
	/*END*/
	/*IF condition.code != null && condition.code != ''*/
	AND UPPER(m_intro_category.code) like UPPER(TRIM('%'|| /*condition.code*/ ||'%'))
	/*END*/
	/*IF condition.note != null && condition.note != ''*/
	AND UPPER(m_intro_category.note) like UPPER(TRIM('%'|| /*condition.note*/ ||'%'))
	/*END*/
	ORDER BY
		m_intro_category.sort ASC,
		m_intro_category.create_date DESC,
		m_intro_category.code ASC