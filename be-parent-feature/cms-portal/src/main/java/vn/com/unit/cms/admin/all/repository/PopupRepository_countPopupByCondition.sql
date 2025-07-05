SELECT
	count(*)
FROM
    m_popup pop
LEFT JOIN m_popup_language pop_lang ON pop.code = pop_lang.m_popup_code
WHERE
	pop.delete_by IS NULL	
	/*IF popupDto.languageCode != null && popupDto.languageCode != ''*/
	AND pop_lang.m_language_code = UPPER(/*popupDto.languageCode*/)
	/*END*/
	/*IF popupDto.name != null && popupDto.name != ''*/	
	AND pop_lang.name LIKE concat('%',/*popupDto.name*/,'%')
	/*END*/	
	/*IF popupDto.effectiveDate != null */
	AND pop.expiry_date >= /*popupDto.effectiveDate*/
	/*END*/	
	/*IF popupDto.expiryDate != null */
	AND pop.expiry_date <= /*popupDto.expiryDate*/
	/*END*/	