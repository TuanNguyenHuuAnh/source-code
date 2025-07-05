SELECT	
		con.GROUP_ID			AS GROUP_CONSTANT_ID
		,con.GROUP_CODE					AS GROUP_CODE
		,con.KIND				AS KIND
		,con.CODE                       AS CODE
		,con.DISPLAY_ORDER              AS DISPLAY_ORDER
		,con.LANG_CODE            		AS LANG_CODE
		
FROM JCA_CONSTANT con
WHERE 
    con.ACTIVED = 1
	AND con.ID = /*id*/1