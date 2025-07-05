SELECT
	item.ID,
	item.FUNCTION_CODE,
	item.FUNCTION_NAME,
	item.DESCRIPTION,
	item.FUNCTION_TYPE,
	CASE
	    WHEN item.FUNCTION_TYPE = '3' THEN jbu.NAME
	    ELSE bu.NAME
	END AS business_code_display,
	bu_co.SYSTEM_CODE 	AS business_company_name,
	item.BUSINESS_CODE,
	item.SUB_TYPE,
	item.CREATED_BY,
	item.CREATED_DATE,
	item.UPDATED_BY,
	item.UPDATED_DATE,
	item.DELETED_BY,
	item.DELETED_DATE,
	item.MENU_TYPE,
	item.DISPLAY_ORDER,
	--item.DISPLAY_FLAG
	, co.id 			  	 AS company_id
	, co.name 			  	 AS company_name,
	case when co.name is null then N'ZZZ' else co.name end AS order_name
FROM
	JCA_ITEM item
LEFT JOIN
	jca_company co ON item.company_id = co.id and co.DELETED_ID = 0
LEFT JOIN
	JPR_BUSINESSDEFINITION bu ON bu.CODE = item.BUSINESS_CODE
LEFT JOIN
	JPM_BUSINESS jbu ON jbu.id = item.BUSINESS_ID
	AND jbu.DELETED_ID = 0
LEFT JOIN
	jca_company bu_co ON bu.COMPANY_ID = bu_co.ID
	AND bu_co.DELETED_ID = 0
WHERE
	item.DELETED_ID = 0
	/*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND item.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*IF searchDto.companyId == null*/
	AND item.COMPANY_ID IS NULL
	/*END*/
	/*IF searchDto.companyId == 0 && !searchDto.companyAdmin*/
	AND (item.COMPANY_ID  IN /*searchDto.companyIdList*/()
		OR item.COMPANY_ID IS NULL)
	/*END*/
  /*BEGIN*/
  AND (
    /*IF searchDto.functionCode != null && searchDto.functionCode != ''*/
		UPPER(item.FUNCTION_CODE) LIKE CONCAT ('%', CONCAT (UPPER(/*searchDto.functionCode*/) , '%') )
		 /*END*/

		 /*IF searchDto.functionName != null && searchDto.functionName != ''*/
    OR UPPER(item.FUNCTION_NAME) LIKE CONCAT ( '%', CONCAT (UPPER(/*searchDto.functionName*/) , '%' ) )
		/*END*/

		/*IF searchDto.description != null && searchDto.description != ''*/
    OR UPPER(item.DESCRIPTION) LIKE CONCAT ( '%', CONCAT (UPPER(/*searchDto.description*/) , '%' ) )
		/*END*/

    /*IF searchDto.businessCode != null && searchDto.businessCode != ''*/
    AND (bu.CODE LIKE CONCAT('%' , CONCAT (/*searchDto.businessCode*/ , '%'))
    	OR jbu.CODE LIKE CONCAT('%' , CONCAT (/*searchDto.businessCode*/ , '%')))
    /*END*/
  )
  /*END*/
ORDER BY
	order_name,
	item.DISPLAY_ORDER,
	item.FUNCTION_NAME
OFFSET /*offSet*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY;
