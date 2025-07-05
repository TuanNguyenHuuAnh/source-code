SELECT 
	max(m_category.sort)
FROM
    m_introduction_category m_category
    /*IF parentId != null && parentId != ''*/
WHERE 
	m_category.parent_id = /*parentId*/
	AND
	m_category.delete_by IS NULL
	/*END*/
    /*IF parentId == null || parentId == ''*/
WHERE 
	m_category.parent_id IS NULL
	AND
	m_category.delete_by IS NULL
	/*END*/

