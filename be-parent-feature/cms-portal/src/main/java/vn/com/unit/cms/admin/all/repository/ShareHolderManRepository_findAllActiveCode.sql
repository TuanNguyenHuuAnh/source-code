SELECT
    *    
FROM
    m_shareholders   
WHERE delete_by is NULL
ORDER BY
   sort ASC, create_date DESC, code ASC