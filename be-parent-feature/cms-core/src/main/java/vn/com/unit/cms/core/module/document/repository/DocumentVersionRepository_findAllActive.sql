SELECT
    *    
FROM
    m_document_detail
WHERE delete_by is NULL
ORDER BY
     create_date ASC
LIMIT /*sizeOfPage*/
OFFSET /*offset*/