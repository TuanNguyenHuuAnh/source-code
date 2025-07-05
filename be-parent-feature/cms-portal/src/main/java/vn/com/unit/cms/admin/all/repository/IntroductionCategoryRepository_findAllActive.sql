SELECT
    introCate.*,
    m_language.LABEL						AS	title
FROM
    m_introduction_category introCate
LEFT JOIN m_introduction_category_language m_language ON (m_language.m_introduce_category_id = introCate.id
	    AND m_language.m_language_code = UPPER(/*languageCode*/)
	    AND m_language.delete_by is NULL)
WHERE introCate.delete_by is NULL
	AND introCate.ENABLED = 1
	/*IF status != null*/
	AND introCate.status = /*status*/
	/*END*/