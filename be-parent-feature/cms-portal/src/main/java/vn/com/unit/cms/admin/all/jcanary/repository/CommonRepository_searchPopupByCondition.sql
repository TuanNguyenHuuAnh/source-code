SELECT
	pop.id 					AS id_popup,		
--	pop.effective_date 		AS effective_date,
	pop.expiry_date 		AS expiry_date,	
	pop_lang.name 			AS name,
	pop_lang.content 		AS content
FROM
	m_popup pop
LEFT JOIN m_popup_language pop_lang ON pop.code = pop_lang.m_popup_code
WHERE
	pop.delete_by IS NULL	
	/*IF languageCode != null && languageCode != ''*/
	AND pop_lang.m_language_code = UPPER(/*languageCode*/)
	/*END*/		
	/*IF expiryDate != null */
	AND pop.expiry_date >= (/*expiryDate*/)
	/*END*/
	
	ORDER BY pop.create_date DESC