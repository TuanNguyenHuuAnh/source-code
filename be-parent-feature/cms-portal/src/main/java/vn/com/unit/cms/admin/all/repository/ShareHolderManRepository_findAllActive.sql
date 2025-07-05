SELECT
    *    
FROM
    m_shareholders
WHERE delete_by is NULL
ORDER BY
   create_date DESC, code ASC
LIMIT /*sizeOfPage*/
OFFSET /*offset*/