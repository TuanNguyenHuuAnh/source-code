SELECT
	 *
FROM m_contact_mail 
WHERE
	delete_date is null
ORDER BY
	processing_status DESC, create_date DESC