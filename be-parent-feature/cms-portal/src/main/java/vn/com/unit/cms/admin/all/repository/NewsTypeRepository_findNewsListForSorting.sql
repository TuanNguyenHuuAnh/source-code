SELECT
	  ntype.id				AS	id
  	, ntype.code			AS	code
  	, ntype.name			AS	name
  	, typeLang.label		AS	label
  	, ntype.sort  			AS	sort
  	, ntype.create_date		AS  create_date
FROM m_news_type ntype
JOIN m_news_type_language typeLang ON (ntype.id = typeLang.m_news_type_id AND typeLang.delete_date is null)

WHERE
	ntype.delete_date is null
	AND ntype.ENABLED = 1
	AND UPPER(typeLang.m_language_code) = UPPER(/*languageCode*/)
	AND ntype.is_promotion = 0
	AND ntype.ENABLED = 1
	AND ntype.M_CUSTOMER_TYPE_ID = /*customerId*/
ORDER BY  ntype.sort ASC, ntype.create_date DESC, ntype.ENABLED DESC, typeLang.label