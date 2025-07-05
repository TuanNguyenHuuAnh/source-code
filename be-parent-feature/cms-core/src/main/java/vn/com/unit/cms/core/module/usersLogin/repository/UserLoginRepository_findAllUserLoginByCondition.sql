SELECT ID
	  ,USER_ID
      ,USERNAME
      ,LOGIN_TYPE
      ,LOGIN_STATUS
      ,LOGIN_DATE
      ,LOGOUT_DATE
      ,CLIENT_IP
      ,BROWSER
      ,OPERATING_SYSTEM AS OS
      ,DEVICE
      ,CHANNEL
  FROM M_USER_LOGIN
  WHERE
	1 = 1
	/*IF searchDto.startDate == null && searchDto.endDate == null*/
	AND LOGIN_DATE >= DATEADD(DAY, -1, GETDATE())
	/*END*/
	/*IF searchDto.startDate != null*/
	 AND LOGIN_DATE >= /*searchDto.startDate*/
	 /*END*/
	 /*IF searchDto.endDate != null*/
	 AND LOGIN_DATE <= /*searchDto.endDate*/
	 /*END*/
	/*BEGIN*/
	AND (
		/*IF searchDto.userName != null && searchDto.userName != ''*/
		OR UPPER(RTRIM(LTRIM(USERNAME))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*searchDto.userName*/))), '%' ))
		/*END*/

		/*IF searchDto.loginType != null && searchDto.loginType != ''*/
		OR UPPER(RTRIM(LTRIM(LOGIN_TYPE))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*searchDto.loginType*/))), '%' ))
		/*END*/
		
		/*IF searchDto.status != null && searchDto.status != ''*/
		OR UPPER(RTRIM(LTRIM(LOGIN_STATUS))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*searchDto.status*/))), '%' ))
		/*END*/
		
		/*IF searchDto.device != null && searchDto.device != ''*/
		OR UPPER(RTRIM(LTRIM(DEVICE))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*searchDto.device*/))), '%' ))
		/*END*/

		/*IF searchDto.os != null && searchDto.os != ''*/
		OR UPPER(RTRIM(LTRIM(OPERATING_SYSTEM))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*searchDto.os*/))), '%' ))
		/*END*/
		
		/*IF searchDto.browser != null && searchDto.browser != ''*/
		OR UPPER(RTRIM(LTRIM(BROWSER))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*searchDto.browser*/))), '%' ))
		/*END*/
	)
	/*END*/
	ORDER BY ID DESC
    OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY