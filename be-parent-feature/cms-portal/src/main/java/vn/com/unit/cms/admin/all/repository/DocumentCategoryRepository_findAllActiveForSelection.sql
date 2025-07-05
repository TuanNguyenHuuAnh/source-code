SELECT
    id, parent_id, name   
FROM
     m_document_category
WHERE delete_by is NULL
/*IF customerTypeId != null*/
and m_customer_type_id = /*customerTypeId*/
/*END*/
ORDER BY
    name ASC, code ASC
