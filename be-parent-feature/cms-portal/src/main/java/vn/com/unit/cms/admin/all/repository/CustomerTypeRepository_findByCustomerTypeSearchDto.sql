SELECT
	  ntype.id				AS	id
  	, ntype.code			AS	code
  	, ntype.name			AS	name
  	, ntype.enabled			AS	enabled
  	, typeLang.title		AS	title
  	, ntype.sort  			AS	sort
  	, ntype.description		AS	description
  	, ntype.create_date		AS  create_date
FROM m_customer_type ntype
LEFT JOIN m_customer_type_language typeLang ON (ntype.id = typeLang.m_customer_type_id AND typeLang.delete_date is null)
WHERE
	ntype.delete_date is null
	AND UPPER(typeLang.m_language_code) = UPPER(/*searchCond.languageCode*/)
	/*BEGIN*/AND (
	/*IF searchCond.code != null && searchCond.code != ''*/
	OR ntype.code LIKE concat('%',  /*searchCond.code*/, '%')
	/*END*/
	/*IF searchCond.description != null && searchCond.description != ''*/
	OR ntype.description LIKE concat('%',  /*searchCond.description*/, '%')
	/*END*/
	/*IF searchCond.title != null && searchCond.title != ''*/
	OR typeLang.title LIKE concat('%',  /*searchCond.title*/, '%')
	/*END*/
	)/*END*/
ORDER BY ntype.create_date DESC
LIMIT /*sizeOfPage*/ OFFSET /*offset*/