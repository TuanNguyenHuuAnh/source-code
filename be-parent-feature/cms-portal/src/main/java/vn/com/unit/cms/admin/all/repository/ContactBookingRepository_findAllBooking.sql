SELECT
	 *
FROM m_contact_booking 
WHERE
	delete_date is null
ORDER BY
	processing_status DESC, create_date DESC