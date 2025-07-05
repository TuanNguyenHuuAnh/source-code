SELECT
	VAD.GRANTED_TYPE AS GRANTED_TYPE
	,VAD.USERNAME AS USERNAME
	,VAD.FULLNAME AS FULLNAME
	,VAD.EMAIL AS EMAIL
    ,CASE WHEN VAD.ENABLED = 1 THEN /*constEnable*/'Enable' ELSE /*constDisable*/'Disable' END AS ENABLED
	,CASE WHEN VAD.ACTIVED = 1 THEN /*constActivated*/'Activated' ELSE /*constInactive*/'Inactive' END AS ACTIVED
	,VAD.ORG_CODE AS ORG_CODE
	,VAD.ORG_NAME AS ORG_NAME
	,VAD.POSITION_CODE AS POSITION_CODE
	,VAD.POSITION_NAME AS POSITION_NAME
	,VAD.GROUP_CODE AS GROUP_CODE
	,VAD.GROUP_NAME AS GROUP_NAME
	,VAD.ROLE_CODE AS ROLE_CODE
	,VAD.ROLE_NAME AS ROLE_NAME
	,CASE WHEN VAD.ROLE_ACTIVED = 1 THEN /*constActivated*/'Activated' ELSE /*constInactive*/'Inactive' END AS ROLE_ACTIVED
	,VAD.FUNCTION_CODE AS FUNCTION_CODE
	,VAD.FUNCTION_NAME AS FUNCTION_NAME
	,VAD.ACCESS_RIGHT AS ACCESS_RIGHT
FROM 
	VW_AUTHORITY_DETAIL VAD
WHERE 0 = 0
/*BEGIN*/
	AND (
		/*IF authorityDetailDto.username != null*/
			OR UPPER(USERNAME) LIKE UPPER( '%' || /*authorityDetailDto.username*/ || '%' )
		/*END*/
		/*IF authorityDetailDto.fullname != null*/
			OR UPPER(FULLNAME) LIKE UPPER( '%' || /*authorityDetailDto.fullname*/ || '%' )
		/*END*/
		/*IF authorityDetailDto.email != null*/
			OR UPPER(EMAIL) LIKE UPPER( '%' || /*authorityDetailDto.email*/ || '%' )
		/*END*/
		/*IF authorityDetailDto.groupCode != null*/
			OR UPPER(GROUP_CODE) LIKE UPPER( '%' || /*authorityDetailDto.groupCode*/ || '%' )
			OR UPPER(GROUP_NAME) LIKE UPPER( '%' || /*authorityDetailDto.groupCode*/ || '%' )
		/*END*/
		/*IF authorityDetailDto.roleCode != null*/
			OR UPPER(ROLE_CODE) LIKE UPPER( '%' || /*authorityDetailDto.roleCode*/ || '%' )
			OR UPPER(ROLE_NAME) LIKE UPPER( '%' || /*authorityDetailDto.roleCode*/ || '%' )
		/*END*/
		/*IF authorityDetailDto.functionCode != null*/
			OR UPPER(FUNCTION_CODE) LIKE UPPER( '%' || /*authorityDetailDto.functionCode*/ || '%' )
			OR UPPER(FUNCTION_NAME) LIKE UPPER( '%' || /*authorityDetailDto.functionCode*/ || '%' )
		/*END*/
	)
/*END*/
	
/*BEGIN*/
	AND (
		/*IF authorityDetailDto.companyId != null*/
			OR COMPANY_ID = /*authorityDetailDto.companyId*/1
		/*END*/
	)
/*END*/
ORDER BY GROUP_CODE, USERNAME, FUNCTION_NAME, ACCESS_RIGHT
OFFSET /*authorityDetailDto.currentPage*/ * /*authorityDetailDto.sizePage*/ ROWS 
FETCH NEXT /*authorityDetailDto.sizePage*/ ROWS ONLY