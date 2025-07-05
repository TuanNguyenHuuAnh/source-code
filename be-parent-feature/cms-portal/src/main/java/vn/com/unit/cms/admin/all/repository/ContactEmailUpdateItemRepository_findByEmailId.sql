SELECT
	contactEmail.comment_name			AS	"comment"
	, contactEmail.*
	, contactEmail.processing_status 	AS status
FROM m_contact_email_update_item 	contactEmail
WHERE
	contactEmail.delete_date is null
	AND contactEmail.m_contact_email_id = /*emailId*/
ORDER BY
	contactEmail.create_date DESC