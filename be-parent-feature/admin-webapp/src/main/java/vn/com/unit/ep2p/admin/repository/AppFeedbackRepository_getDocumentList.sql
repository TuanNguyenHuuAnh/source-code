SELECT 
	feedback.ID						AS ID
	,feedback.CONTENT				AS CONTENT
	,company.NAME					AS COMPANY_NAME
	,feedback.STATUS				AS STATUS
	,feedback.CREATED_DATE			AS CONTENT_CREATED_DATE
	,account.FULLNAME				AS USER_NAME
FROM
	APP_FEEDBACK feedback
LEFT JOIN
	jca_company	company
ON
	company.ID = feedback.COMPANY_ID
LEFT JOIN 
	JCA_ACCOUNT account
ON
	account.ID = feedback.USER_ID
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
ORDER BY
    FN_REMOVE_TIME_FROM_DATE(feedback.CREATED_DATE) DESC
	, feedback.CONTENT DESC
	, feedback.CREATED_DATE DESC
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY