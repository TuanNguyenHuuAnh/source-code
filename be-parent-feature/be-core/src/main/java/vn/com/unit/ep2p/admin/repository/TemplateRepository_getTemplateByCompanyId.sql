SELECT id as id, name AS name, name AS text
FROM
    JCA_EMAIL_TEMPLATE 
where 
DELETED_ID = 0
/*IF term!= null && term != ''*/
AND UPPER(NAME) like CONCAT('%',CONCAT(/*term*/'','%'))
/*END*/
/*IF companyId != null*/
AND (company_id = /*companyId*/
	or company_id is null)
/*END*/ 
/*IF companyId == null*/
AND COMPANY_ID IS NULL 
/*END*/
ORDER BY 
	CREATED_DATE DESC 
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/

