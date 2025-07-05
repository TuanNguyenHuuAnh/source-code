SELECT
	pop.id 					AS id,	
	pop.active 				AS active,
	pop.code 				AS CODE,	
	pop.create_date 		AS create_date,
	pop.effective_date 		AS effective_date,
	pop.expiry_date 		AS expiry_date,
	pop.position_display	AS position_display,
	pop.page_display		AS page_display,
	pop_lang.name 			AS name,
	pop_lang.content 		AS content
FROM
	m_popup pop
LEFT JOIN m_popup_language pop_lang ON pop.code = pop_lang.m_popup_code
WHERE
	pop.delete_by IS NULL	
	/*IF popupDto.languageCode != null && popupDto.languageCode != ''*/
	AND pop_lang.m_language_code = UPPER(/*popupDto.languageCode*/)
	/*END*/
	/*IF popupDto.id != null*/
	AND pop.id = /*popupDto.id*/
	/*END*/
	/*IF popupDto.name != null && popupDto.name != ''*/
	AND pop_lang.name LIKE concat('%',  /*popupDto.name*/, '%')
	/*END*/		
	/*IF popupDto.effectiveDate != null */
	AND pop.effective_date >= /*popupDto.effectiveDate*/
	/*END*/
	/*IF popupDto.expiryDate != null */
	AND pop.expiry_date <= /*popupDto.expiryDate*/
	/*END*/
	
	ORDER BY pop.create_date DESC