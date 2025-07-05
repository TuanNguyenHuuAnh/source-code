SELECT
	pro.ID				      AS ID
	, pro.COMPANY_ID          AS COMPANY_ID
	, pro.PROCESS_CODE		      	  AS CODE
	, pro.PROCESS_NAME   			  AS NAME
	, pro.DESCRIPTION         AS DESCRIPTION
	, pro.ACTIVED	  		  AS IS_ACTIVE
	, pro.CREATED_DATE        AS CREATED_DATE
FROM
	JPM_PROCESS pro
WHERE 
	pro.PROCESS_NAME = /*name*/''
	AND pro.COMPANY_ID = /*companyId*/1
	AND pro.DELETED_ID = 0