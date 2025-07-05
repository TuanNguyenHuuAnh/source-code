SELECT tbL.id,
		tbL.m_service_id,
		tbL.m_language_code,
		tbL.title,
		tbL.description_abv,
		tbL.description_slogan
FROM m_service_language tbL
WHERE 
tbL.m_service_id = /*id*/