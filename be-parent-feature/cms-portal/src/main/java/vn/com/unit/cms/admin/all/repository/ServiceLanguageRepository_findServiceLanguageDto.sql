SELECT tb.id,
	tb.name,
	tbL.m_language_code,
	tb.code,
	tbL.title,
	tbL.description_abv,
	tbL.description_slogan,
	tb.create_date,
	tbL.m_service_id,
	tb.note,
	tb.sort_order,
	tb.m_customer_type_id,
	tbL.m_customer_type_name,
	tb.image_url,
	tb.image_name
FROM m_service tb
LEFT JOIN m_service_language tbL ON tb.id = tbL.m_service_id
WHERE 
tbL.m_service_id = /*id*/ AND tbL.m_language_code = /*lang*/