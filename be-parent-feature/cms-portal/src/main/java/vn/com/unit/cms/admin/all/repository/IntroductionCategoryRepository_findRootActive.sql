SELECT
    introCate.*,
    m_language.LABEL						AS	title
FROM
    m_introduction_category introCate
JOIN m_introduction_category_language m_language ON (m_language.m_introduce_category_id = introCate.id
	    AND m_language.m_language_code = UPPER(/*languageCode*/)
	    AND m_language.delete_by is NULL)
WHERE introCate.delete_by is NULL
	AND introCate.parent_id is NULL
	AND introCate.ENABLED = 1
ORDER BY
     sort ASC, introCate.create_date DESC, introCate.code ASC
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY