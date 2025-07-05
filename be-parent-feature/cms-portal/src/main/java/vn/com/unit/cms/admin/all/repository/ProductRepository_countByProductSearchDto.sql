SELECT
	count(*)
FROM m_product product
JOIN m_product_language productLan ON (productLan.m_product_id = product.id AND productLan.delete_date is null)
JOIN m_product_category_language category ON (category.m_product_category_id = product.m_product_category_id 
												AND category.m_language_code = productLan.m_language_code
												AND category.delete_date is null)
JOIN m_customer_type_language productType ON (productType.m_customer_type_id = product.m_customer_type_id 
											AND productType.m_language_code = productLan.m_language_code
											AND productType.delete_date is null
											)
LEFT JOIN m_product_category_sub_language categorySub ON (categorySub.m_product_category_sub_id = product.m_product_category_sub_id 
														  AND categorySub.m_language_code = productLan.m_language_code
														  AND categorySub.delete_date is null)
WHERE
	product.delete_date is null
	AND UPPER(productLan.m_language_code) = UPPER(/*searchCond.languageCode*/)
	/*IF searchCond.microsite != null*/
	AND product.is_microsite = /*searchCond.microsite*/
	/*END*/
	/*IF searchCond.typeId != null */
	AND product.m_customer_type_id = /*searchCond.typeId*/
	/*END*/
	/*IF searchCond.categoryId != null*/
	AND product.m_product_category_id = /*searchCond.categoryId*/
	/*END*/
	/*IF searchCond.categorySubId != null*/
	AND product.m_product_category_sub_id = /*searchCond.categorySubId*/
	/*END*/
	/*IF searchCond.status != null && searchCond.status != ''*/
	AND product.status = /*searchCond.status*/
	/*END*/
	/*IF searchCond.code != null && searchCond.code != ''*/
	AND product.code LIKE concat('%',/*searchCond.code*/,'%')
	/*END*/
	/*IF searchCond.title != null && searchCond.title != ''*/
	AND productLan.title LIKE concat('%',/*searchCond.title*/,'%')
	/*END*/
	/*IF searchCond.enabled != null*/
	AND product.ENABLED = /*searchCond.enabled*/
	/*END*/
	/*IF searchCond.highlights != null*/
	AND product.IS_HIGHLIGHTS = /*searchCond.highlights*/
	/*END*/
	/*IF searchCond.lending != null*/
	AND product.IS_LENDING = /*searchCond.lending*/
	/*END*/
	/*IF searchCond.priority != null*/
	AND product.IS_PRIORITY = /*searchCond.priority*/
	/*END*/
	/*IF searchCond.showForm != null*/
	AND product.SHOW_FORM = /*searchCond.showForm*/
	/*END*/