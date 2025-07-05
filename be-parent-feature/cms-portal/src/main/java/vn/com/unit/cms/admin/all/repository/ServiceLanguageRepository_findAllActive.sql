SELECT s.id,
	s.m_customer_type_id,
	sL.m_language_code,
	s.code,
	sL.title,
	sL.description_abv,
	s.create_date, s.sort_order,
	sL.m_customer_type_name
FROM m_service s
JOIN m_service_language sL ON s.id = sL.m_service_id
WHERE s.delete_date IS NULL
	/*IF serviceSearchDto.langCode != null && serviceSearchDto.langCode != ''*/
	AND	sL.m_language_code = /*serviceSearchDto.langCode*/
	/*END*/
	/*BEGIN*/	
	AND (
	/*IF serviceSearchDto.code != null && serviceSearchDto.code != ''*/	
	s.code LIKE concat('%',  /*serviceSearchDto.code*/, '%')
	/*END*/	
	/*IF serviceSearchDto.title != null && serviceSearchDto.title != ''*/
	OR sL.title LIKE concat('%',  /*serviceSearchDto.title*/, '%')
	/*END*/	
	/*IF serviceSearchDto.descriptionAbv != null && serviceSearchDto.descriptionAbv != ''*/
	OR sL.description_abv LIKE concat('%',  /*serviceSearchDto.descriptionAbv*/, '%')
	/*END*/
	/*IF serviceSearchDto.customerTypesSearch != null && serviceSearchDto.customerTypesSearch != ''*/
	OR sL.m_customer_type_name LIKE concat('%',  /*serviceSearchDto.customerTypesSearch*/, '%')
	/*END*/	
	)
	/*END*/
ORDER BY s.sort_order, s.create_date DESC
LIMIT /*sizeOfPage*/ OFFSET /*offset*/
