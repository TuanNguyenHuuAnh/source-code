SELECT
    *    
FROM
    m_introduction
WHERE delete_by is NULL
AND m_introduction_category_id = /*cateId*/
ORDER BY
    sort ASC, create_date DESC, code ASC
LIMIT /*sizeOfPage*/
OFFSET /*offset*/