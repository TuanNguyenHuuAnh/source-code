WITH TBL_TMP AS (
    SELECT DISTINCT
        			tbl.id												AS	id
                  , tbl.CODE											AS	CODE
                  , tbl.PRODUCT_NAME									AS	PRODUCT_NAME
                  , c.NAME												AS	PRODUCT_TYPE
                  , tbl.UNIT_PRICE										AS  UNIT_PRICE
                  , tbl.EFFECTIVE_DATE									AS	EFFECTIVE_DATE
                  , tbl.EXPIRED_DATE									AS	EXPIRED_DATE
                  , tbl.PRODUCT_IMG										AS	PRODUCT_IMG
                  , tbl.PRODUCT_PHYSICAL_IMG							AS	PRODUCT_PHYSICAL_IMG
                  , tbl.create_date										AS	create_date
                  , tbl.create_by										AS	create_by
                  , tbl.UPDATE_BY										AS	UPDATE_BY
                  , tbl.UPDATE_DATE										AS	UPDATE_DATE
                  , '999' AS STATUS_CODE
    FROM M_PRODUCT tbl
    left join JCA_CONSTANT c
    on tbl.PRODUCT_TYPE = c.CODE and c.GROUP_CODE='M_PRODUCT'
    WHERE tbl.DELETE_BY is NULL
)
SELECT *
FROM TBL_TMP tbl
WHERE
        1 = 1
    	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
		AND
		(
			/*$searchDto.querySearch*/'BANNER'
		)
		/*END*/
/*IF orders != null*/
ORDER BY /*$orders*/tbl.ID
-- ELSE ORDER BY ISNULL(tbl.update_date, tbl.create_date) DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/