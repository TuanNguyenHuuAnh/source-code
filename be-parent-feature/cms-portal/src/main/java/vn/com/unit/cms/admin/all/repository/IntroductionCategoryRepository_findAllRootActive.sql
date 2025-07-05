SELECT
    introCate.*,
    introCatelang.LABEL         as    title
FROM m_introduction_category introCate
LEFT JOIN M_INTRODUCTION_CATEGORY_LANGUAGE introCatelang
ON (introCatelang.M_INTRODUCE_CATEGORY_ID = introCate.ID
    AND introCatelang.DELETE_DATE is NULL)
WHERE introCate.delete_by is NULL
	AND introCate.parent_id is NULL
	AND introCate.enabled = 1
	AND introCatelang.M_LANGUAGE_CODE = UPPER(/*lang*/)
ORDER BY
     introCate.sort ASC, introCate.create_date DESC, introCate.code ASC