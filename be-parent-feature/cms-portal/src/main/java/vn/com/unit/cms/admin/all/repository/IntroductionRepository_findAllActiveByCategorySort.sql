SELECT
	introLang.TITLE			AS	name,
    intro.*    
FROM
    m_introduction intro
LEFT JOIN m_introduction_language introLang
ON (introLang.delete_date is NULL
	AND introLang.M_INTRODUCTION_ID = intro.id
	AND UPPER(introLang.M_LANGUAGE_CODE) = UPPER(/*language*/)
)
WHERE
	intro.delete_by is NULL
	AND intro.CUSTOMER_TYPE_ID = /*customerId*/
/*IF cateId != null*/
AND intro.m_introduction_category_id = /*cateId*/
/*END*/
ORDER BY
    intro.sort ASC, intro.create_date DESC, intro.code ASC