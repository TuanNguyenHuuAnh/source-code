SELECT
	count(*)
FROM m_currency currency
LEFT JOIN m_currency_language currencyLanguage ON (currency.id = currencyLanguage.m_currency_id AND currencyLanguage.delete_date is null)
WHERE
	currency.delete_date is NULL
	AND UPPER(currencyLanguage.m_language_code) = UPPER(/*languageCode*/)
	/*IF currencySearchDto.code != null && currencySearchDto.code != ''*/
	AND UPPER(currency.code) LIKE UPPER(TRIM('%'||  /*currencySearchDto.code*/  ||'%'))
	/*END*/
	/*IF currencySearchDto.name != null && currencySearchDto.name != ''*/
	AND UPPER(currencyLanguage.title) LIKE UPPER(TRIM('%'||  /*currencySearchDto.name*/  ||'%'))
	/*END*/
	/*IF currencySearchDto.status != null && currencySearchDto.status != ''*/
	AND UPPER(currency.status) LIKE UPPER(TRIM('%'||  /*currencySearchDto.status*/  ||'%'))
	/*END*/