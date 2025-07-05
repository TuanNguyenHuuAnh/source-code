SELECT  
	  tbl.id												AS id
	, tbl.CODE 												AS code
	, tbl.CONTEST_NAME 										AS CONTEST_NAME
  	, tbl.MEMO_NO 											AS MEMO_NO
    , tbl.start_date										AS start_date
    , tbl.end_date											AS end_date
    , tbl.CONTEST_TYPE											AS CONTEST_TYPE
    , tbl.enabled											    as enabled
    , tbl.IS_HOT												AS is_hot
	, tbl.EFFECTIVE_DATE										AS EFFECTIVE_DATE
    , tbl.EXPIRED_DATE											AS EXPIRED_DATE
	 , tbl.created_date 										AS create_date
	 , tbl.created_by											AS create_by
	 , tbl.updated_date 										AS update_date
	 , tbl.updated_by											AS update_by
    , tbl.IS_ODS													as IS_ODS
 	
FROM M_CONTEST_SUMMARY tbl

WHERE
	tbl.deleted_date is null
  	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/'BANNER'
	)
	/*END*/

/*IF orders != null*/
ORDER BY /*$orders*/tbl.ID
-- ELSE ORDER BY ISNULL(tbl.updated_date, tbl.created_date) DESC

/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/