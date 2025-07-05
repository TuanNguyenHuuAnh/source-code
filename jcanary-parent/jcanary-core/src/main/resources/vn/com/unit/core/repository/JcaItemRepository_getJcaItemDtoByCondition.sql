--
-- JcaItemRepository_getJcaItemDtoByCondition.sql
SELECT
	 item.ID					AS ITEM_ID
	, item.FUNCTION_CODE		AS FUNCTION_CODE
	, item.FUNCTION_NAME		AS FUNCTION_NAME
	, item.DESCRIPTION			AS DESCRIPTION
	, CONS.NAME                 AS FUNCTION_TYPE
	, item.CREATED_ID			AS CREATED_ID
	, item.CREATED_DATE			AS CREATED_DATE
	, item.UPDATED_ID			AS UPDATED_ID
	, item.UPDATED_DATE			AS UPDATED_DATE
	, item.DELETED_ID			AS DELETED_ID
	, item.DELETED_DATE			AS DELETED_DATE
	, item.DISPLAY_ORDER		AS DISPLAY_ORDER
	, item.ACTIVED				AS ACTIVED
	, com.NAME					AS COMPANY_NAME
FROM JCA_ITEM item
LEFT JOIN jca_company com
    ON com.id= item.company_id
LEFT JOIN JCA_CONSTANT CONS
    ON CONS.GROUP_CODE = 'JCA_ADMIN_ITEM'
    AND CONS.KIND = 'ITEM_TYPE'
    AND CONS.LANG_CODE = 'EN'
    AND CONS.CODE = item.FUNCTION_TYPE
WHERE
	item.DELETED_ID = 0
	
	/*IF jcaItemSearchDto.companyId != null && jcaItemSearchDto.companyId != 0  */
	AND item.COMPANY_ID = /*jcaItemSearchDto.companyId*/1
	/*END*/
	/*IF jcaItemSearchDto.companyId == null*/
	AND item.COMPANY_ID is null
	/*END*/
	/*IF !jcaItemSearchDto.companyAdmin && jcaItemSearchDto.companyId==0*/
 	AND (item.COMPANY_ID IN /*jcaItemSearchDto.companyIdList*/()
 		OR item.COMPANY_ID is null)
 	/*END*/
	
  /*BEGIN*/
  AND (
    /*IF jcaItemSearchDto.functionCode != null && jcaItemSearchDto.functionCode != ''*/
		UPPER(item.FUNCTION_CODE) LIKE CONCAT ('%', CONCAT (UPPER(/*jcaItemSearchDto.functionCode*/) , '%') )
		 /*END*/

		 /*IF jcaItemSearchDto.functionName != null && jcaItemSearchDto.functionName != ''*/
    OR UPPER(item.FUNCTION_NAME) LIKE CONCAT ( '%', CONCAT (UPPER(/*jcaItemSearchDto.functionName*/) , '%' ) )
		/*END*/

		/*IF jcaItemSearchDto.description != null && jcaItemSearchDto.description != ''*/
    OR UPPER(item.DESCRIPTION) LIKE CONCAT ( '%', CONCAT (UPPER(/*jcaItemSearchDto.description*/) , '%' ) )
		/*END*/
  )
  /*END*/

/*IF orders != null*/
ORDER BY /*$orders*/DISPLAY_ORDER ,
	/*$orders*/FUNCTION_NAME
-- ELSE ORDER BY item.DISPLAY_ORDER DESC
/*END*/


/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/