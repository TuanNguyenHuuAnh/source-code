SELECT COUNT(*)
FROM
	APP_FEEDBACK feedback
WHERE
	feedback.DELETED_ID = 0
	
	/*IF search.companyId != null*/
	AND feedback.COMPANY_ID = /*search.companyId*/1 
	/*END*/ 
	/*IF search.content != null && search.content !=''*/
	AND feedback.CONTENT LIKE concat(concat('%',  /*search.content*/), '%')
	/*END*/
	
	/*IF search.fromDate != null*/
	AND /*search.fromDate*/ <= feedback.CREATED_DATE
	/*END*/
	/*IF search.toDate != null*/
	AND /*search.toDate*/ >= feedback.CREATED_DATE
	/*END*/