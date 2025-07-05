INSERT INTO ACT_ID_MEMBERSHIP(USER_ID_, GROUP_ID_)
SELECT detail.ACCOUNT_ID, detail.FUNCTION_CODE
FROM VW_GET_AUTHORITY_ACCOUNT detail
INNER JOIN JPM_PERMISSION_DEPLOY function_
	 ON detail.FUNCTION_CODE = function_.DEPLOY_CODE
     AND (function_.PERMISSION_TYPE IS NULL OR function_.PERMISSION_TYPE = 'GROUP')
WHERE detail.ACCESS_FLG = '0'
      AND detail.FUNCTION_TYPE = '3'
      /*IF userIds != null*/
      AND detail.ACCOUNT_ID IN /*userIds*/()
      /*END*/
      /*IF groupIds != null*/
	  AND detail.FUNCTION_CODE IN /*groupIds*/()
	  /*END*/