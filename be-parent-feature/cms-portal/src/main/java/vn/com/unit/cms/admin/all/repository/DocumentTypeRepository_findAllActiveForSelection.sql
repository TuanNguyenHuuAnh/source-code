SELECT m_type.id
	  ,tbl_lang.title
FROM m_document_type m_type
join m_document_type_language tbl_lang on m_type.id = tbl_lang.m_document_type_id and tbl_lang.m_language_code = upper(/*languageCode*/'vi')
WHERE
	m_type.delete_date is null
	and m_type.enabled = 1
	/*IF customerTypeId != null*/
	and m_type.m_customer_type_id = /*customerTypeId*/
	/*END*/
	/*IF status != null*/
        AND m_type.status = /*status*/
    /*END*/
ORDER BY  m_type.sort_order ASC, m_type.create_date DESC, tbl_lang.title ASC
