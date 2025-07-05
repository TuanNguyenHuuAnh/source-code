SELECT 
	max(m_introduction.sort)
FROM
    m_introduction m_introduction
    /*IF categoryId!= null && categoryId != ''*/
WHERE 
	m_introduction.m_introduction_category_id = /*categoryId*/
	AND
	m_introduction.delete_by IS NULL
	/*END*/
    /*IF categoryId == null || categoryId == ''*/
WHERE 
	m_introduction.m_introduction_category_id IS NULL
	AND
	m_introduction.delete_by IS NULL
	/*END*/

