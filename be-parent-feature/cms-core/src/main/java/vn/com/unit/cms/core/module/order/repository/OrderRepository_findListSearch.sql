WITH TBL_TMP AS (
    SELECT DISTINCT
        			tbl.id												AS	id
                  , tbl.CODE											AS	CODE
                  , tbl.AGENT_CODE										AS	AGENT_CODE
                  , tbl.AGENT_NAME										AS	AGENT_NAME
                  , tbl.DELIVERY_OFFICE_CODE							AS	OFFICE_CODE
                  , tbl.PHONE											AS  PHONE
                  , tbl.CREATE_DATE										AS	CREATE_DATE
                  , tbl.STATUS 											AS  STATUS_ORDER
                  , c.NAME												AS	STATUS_NAME
                  , tbl.CANCEL_DATE										AS	CANCEL_DATE
                  , tbl.CANCEL_BY										AS	CANCEL_BY
                  , tbl.INVOICE_COMPANY_NAME							AS	INVOICE_COMPANY_NAME
                  , tbl.INVOICE_TAX_CODE								AS	INVOICE_TAX_CODE
                  , tbl.INVOICE_COMPANY_ADDRESS							AS	INVOICE_COMPANY_ADDRESS
                  , tbl.TOTAL_AMOUNT									AS	TOTAL_AMOUNT
    FROM M_ORDER tbl
    INNER JOIN JCA_CONSTANT c
    ON tbl.STATUS = c.CODE AND c.KIND = 'STATUS' AND GROUP_CODE = 'M_ORDER'
    WHERE tbl.DELETE_BY is NULL
    AND  dateadd(MONTH, 6, tbl.CREATE_DATE) >= GETDATE()
)
SELECT *
FROM TBL_TMP tbl
WHERE
        1 = 1
    	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
		AND
		(
			/*$searchDto.querySearch*/'ORDER'
		)
		/*END*/
/*IF orders != null*/
ORDER BY /*$orders*/tbl.ID
-- ELSE ORDER BY tbl.create_date DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/