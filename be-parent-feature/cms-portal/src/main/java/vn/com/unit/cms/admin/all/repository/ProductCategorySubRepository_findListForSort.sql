SELECT
	  categ.id				AS	id
  	, categ.code			AS	code
  	, categ.name			AS	name
  	, categ.enabled			AS	enabled
  	, clanguage.title		AS	title
  	, categ.sort  			AS	sort
  	, customerType.title	AS  type_name
  	, categ.description		AS  description
  	, categ.create_date		AS  create_date
FROM m_product_category_sub categ
LEFT JOIN m_product_category_sub_language clanguage ON (categ.id = clanguage.m_product_category_sub_id AND clanguage.delete_date is null)
LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = categ.m_customer_type_id 
											AND customerType.m_language_code = clanguage.m_language_code
											AND customerType.delete_date is null)
WHERE
	categ.delete_date is null
	AND categ.ENABLED = 1
	AND UPPER(clanguage.m_language_code) = UPPER(/*languageCode*/'')
	AND customerType.m_customer_type_id = /*customerId*/''
	AND categ.m_product_category_id = /*categoryId*/''
	AND categ.status != 100
ORDER BY categ.sort