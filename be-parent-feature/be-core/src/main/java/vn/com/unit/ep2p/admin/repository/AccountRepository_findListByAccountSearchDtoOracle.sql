SELECT DISTINCT * FROM
(
SELECT
    acc.id                AS id
	, acc.username        AS username
	, acc.fullname        AS fullname
	, acc.email           AS email
	, acc.phone           AS phone
	, acc.birthday        AS birthday
	, acc_code.code       AS status_code
	, co.id 			  AS company_id
	, co.name 			  AS company_name
FROM
	(	
		(	jca_account acc 
		 	LEFT JOIN
		    jca_m_org m_org
			ON
		    acc.branch_id = m_org.id
		)
  	LEFT JOIN jca_position position ON acc.position_id = position.id
	)
LEFT JOIN
    JCA_CONSTANT acc_code
ON
    acc_code.group_code = 'M02'
    AND acc_code.note = acc.enabled
LEFT JOIN
	JCA_COMPANY co ON acc.company_id = co.id and co.DELETED_ID = 0
WHERE
	acc.DELETED_ID = 0
	/*IF !accountSearchDto.companyAdmin*/
	AND acc.COMPANY_ID = /*accountSearchDto.companyId*/
	/*END*/
	/*BEGIN*/	
	AND
	(
		/*IF accountSearchDto.userName != null && accountSearchDto.userName != ''*/
		OR UPPER(replace(acc.username,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*accountSearchDto.userName*/), '%' ))
		/*END*/
		/*IF accountSearchDto.email != null && accountSearchDto.email != ''*/
	    OR UPPER(replace(acc.email,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*accountSearchDto.email*/), '%' ))
	    /*END*/
	    
	    /*IF accountSearchDto.fullName != null && accountSearchDto.fullName != ''*/
	    OR UPPER(acc.fullname) LIKE CONCAT( '%', CONCAT(UPPER(/*accountSearchDto.fullName*/), '%' ))
	    /*END*/
	    
	    /*IF accountSearchDto.birthday != null*/
	    OR acc.birthday = /*accountSearchDto.birthday*/
	    /*END*/
	    
	    /*IF accountSearchDto.phone != null && accountSearchDto.phone != ''*/
	    OR replace(acc.phone,' ','') LIKE CONCAT( '%', CONCAT(/*accountSearchDto.phone*/, '%' ))
	    /*END*/
	    
	    /*IF accountSearchDto.departmentName != null && accountSearchDto.departmentName != ''*/
	    OR UPPER(replace(m_org.org_name,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*accountSearchDto.departmentName*/), '%' ))
	    /*END*/
	    
	     /*IF accountSearchDto.positionName != null && accountSearchDto.positionName != ''*/
	    OR UPPER(replace(position.name,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*accountSearchDto.positionName*/), '%' ))
	    /*END*/
	    
	    /*IF accountSearchDto.branchName != null && accountSearchDto.branchName != ''*/
	    OR UPPER(m_org.org_name) LIKE CONCAT( '%', CONCAT(UPPER(/*accountSearchDto.branchName*/), '%' ))
	    /*END*/
    )
    /*END*/
 ) TBL
ORDER BY
	company_name,
    username ASC
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY
