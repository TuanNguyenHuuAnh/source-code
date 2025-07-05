SELECT COUNT(md.id)    
FROM m_document md
join M_DOCUMENT_LANGUAGE mdl on mdl.M_DOCUMENT_ID = md.id
WHERE md.delete_by is NULL
	and md.ENABLED = 1
	and mdl.M_LANGUAGE_CODE = /*languageCode*/
	/*IF customerTypeId != null*/
	AND md.M_CUSTOMER_TYPE_ID = /*customerTypeId*/9
	/*END*/
	/*IF typeId != null*/
	AND md.M_DOCUMENT_TYPE_ID = /*typeId*/1
	/*END*/
	/*IF linkAlias != null && linkAlias != ''*/
	AND mdl.LINK_ALIAS = /*linkAlias*/
	/*END*/
	/*IF exceptId != null && exceptId != ''*/
	AND mdl.id != /*exceptId*/
	/*END*/