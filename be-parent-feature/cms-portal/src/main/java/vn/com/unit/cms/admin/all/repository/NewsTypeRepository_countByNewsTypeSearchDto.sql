SELECT
	  count(*)
FROM m_news_type ntype
JOIN m_news_type_language typeLang ON (ntype.id = typeLang.m_news_type_id AND typeLang.delete_date is null)
LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = ntype.m_customer_type_id 
											AND customerType.m_language_code = typeLang.m_language_code
											AND customerType.delete_date is null)
WHERE
	ntype.delete_date is null	
	AND UPPER(typeLang.m_language_code) = UPPER(/*searchCond.languageCode*/)
	AND ntype.m_customer_type_id = /*searchCond.customerTypeId*/
	/*IF searchCond.code != null && searchCond.code != ''*/
	AND ntype.code LIKE concat('%',/*searchCond.code*/,'%')
	/*END*/
	/*IF searchCond.label != null && searchCond.label != ''*/
	AND typeLang.label LIKE concat('%',/*searchCond.label*/,'%')
	/*END*/
	/*IF searchCond.status != null && searchCond.status != ''*/
	AND ntype.status LIKE concat('%',/*searchCond.status*/,'%')
	/*END*/
	/*IF searchCond.statusActive != null*/
	AND ntype.ENABLED = /*searchCond.statusActive*/
	/*END*/
	/*IF searchCond.typeOfLibrary != null*/
	AND ntype.TYPE_OF_LIBRARY = /*searchCond.typeOfLibrary*/
	/*END*/
