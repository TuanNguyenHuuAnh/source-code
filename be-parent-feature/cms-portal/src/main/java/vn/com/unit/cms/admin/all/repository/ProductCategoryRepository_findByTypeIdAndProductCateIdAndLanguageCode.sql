SELECT distinct
	  categ.id			AS id
	, categ.code		AS code
	, categLang.title	AS title
FROM 
	m_product_category categ
	LEFT JOIN m_product_category_language categLang ON (categLang.m_product_category_id = categ.id AND categLang.delete_date is null)
WHERE categ.delete_date is null
	and categ.ENABLED = 1
	AND categ.m_customer_type_id = /*typeId*/
	AND UPPER(categLang.m_language_code) = UPPER(/*languageCode*/)
	AND categ.is_promotion = 0
	/*IF status != null*/
        AND categ.status = /*status*/
    /*END*/
	/*IF productTypeId != null*/
        AND categ.id = /*productTypeId*/
    /*END*/
order by categLang.title