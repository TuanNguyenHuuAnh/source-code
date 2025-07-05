SELECT
	investorLang.id
	, investor.* 
FROM m_investor investor
LEFT JOIN m_investor_language investorLang
	ON (investorLang.delete_date is NULL
		AND investorLang.M_INVESTOR_ID = investor.ID
		AND UPPER(investorLang.M_LANGUAGE_CODE) = UPPER(/*language*/'')
	)
WHERE
	investor.delete_date is NULL
	AND investor.M_INVESTOR_CATEGORY_ID = /*categoryId*/
	AND UPPER(investorLang.LINK_ALIAS) = UPPER(/*linkAlias*/)