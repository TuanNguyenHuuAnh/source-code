SELECT
	  categ.id			AS id
	, categ.code		AS code
	, categLang.title	AS title
FROM 
	m_product_category_sub categ
	JOIN m_product_category_sub_language categLang ON (categLang.m_product_category_sub_id = categ.id AND categLang.delete_date is null)
WHERE categ.delete_date is null
	AND categ.ENABLED = 1
	AND categ.m_customer_type_id = /*typeId*/
	AND UPPER(categLang.m_language_code) = UPPER(/*languageCode*/)
ORDER BY categLang.title ASC