SELECT 
	acc.ID						AS USER_ID
	,acc.USERNAME				AS USER_NAME
	,org.ID						AS ORG_ID
	,org.NAME					AS ORG_NAME
	,org.CODE					AS ORG_CODE
	,pos.ID						AS POSITION_ID
	,pos.NAME					AS POSITION_NAME
	,acc_org.ACTIVED			AS ACTIVED
	,acc_org.MAIN_FLAG			AS MAIN_FLAG
FROM  
	JCA_ACCOUNT_ORG acc_org
LEFT JOIN
	JCA_ACCOUNT acc
ON
	acc.ID = acc_org.ACCOUNT_ID 
LEFT JOIN
	JCA_POSITION pos
ON
	pos.ID = acc_org.POSITION_ID
LEFT JOIN
	JCA_ORGANIZATION org
ON 
	org.ID = acc_org.ORG_ID	
	
WHERE
	acc.DELETED_ID = 0
	
	/*IF jcaAccountOrgSearchDto.accountId != null && jcaAccountOrgSearchDto.accountId != ''*/
	AND acc.ID = /*jcaAccountOrgSearchDto.accountId*/
	/*END*/
		
	/*IF jcaAccountOrgSearchDto.orgName != null &&  jcaAccountOrgSearchDto.orgName != ''*/
	AND org.NAME LIKE  CONCAT('%', CONCAT(/*jcaAccountOrgSearchDto.orgName*/, '%'))
	/*END*/	
		    
	/*IF jcaAccountOrgSearchDto.positionName != null &&  jcaAccountOrgSearchDto.positionName != ''*/
	AND  pos.NAME  LIKE  CONCAT('%', CONCAT(/*jcaAccountOrgSearchDto.positionName*/, '%'))
	/*END*/	
	
	/*IF jcaAccountOrgSearchDto.actived != null*/
	AND acc_org.ACTIVED = /*jcaAccountOrgSearchDto.actived*/
	/*END*/	

/*IF orders != null*/
ORDER BY /*$orders*/
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/