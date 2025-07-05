SELECT 
    COUNT(*)
FROM 
    m_investor  investor
LEFT JOIN m_investor_language   investorLang
    ON (investor.id = investorlang.m_investor_id
        AND investorlang.delete_date is NULL
        AND UPPER(investorlang.m_language_code) = UPPER(/*searchDto.language*/'vi')
    )
LEFT JOIN m_investor_category   investorCate
    ON (investorCate.delete_date is NULL
        AND investorCate.status = 99
        AND investorCate.id = investor.M_INVESTOR_CATEGORY_ID
    )
LEFT JOIN m_investor_category_language investorCateLang
    ON (investor.M_INVESTOR_CATEGORY_ID = investorCateLang.M_INVESTOR_CATEGORY_ID
        AND investorCateLang.delete_date is NULL
        AND UPPER(investorCateLang.m_language_code) = UPPER(/*searchDto.language*/'vi') 
    )
LEFT JOIN jca_constant_display display
    ON (display.type = 'NDT'
        AND display.delete_date is NULL
        AND display.cat = investorCate.INVESTOR_CATEGORY_TYPE
    )
WHERE
    investor.delete_date is NULL
	/*IF searchDto.status != null && searchDto.status != ''*/
	AND investor.status = /*searchDto.status*/
	/*END*/
    /*IF searchDto.name != null && searchDto.name != ''*/
	AND (investorLang.title LIKE concat('%',/*searchDto.name*/, '%')
		OR UPPER(investor.code) LIKE concat('%',/*searchDto.name*/,'%')
	)
	/*END*/
    /*IF searchDto.enabled != null && searchDto.enabled != ''*/
	AND investor.enabled = /*searchDto.enabled*/
	/*END*/
    /*IF searchDto.categoryId != null && searchDto.categoryId != ''*/
	AND investor.M_INVESTOR_CATEGORY_ID = /*searchDto.categoryId*/
	/*END*/
    /*IF searchDto.kind != null*/
	AND investor.kind = /*searchDto.kind*/
	/*END*/