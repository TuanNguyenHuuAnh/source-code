SELECT	COUNT(1)
FROM JCA_CONSTANT con
WHERE 
	con.GROUP_CODE = /*groupCode*/1
	AND con.KIND = /*kind*/1
	AND con.CODE = /*code*/
	/*IF langId != null*/
    AND con.LANG_ID = /*langId*/
    /*END*/