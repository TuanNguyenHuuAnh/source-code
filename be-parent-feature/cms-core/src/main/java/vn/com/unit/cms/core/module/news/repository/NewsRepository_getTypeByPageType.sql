SELECT
	TYPE_.ID AS ID,
	TYPE_.CODE AS CODE,
	lang.LABEL AS LABEL,
	lang.DESCRIPTION as DESCRIPTION,
	lang.LINK_ALIAS as LINK_ALIAS
FROM M_NEWS_TYPE TYPE_
LEFT JOIN M_NEWS_TYPE_LANGUAGE lang ON (lang.M_NEWS_TYPE_ID = TYPE_.id AND lang.delete_date is null)
WHERE
	TYPE_.delete_date is null
	AND UPPER(lang.m_language_code) = UPPER(/*language*/'vi')
	AND TYPE_.CODE = /*pageType*/''
	/*IF modeView == 0*/
	AND TYPE_.ENABLED = 1
	/*END*/