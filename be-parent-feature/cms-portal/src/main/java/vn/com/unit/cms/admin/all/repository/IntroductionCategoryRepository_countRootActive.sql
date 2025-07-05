SELECT
    COUNT(*)
FROM
    m_introduction_category
WHERE
	delete_by is NULL
	AND
	parent_id is NULL