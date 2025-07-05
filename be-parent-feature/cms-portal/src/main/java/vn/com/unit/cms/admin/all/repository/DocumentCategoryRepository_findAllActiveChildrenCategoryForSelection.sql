SELECT
    id, parent_id, name   
FROM
     m_document_category
WHERE delete_by is NULL
AND parent_id in /*parentIds*/()
/*IF customerTypeId != null*/
and m_customer_type_id = /*customerTypeId*/
/*END*/
ORDER BY
    create_date DESC, code ASC
