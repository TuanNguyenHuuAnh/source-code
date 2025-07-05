SELECT
	
    /*jcaAuthoritySearchDto.roleId*/            AS ROLE_ID
    ,ITEM.id                                    AS ITEM_ID
    ,AUHTOR_PIVOT.CAN_ACCESS_FLG                AS CAN_ACCESS_FLG
    ,AUHTOR_PIVOT.CAN_DISP_FLG                  AS CAN_DISP_FLG
    ,AUHTOR_PIVOT.CAN_EDIT_FLG                  AS CAN_EDIT_FLG
    ,ITEM.FUNCTION_TYPE                         AS AUTHORITY_TYPE
    ,ITEM.FUNCTION_NAME                         AS FUNCTION_NAME
    ,ITEM.FUNCTION_CODE                         AS FUNCTION_CODE
    ,ITEM.CREATED_ID							AS CREATED_ID
	,ITEM.CREATED_DATE							AS CREATED_DATE
	,ITEM.UPDATED_ID							AS UPDATED_ID
	,ITEM.UPDATED_DATE							AS UPDATED_DATE
	,ITEM.DELETED_ID							AS DELETED_ID
	,ITEM.DELETED_DATE							AS DELETED_DATE
FROM
    JCA_ITEM ITEM
LEFT JOIN
    VW_AUTHORITY_PIVOT AUHTOR_PIVOT
ON
    AUHTOR_PIVOT.ITEM_ID = ITEM.ID
    AND AUHTOR_PIVOT.ROLE_ID = /*jcaAuthoritySearchDto.roleId*/
WHERE
    ITEM.DELETED_ID	IS NULL
    --AND ITEM.DISPLAY_FLAG = '1'
    /*IF jcaAuthoritySearchDto.companyId != null*/
	AND (ITEM.COMPANY_ID is null or ITEM.COMPANY_ID = /*jcaAuthoritySearchDto.companyId*/1)
	/*END*/
	/*IF jcaAuthoritySearchDto.companyId == null*/
	AND ITEM.COMPANY_ID is null
	/*END*/
	AND ITEM.FUNCTION_TYPE in /*jcaAuthoritySearchDto.functionTypes*/()

		    
	/*IF orders != null*/
	ORDER BY /*$orders*/username
	-- ELSE ORDER BY item.UPDATED_DATE DESC
	/*END*/
	
	/*BEGIN*/
	  /*IF offset != null*/
			OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
	  /*END*/
	/*END*/
