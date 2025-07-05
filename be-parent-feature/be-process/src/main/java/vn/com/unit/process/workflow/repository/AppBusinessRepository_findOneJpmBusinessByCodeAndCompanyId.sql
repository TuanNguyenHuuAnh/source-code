SELECT
	bus.ID				      AS ID
	, bus.COMPANY_ID          AS COMPANY_ID
	, bus.CODE		      	  AS CODE
	, bus.NAME   			  AS NAME
	, bus.DESCRIPTION         AS DESCRIPTION
	, bus.IS_ACTIVE	  		  AS IS_ACTIVE
	, bus.CREATED_BY		  AS CREATED_BY
	, bus.CREATED_DATE        AS CREATED_DATE
	, bus.PROCESS_TYPE		  AS PROCESS_TYPE
FROM
	JPM_BUSINESS bus
WHERE 
	bus.CODE = /*code*/''
	
	/*IF companyId != null*/
	AND bus.COMPANY_ID = /*companyId*/1
	/*END*/
	
	/*IF companyId == null*/
	AND bus.COMPANY_ID IS NULL
	/*END*/
	
	AND bus.DELETED_ID = 0