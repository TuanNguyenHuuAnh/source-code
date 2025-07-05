SELECT
    *    
FROM
    m_document_category
WHERE delete_by is NULL
ORDER BY
     sort_order ASC,create_date DESC, code ASC