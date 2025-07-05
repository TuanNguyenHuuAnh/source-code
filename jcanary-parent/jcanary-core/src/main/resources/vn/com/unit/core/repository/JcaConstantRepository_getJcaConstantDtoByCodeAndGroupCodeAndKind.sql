SELECT	*
FROM JCA_CONSTANT con
WHERE 
	con.GROUP_CODE = /*groupCode*/1
	AND con.KIND = /*kind*/1
	AND con.CODE = /*code*/
	AND UPPER(con.LANG_CODE) = UPPER(/*lang*/)
	