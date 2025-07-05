SELECT
	*
FROM
	jca_position position			
WHERE
	position.DELETED_ID = 0	
	AND position.code = /*code*/
	AND position.company_id = /*companyId*/
	/*IF positionId!=null*/
		AND position.id <> /*positionId*/
	/*END*/