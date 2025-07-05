WITH TBL_TMP AS (
    SELECT DISTINCT
        tbl.id												AS	id
                  , tbl.code												AS	code
                  , tblLang.title											AS	title
                  , item.FUNCTION_NAME									AS	ITEM_FUNCTION_NAME
                  , tbl.SORT												AS	SORT
                  , tbl.ENABLED											AS	enabled
                  , tbl.create_by											AS	create_by
                  , tbl.create_date										AS	create_date
                  , tbl.UPDATE_BY											AS	UPDATE_BY
                  , tbl.UPDATE_DATE										AS	UPDATE_DATE

                  , tmp.name AS  CATEGORY_TYPE

                  , tbl.FOR_CANDIDATE										AS 	FOR_CANDIDATE
                  , (SELECT count(1)
                     FROM M_DOCUMENT tmpTbl
                     WHERE
                         tmpTbl.delete_date is NULL
                       AND tmpTbl.ENABLED = 1
                       AND tmpTbl.M_CATEGORY_ID = tbl.id
                    )
        +
                    (SELECT count(1)
                     FROM M_DOCUMENT_category tmpTbl
                     WHERE
                         tmpTbl.delete_date is NULL
                       AND tmpTbl.ENABLED = 1
                       AND tmpTbl.PARENT_ID = tbl.id
                    )														AS	count_used
    FROM M_DOCUMENT_CATEGORY tbl
             LEFT JOIN M_DOCUMENT_CATEGORY_language tblLang
                       ON tblLang.M_CATEGORY_ID = tbl.id
             LEFT JOIN JCA_ITEM item
                       ON item.FUNCTION_CODE = tbl.ITEM_FUNCTION_CODE
                           AND item.COMPANY_ID = /*searchDto.companyId*/2
             LEFT JOIN JCA_CONSTANT tmp
                       ON tmp.CODE = tbl.CATEGORY_TYPE
                           AND tmp.GROUP_CODE = 'DOCUMENT' AND tmp.LANG_CODE = UPPER(/*searchDto.languageCode*/'VI')
    WHERE
        tbl.delete_date is null
      AND UPPER(tblLang.m_language_code) = UPPER(/*searchDto.languageCode*/'VI')
      /*IF searchDto.channel != null && searchDto.channel != ''*/
	  AND isnull(tbl.CHANNEL, 'AG') = /*searchDto.channel*/
	  /*END*/
)
SELECT *
FROM TBL_TMP tbl
WHERE
        1 = 1
    /*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
  AND
    (
        /*$searchDto.querySearch*/
        )
    /*END*/
/*IF orders != null*/
ORDER BY /*$orders*/tbl.ID
-- ELSE ORDER BY tbl.sort, ISNULL(tbl.update_date, tbl.create_date) DESC
/*END*/

/*BEGIN*/
    /*IF offset != null*/
OFFSET /*offset*/0 ROWS FETCH NEXT  /*size*/10 ROWS ONLY
/*END*/
/*END*/