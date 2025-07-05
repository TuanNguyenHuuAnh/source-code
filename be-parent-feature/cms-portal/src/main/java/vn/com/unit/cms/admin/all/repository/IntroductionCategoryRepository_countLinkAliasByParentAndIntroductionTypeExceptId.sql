SELECT
    COUNT(micl.id)    
FROM m_introduction_category mic
join m_introduction_category_language micl on micl.M_INTRODUCE_CATEGORY_ID = mic.id
WHERE
	micl.delete_by is NULL
	and mic.delete_by is NULL
    and mic.enabled = 1
    and micl.M_LANGUAGE_CODE = /*languageCode*/
	/*IF parentId != null && parentId != ''*/
	AND mic.parent_id = /*parentId*/
	/*END*/
	/*IF linkAlias != null && linkAlias != ''*/
	AND micl.link_alias = /*linkAlias*/
	/*END*/
	/*IF exceptedId != null && exceptedId != ''*/
	AND micl.id != /*exceptedId*/
	/*END*/
		