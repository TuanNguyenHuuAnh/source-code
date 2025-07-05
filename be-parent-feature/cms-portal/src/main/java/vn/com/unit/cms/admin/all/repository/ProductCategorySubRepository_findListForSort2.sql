SELECT
	  categ.id				AS	id
  	, clanguage.title		AS	text
  	, clanguage.title		AS	name
FROM m_product_category_sub categ
LEFT JOIN m_product_category_sub_language clanguage ON (categ.id = clanguage.m_product_category_sub_id AND clanguage.delete_date is null)
LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = categ.m_customer_type_id 
											AND customerType.m_language_code = clanguage.m_language_code
											AND customerType.delete_date is null)
WHERE
	categ.delete_date is null
	AND categ.ENABLED = 1
	AND UPPER(clanguage.m_language_code) = UPPER(/*languageCode*/'')
	/*IF customerId != null*/
		AND customerType.m_customer_type_id = /*customerId*/
	/*END*/
	/*IF typeId != null*/	
	AND categ.m_product_category_id = /*typeId*/
	/*END*/
	/*IF categoryId != null*/
	AND categ.id != /*categoryId*/
	/*END*/
ORDER BY categ.sort