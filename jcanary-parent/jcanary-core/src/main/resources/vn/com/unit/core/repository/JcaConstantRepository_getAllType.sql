SELECT DISTINCT
	CD.KIND as ID,
	CD.KIND as TEXT,
	CD.KIND as NAME
FROM
	JCA_CONSTANT CD
/*BEGIN*/
WHERE 
	/*IF null != lang && lang != ''*/
		CD.LANG_CODE = /*lang*/
	/*END*/
/*END*/