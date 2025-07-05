SELECT
	  categ.id				AS	id
  	, categ.code			AS	code
  	, categ.name			AS	name
  	, categ.enabled			AS	enabled
  	, clanguage.title		AS	title
  	, categ.sort  			AS	sort
  	, categ.before_id		AS	before_id
  	, categ.create_date		AS  create_date
    , clanguage.link_alias  AS  link_alias
FROM m_product_category categ
LEFT JOIN m_product_category_language clanguage ON (categ.id = clanguage.m_product_category_id AND clanguage.delete_date is null)
WHERE
	categ.delete_date is null
	AND categ.ENABLED = 1
	AND categ.status = 99
	AND UPPER(clanguage.m_language_code) = UPPER(/*language*/'vi')
	/*IF customerId != -1*/
	AND categ.M_CUSTOMER_TYPE_ID = /*customerId*/
	/*END*/
ORDER BY categ.sort ASC, clanguage.title
