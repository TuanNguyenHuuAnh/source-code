SELECT
	  count(DISTINCT docType.id)
FROM m_document_type docType
LEFT JOIN m_document_type_language typeLang ON docType.id = typeLang.m_document_type_id AND UPPER(typeLang.m_language_code) = UPPER(/*languageCode*/)
WHERE
	docType.delete_date is null
	/*IF searchCond.customerTypeId != null*/
	AND docType.m_customer_type_id = /*searchCond.customerTypeId*/
	/*END*/
	/*IF searchCond.code != null && searchCond.code != ''*/
	AND replace(docType.code,' ','') LIKE concat('%',replace(/*searchCond.code*/,' ',''),'%')
	/*END*/
	/*IF searchCond.name != null && searchCond.name != ''*/
	AND replace(typeLang.title,' ','') LIKE concat('%',replace( /*searchCond.name*/,' ',''),'%')
	/*END*/
	/*IF searchCond.enabled != null*/
	AND docType.enabled = /*searchCond.enabled*/
	/*END*/
	/*IF searchCond.status != null && searchCond.status != ''*/
	AND docType.status = /*searchCond.status*/
	/*END*/
