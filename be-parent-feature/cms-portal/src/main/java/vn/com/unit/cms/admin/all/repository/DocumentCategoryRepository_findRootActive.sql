SELECT
    *
FROM
    m_document_category
WHERE delete_by is NULL
	AND
	parent_id is NULL
	/*IF customerTypeId != null*/
	and m_customer_type_id = /*customerTypeId*/
	/*END*/
ORDER BY
     sort_order ASC, create_date DESC, code ASC
OFFSET /*offset*/0 ROWS FETCH NEXT /*sizeOfPage*/10 ROWS ONLY;