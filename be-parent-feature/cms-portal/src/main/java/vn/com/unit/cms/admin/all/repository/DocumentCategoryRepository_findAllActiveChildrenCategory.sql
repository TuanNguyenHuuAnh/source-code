SELECT
    *    
FROM
     m_document_category
WHERE delete_by IS NULL
	AND parent_id IN /*parentIds*/()
	/*IF customerTypeId != null*/
	and m_customer_type_id = /*customerTypeId*/
	/*END*/
ORDER BY
    sort_order ASC, create_date ASC, code ASC
