--
-- ContactEmailRepository_countBySearchCondition.sql

SELECT
	COUNT(*)
FROM m_contact_mail contactEmail
LEFT JOIN M_PRODUCT_CATEGORY_LANGUAGE cateLang
	ON(cateLang.delete_date is NULL
		AND cateLang.LINK_ALIAS = contactEmail.service
		AND UPPER(cateLang.M_LANGUAGE_CODE) = UPPER(/*searchCondition.language*/)
	)
LEFT JOIN JCA_CONSTANT_DISPLAY	displayMotive
	ON(displayMotive.deleted_date is NULL
		AND displayMotive.TYPE = 'MOTIVE'
		AND displayMotive.cat = contactEmail.motive
	)
WHERE
	contactEmail.delete_date is null
	/*IF searchCondition.fullName != null && searchCondition.fullName != ''*/
	AND contactEmail.full_name LIKE concat('%',/*searchCondition.fullName*/,'%')
	/*END*/
	/*IF searchCondition.email != null && searchCondition.email != ''*/
	AND contactEmail.email LIKE concat('%',/*searchCondition.email*/,'%')
	/*END*/
	/*IF searchCondition.emailSubject != null && searchCondition.emailSubject != ''*/
	AND contactEmail.subject LIKE concat('%',/*searchCondition.emailSubject*/,'%')
	/*END*/
	/*IF searchCondition.emailContent != null && searchCondition.emailContent != ''*/
	AND contactEmail.content LIKE concat('%',/*searchCondition.emailContent*/,'%')
	/*END*/
	/*IF searchCondition.motive != null*/
	AND contactEmail.motive = /*searchCondition.motive*/
	/*END*/
	/*IF searchCondition.service != null && searchCondition.service != ''*/
	AND contactEmail.service LIKE concat('%',/*searchCondition.service*/,'%')
	/*END*/
	/*IF searchCondition.customerId != null && searchCondition.customerId != ''*/
	AND contactEmail.customer_type_id = /*searchCondition.customerId*/
	/*END*/
	/*IF searchCondition.fromDate != null*/
	AND CAST(contactEmail.create_date as DATE) >= CAST(/*searchCondition.fromDate*/'' as DATE)
	/*END*/
	/*IF searchCondition.toDate != null*/
	AND CAST(contactEmail.create_date as DATE) <= CAST(/*searchCondition.toDate*/'' as DATE)
	/*END*/
	/*IF searchCondition.processingStatus != null && searchCondition.processingStatus != ''*/
	AND contactEmail.processing_status LIKE concat('%',/*searchCondition.processingStatus*/,'%')
	/*END*/
