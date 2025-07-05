SELECT COUNT (1)
FROM
	JCA_ITEM item
WHERE
	item.DELETED_ID = 0
	/*IF jcaItemSearchDto.companyId != null && jcaItemSearchDto.companyId !=0 */
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