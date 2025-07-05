SELECT DISTINCT * FROM
(
SELECT
    acc.id                AS id
    , acc.code	          AS code
	, acc.username        AS username
	, acc.fullname        AS fullname
	, acc.email           AS email
	, acc.phone           AS phone
	, acc.birthday        AS birthday
	, acc_code.code       AS status_code
	, co.id 			  AS company_id
	, co.name 			  AS company_name
	, acc.actived		  AS actived
	, acc.enabled		  AS enabled
	, acc.IS_H_O		  AS is_h_o
	, acc.CAN_SEND_H_O	  AS can_send_h_o
FROM
	jca_account acc
	LEFT JOIN jca_m_account_org acc_org ON acc.id = acc_org.account_id AND acc_org.DELETED_ID = 0 AND acc_org.ASSIGN_TYPE = '1'
	LEFT JOIN jca_m_org m_org ON acc_org.org_id = m_org.id AND m_org.DELETED_ID = 0 AND (acc_org.org_id = 0 OR (acc_org.org_id <>0 and m_org.id is not null))
	LEFT JOIN jca_m_position position ON acc_org.position_id = position.id AND position.DELETED_ID = 0 AND (acc_org.position_id = 0 OR (acc_org.position_id <>0 and position.id is not null))
	LEFT JOIN JCA_CONSTANT acc_code ON acc_code.group_code = 'M02' AND acc_code.note = acc.enabled	
	LEFT JOIN JCA_COMPANY co ON acc.company_id = co.id and co.DELETED_ID = 0
WHERE
	acc.DELETED_ID = 0
	/*IF accountSearchDto.companyId != null && accountSearchDto.companyId != 0*/
	AND acc.COMPANY_ID = /*accountSearchDto.companyId*/
	/*END*/
	/*IF accountSearchDto.companyId == 0 && !accountSearchDto.companyAdmin*/
	AND acc.COMPANY_ID  IN /*accountSearchDto.companyIdList*/()
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
	    
	    /*IF accountSearchDto.orgName != null && accountSearchDto.orgName != ''*/
	    OR UPPER(m_org.org_name) LIKE CONCAT( '%', CONCAT(UPPER(/*accountSearchDto.orgName*/), '%' ))
	    /*END*/
    )
    /*END*/
    /*IF accountSearchDto.enabled != null && accountSearchDto.enabled*/
	AND acc.ENABLED = 1
	/*END*/
	/*IF accountSearchDto.locked != null && accountSearchDto.locked*/
	AND acc.ACTIVED = 0
	/*END*/
	/*IF accountSearchDto.unknownOrg != null && accountSearchDto.unknownOrg*/
	AND acc_org.org_id = 0
	/*END*/
	/*IF accountSearchDto.unknownPosition != null && accountSearchDto.unknownPosition*/
	AND acc_org.POSITION_ID = 0
	/*END*/
	/*IF accountSearchDto.emptyOrg != null && accountSearchDto.emptyOrg*/
	AND acc_org.org_id is null
	/*END*/
	/*IF accountSearchDto.emptyPosition != null && accountSearchDto.emptyPosition*/
	AND acc_org.position_id is null
	/*END*/
	/*IF accountSearchDto.BOD != null && accountSearchDto.BOD*/
	AND acc.IS_H_O = 1
	/*END*/
	/*IF accountSearchDto.sentBOD != null && accountSearchDto.sentBOD*/
	AND acc.CAN_SEND_H_O = 1
	/*END*/
 ) TBL
ORDER BY
	company_name,
    username ASC
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY
