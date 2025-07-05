SELECT 
	proLang.LANG_CODE        	AS LANG_CODE
	, proLang.LANG_ID     		AS LANG_ID
	, proLang.PROCESS_NAME     	AS PROCESS_NAME
	, proLang.PROCESS_ID       	AS PROCESS_ID
FROM 
	JPM_PROCESS_LANG proLang
WHERE
	proLang.PROCESS_ID = /*processId*/