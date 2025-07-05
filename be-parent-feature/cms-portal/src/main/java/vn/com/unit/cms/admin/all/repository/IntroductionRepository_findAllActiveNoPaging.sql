SELECT
	introLang.TITLE				AS	name,
    intro.*
FROM
    m_introduction intro
LEFT JOIN m_introduction_language introLang
	ON(introLang.delete_date is NULL
		AND introLang.M_INTRODUCTION_ID = intro.ID
		AND UPPER(introLang.M_LANGUAGE_CODE) = UPPER(/*language*/'vi')
	)
WHERE intro.delete_by is NULL
ORDER BY
    intro.m_introduction_category_id ASC, intro.create_date DESC, intro.code ASC