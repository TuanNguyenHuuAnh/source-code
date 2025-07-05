SELECT m.COMMENT_NAME 		AS comment_name
    , m.COMMENT_CODE 		AS comment_code
    , m.CREATE_BY 			AS create_by
    , m.CREATE_DATE  		AS create_date
    , m.ID					AS id
    , m.PROCESSED_USER 		AS processed_user
    , m.PROCESSING_STATUS  	AS status_title
    , m.processing_status 	AS status
FROM m_contact_booking_update_item m
WHERE m.delete_date is null
AND m.m_contact_booking_id = /*bookingId*/
ORDER BY m.create_date DESC