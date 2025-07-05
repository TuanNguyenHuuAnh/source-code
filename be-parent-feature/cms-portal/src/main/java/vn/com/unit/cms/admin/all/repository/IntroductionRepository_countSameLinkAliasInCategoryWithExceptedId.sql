SELECT COUNT(micl.id)    
FROM m_introduction mic
join m_introduction_language micl on micl.M_INTRODUCTION_ID = mic.id
WHERE
	mic.delete_by is NULL
--    and mic.enabled = 1 
    and micl.m_language_code = /*languageCode*/
	/*IF categoryId != null && categoryId != ''*/
	AND mic.m_introduction_category_id = /*categoryId*/
	/*END*/
	/*IF linkAlias != null && linkAlias != ''*/
	AND micl.link_alias = /*linkAlias*/
	/*END*/
	/*IF exceptedId != null && exceptedId != ''*/
	AND micl.id != /*exceptedId*/
	/*END*/