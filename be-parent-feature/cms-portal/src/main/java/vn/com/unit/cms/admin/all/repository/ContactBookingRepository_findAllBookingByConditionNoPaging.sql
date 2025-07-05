SELECT
	 *
FROM m_contact_booking contactBooking
WHERE
	delete_date is null
		/*IF searchCondition.fullName != null && searchCondition.fullName != ''*/
		AND contactBooking.full_name LIKE concat('%',/*searchCondition.fullName*/,'%')
		/*END*/
		/*IF searchCondition.email != null && searchCondition.email != ''*/
		AND contactBooking.email LIKE concat('%', /*searchCondition.email*/ ,'%')
		/*END*/
		/*IF searchCondition.phoneNumber != null && searchCondition.phoneNumber != ''*/
		AND contactBooking.phone_number LIKE concat('%',/*searchCondition.phoneNumber*/,'%')
		/*END*/
		/*IF searchCondition.idNumber != null && searchCondition.idNumber != ''*/
		AND contactBooking.id_number LIKE concat('%',/*searchCondition.idNumber*/,'%')
		/*END*/
		/*IF searchCondition.bookingSubject != null && searchCondition.bookingSubject != ''*/
		AND contactBooking.booking_subject LIKE concat('%',/*searchCondition.bookingSubject*/,'%')
		/*END*/
		/*IF searchCondition.placeBooking != null && searchCondition.placeBooking != ''*/
		AND contactBooking.place_booking LIKE concat('%',/*searchCondition.placeBooking*/,'%')
		/*END*/
		/*IF searchCondition.bookingContent != null && searchCondition.bookingContent != ''*/
		AND contactBooking.booking_content LIKE concat('%',/*searchCondition.bookingContent*/,'%')
		/*END*/
	/*IF searchCondition.fromDate != null || searchCondition.toDate != null*/
	AND (
		/*IF searchCondition.fromDate != null && searchCondition.toDate != null*/
		(
			contactBooking.date_booking >= /*searchCondition.fromDate*/
			AND 
			contactBooking.date_booking <= /*searchCondition.toDate*/
		)
		/*END*/
		/*IF searchCondition.fromDate != null && searchCondition.toDate == null*/
		 contactBooking.date_booking >= /*searchCondition.fromDate*/
		/*END*/
		/*IF searchCondition.fromDate == null && searchCondition.toDate != null*/
		 contactBooking.date_booking <= /*searchCondition.toDate*/
		/*END*/
	)
	/*END*/
	/*IF searchCondition.processingStatus != null && searchCondition.processingStatus != ''*/
	AND contactBooking.processing_status LIKE concat('%',/*searchCondition.processingStatus*/,'%')
	/*END*/
	ORDER BY
	create_date DESC

