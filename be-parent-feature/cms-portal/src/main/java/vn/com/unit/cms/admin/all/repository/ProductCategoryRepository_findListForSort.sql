SELECT
	  categ.id				AS	id
  	, categ.code			AS	code
  	, categ.name			AS	name
  	, categ.enabled			AS	enabled
  	, clanguage.title		AS	title
  	, categ.sort  			AS	sort
  	, categ.before_id		AS	before_id
  	, customerType.title	AS  type_name
  	, categ.create_date		AS  create_date
    , categ.link_alias      AS  link_alias
FROM m_product_category categ
LEFT JOIN m_product_category_language clanguage ON (categ.id = clanguage.m_product_category_id AND clanguage.delete_date is null)
LEFT JOIN m_customer_type_language customerType ON (customerType.m_customer_type_id = categ.m_customer_type_id 
											AND customerType.m_language_code = clanguage.m_language_code
											AND customerType.delete_date is null)
WHERE
	categ.delete_date is null
	AND categ.ENABLED = 1
	AND UPPER(clanguage.m_language_code) = UPPER(/*languageCode*/'vi')
	AND customerType.m_customer_type_id = /*customerId*/9
ORDER BY  categ.sort
