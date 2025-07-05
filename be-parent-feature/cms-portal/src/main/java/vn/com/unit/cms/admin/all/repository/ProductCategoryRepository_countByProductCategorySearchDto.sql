SELECT
	  count(*)
FROM m_product_category categ
LEFT JOIN m_product_category_language clanguage ON (categ.id = clanguage.m_product_category_id AND clanguage.delete_date is null)
LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = categ.m_customer_type_id 
											AND customerType.m_language_code = clanguage.m_language_code
											AND customerType.delete_date is null)
WHERE
	
	categ.delete_date is null
	
	AND UPPER(clanguage.m_language_code) = UPPER(/*categCond.languageCode*/)
	
	AND categ.M_CUSTOMER_TYPE_ID = /*categCond.customerId*/
	
	/*IF categCond.code != null && categCond.code != ''*/
	AND categ.code LIKE concat('%',/*categCond.code*/,'%')
	/*END*/
	
	/*IF categCond.name != null && categCond.name != ''*/
	AND clanguage.title LIKE concat('%',/*categCond.name*/,'%')
	/*END*/
	
	/*IF categCond.status != null && categCond.status != ''*/
	AND categ.status = /*categCond.status*/
	/*END*/

	/*IF categCond.enabled != null*/
	AND categ.enabled = /*categCond.enabled*/
	/*END*/