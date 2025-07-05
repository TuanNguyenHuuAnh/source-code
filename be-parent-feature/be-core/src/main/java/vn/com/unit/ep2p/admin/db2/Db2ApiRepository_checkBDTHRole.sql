SELECT COUNT(*)
FROM (
		SELECT AGENT_CODE,  
	   		   ROLENAME,  
	           ROW_NUMBER () OVER (PARTITION BY AGENT_CODE ORDER BY IDX)  
               AS ORDERBY  
               FROM (
	                  SELECT DISTINCT BDTH_CODE AS AGENT_CODE,
	                  1 AS IDX, 'BDTH' AS ROLENAME  
	                  FROM RPT_ODS.D_CURRENT_ORG_HIERARCHY WHERE NVL(ND_CODE,'') <>'' 
         	   )
) 
WHERE AGENT_CODE = /*agentCode*/;  