SELECT
	pop_lang.id 				AS id,
	pop_lang.name 				AS name,
	pop_lang.content 			AS content,
	pop_lang.m_language_code 	AS language_code,
	pop_lang.m_popup_code 		AS popup_code	
FROM
	m_popup_language pop_lang			    
WHERE 
	delete_by IS NULL
	/*IF popupCode != null && popupCode != ''*/
	AND m_popup_code =/*popupCode*/
	/*END*/
	ORDER BY create_date DESC