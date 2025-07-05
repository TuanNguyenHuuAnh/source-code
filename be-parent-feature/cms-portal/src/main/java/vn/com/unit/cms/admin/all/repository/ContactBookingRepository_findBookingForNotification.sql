SELECT
	 *
FROM m_contact_booking contactBooking
WHERE
	delete_date is null

	AND	datediff(contactBooking.date_booking, /*dateOffset*/)  = /*remainDay*/

