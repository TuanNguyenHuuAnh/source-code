SELECT
	  count(*)
FROM m_product_category_sub categ
LEFT JOIN m_product_category_sub_language clanguage 
	ON (categ.id = clanguage.m_product_category_sub_id AND clanguage.delete_date is null)
LEFT JOIN m_product_category pc 
	ON (pc.id = categ.m_product_category_id AND pc.delete_date is null)
LEFT JOIN m_product_category_language pcl 
	ON (pcl.m_product_category_id = pc.id AND pcl.delete_date is null)
LEFT JOIN m_customer_type_language customerType 
	ON (customerType.m_customer_type_id = categ.m_customer_type_id 
	AND customerType.m_language_code = clanguage.m_language_code
	AND customerType.delete_date is null)
WHERE
	categ.delete_date is null
	AND UPPER(clanguage.m_language_code) = UPPER(/*categCond.languageCode*/)
	AND UPPER(pcl.m_language_code) = UPPER(/*categCond.languageCode*/)	
	AND categ.M_CUSTOMER_TYPE_ID = /*categCond.customerTypeId*/9
	/*IF categCond.categoryId != null*/
	AND categ.m_product_category_id = /*categCond.categoryId*/
	/*END*/
	/*IF categCond.statusText != null && categCond.statusText != ''*/
	AND categ.status = /*categCond.statusText*/
	/*END*/
	/*IF categCond.code != null && categCond.code != ''*/
	AND categ.code LIKE concat('%',/*categCond.code*/,'%')
	/*END*/
	/*IF categCond.title != null && categCond.title != ''*/
	AND clanguage.title LIKE concat('%',/*categCond.title*/,'%')
	/*END*/	
	/*IF categCond.enabled != null*/
	AND categ.enabled = /*categCond.enabled*/
	/*END*/
	/*IF categCond.priority != null*/
	AND categ.is_priority = /*categCond.priority*/
	/*END*/	
