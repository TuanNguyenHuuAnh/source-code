WITH TBL_TMP AS (
    SELECT DISTINCT
        			tbl.id												AS	id
                  , tbl.CODE											AS	CODE
                  , tbl.AGENT_CODE										AS	AGENT_CODE
                  , tbl.AGENT_NAME										AS	AGENT_NAME
                  , tbl.CHANNEL											AS	CHANNEL
                  , tbl.DELIVERY_OFFICE_CODE							AS	OFFICE_CODE
                  , tbl.PHONE											AS  PHONE
                  , d.PRODUCT_CODE										AS  PRODUCT_CODE
                  , prd.PRODUCT_NAME									AS  PRODUCT_NAME
                  , tbl.CREATE_DATE										AS	CREATE_DATE
                  , c.NAME												AS	STATUS_NAME
                  , tbl.CANCEL_DATE										AS	CANCEL_DATE
                  , tbl.CANCEL_BY										AS	CANCEL_BY
                  , tbl.INVOICE_COMPANY_NAME							AS	INVOICE_COMPANY_NAME
                  , tbl.INVOICE_TAX_CODE								AS	INVOICE_TAX_CODE
                  , tbl.INVOICE_COMPANY_ADDRESS							AS	INVOICE_COMPANY_ADDRESS
                  , tbl.TOTAL_AMOUNT									AS	TOTAL_AMOUNT
				  , prd.UNIT_PRICE										AS  UNIT_PRICE
				  , d.QUANTITY											AS  QUANTITY
				  , d.QUANTITY * prd.UNIT_PRICE							AS  AMOUNT
    FROM M_ORDER tbl
    INNER JOIN JCA_CONSTANT c
    ON tbl.STATUS = c.CODE AND c.KIND = 'STATUS' AND GROUP_CODE = 'M_ORDER'
    INNER JOIN M_ORDER_DETAIL d 
    ON d.ORDER_ID = tbl.ID
    INNER JOIN M_PRODUCT prd
    ON d.PRODUCT_CODE = prd.CODE
    WHERE tbl.DELETE_BY is NULL
    AND  dateadd(MONTH, 6, tbl.CREATE_DATE) >= GETDATE()
)
SELECT ROW_NUMBER() OVER (order by t.CODE desc) NO, t.*
FROM TBL_TMP t
WHERE
        1 = 1