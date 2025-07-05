SELECT
		con.CODE                      AS CODE
		,con.GROUP_CODE                AS GROUP_CODE
		,con.DISPLAY_ORDER             AS DISPLAY_ORDER
		,con.NAME					   AS NAME
FROM JCA_CONSTANT con
WHERE 
	con.ACTIVED = 1
  	AND con.KIND = /*kind*/'1'
  	and UPPER(con.LANG_CODE) = UPPER(/*lang*/)