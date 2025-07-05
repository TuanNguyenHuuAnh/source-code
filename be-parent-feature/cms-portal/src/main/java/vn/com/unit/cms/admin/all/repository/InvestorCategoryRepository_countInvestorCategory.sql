SELECT
	  count(*)
FROM m_investor_category categ
LEFT JOIN m_investor_category_language clanguage ON (categ.id = clanguage.m_investor_category_id AND clanguage.delete_date is null)

WHERE
	categ.delete_date is null
	
	AND UPPER(clanguage.m_language_code) = UPPER(/*searchDto.languageCode*/)

	AND categ.customer_type_id = /*searchDto.customerId*/
	
	/*IF searchDto.categoryType != null*/
      AND UPPER(categ.investor_category_type)  = UPPER(/*searchDto.categoryType*/)
    /*END*/
	
	/*IF searchDto.status != null*/
      AND UPPER(categ.status)  = UPPER(/*searchDto.status*/)
    /*END*/
      
    /*IF searchDto.code != null && searchDto.code != ''*/
		AND categ.code LIKE concat('%',/*searchDto.code*/,'%')
	/*END*/
		
	 /*IF searchDto.name != null && searchDto.name != ''*/
		AND clanguage.title LIKE concat('%',/*searchDto.name*/,'%')
	/*END*/	
 