SELECT  
	ROW_NUMBER ()  OVER ( ORDER BY dem.ID ASC )   as no
	, dem.ID											AS ID
	, dem.MEMO_NO									AS MEMO_NO
	, dem.AGENT_CODE							AS AGENT_CODE
	, dem.REPORTINGTO_CODE				AS REPORTINGTO_CODE
	, dem.BM_CODE									AS BM_CODE
	, dem.GAD_CODE								AS GAD_CODE
	, dem.GA_CODE									AS GA_CODE
	, dem.BDOH_CODE								AS BDOH_CODE
	, dem.BDRH_CODE								AS BDRH_CODE
	, dem.BDAH_CODE								AS BDAH_CODE
	, dem.BDTH_CDE								AS BDTH_CDE
	, dem.POLICY_NO								AS POLICY_NO
	, dem.APPLIED_DATE						AS APPLIED_DATE
	, dem.ISSUED_DATE							AS ISSUED_DATE
	, dem.RESULT									AS RESULT
	, dem.ADVANCE									AS ADVANCE
	, dem.BONUS										AS BONUS
	, dem.CLAWBACK								AS CLAWBACK
FROM M_CONTEST_DETAIL dem

WHERE
	dem.DELETED_DATE is null
	AND MEMO_NO = /*searchDto.memoNo*/'A1'
	


