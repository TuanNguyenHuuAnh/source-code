SELECT 
	bus.ID                	AS BUSINESS_ID
	, bus.BUSINESS_CODE     AS BUSINESS_CODE
	, bus.BUSINESS_NAME     AS BUSINESS_NAME
	, bus.DESCRIPTION       AS DESCRIPTION
	, bus.ACTIVED           AS ACTIVED
	, bus.COMPANY_ID        AS COMPANY_ID
	, bus.PROCESS_TYPE      AS PROCESS_TYPE
	, bus.AUTHORITY         AS AUTHORITY
	, comp.NAME		 		AS COMPANY_NAME
FROM  
	JPM_BUSINESS bus
LEFT JOIN
	JCA_COMPANY comp
ON
	comp.ID = bus.COMPANY_ID
WHERE
	bus.DELETED_ID = 0
	/*IF searchDto.keySearch != null && searchDto.keySearch != ''*/
	/*BEGIN*/
	AND 
		(	
			/*IF searchDto.businessName != null && searchDto.businessName != ''*/
			UPPER(bus.BUSINESS_NAME) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.businessName*/), '%' ))
			/*END*/
			/*IF searchDto.businessCode != null && searchDto.businessCode != ''*/
			OR UPPER(bus.BUSINESS_CODE) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.businessCode*/), '%' ))
			/*END*/
		)
	/*END*/
	/*END*/
		    
	/*IF searchDto.companyId != null*/
	AND bus.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
		    
	/*IF searchDto.processType != null && searchDto.processType != '' */
	AND bus.PROCESS_TYPE = /*searchDto.processType*/
	/*END*/
	
/*IF orders != null*/
ORDER BY /*$orders*/UPDATED_DATE
-- ELSE ORDER BY bus.ID DESC
/*END*/
/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/