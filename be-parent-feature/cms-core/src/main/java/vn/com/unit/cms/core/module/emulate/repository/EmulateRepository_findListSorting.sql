SELECT 
		tbl.id													AS id
	, tbl.code 												AS code
	, tbl.CONTEST_NAME 										AS CONTEST_NAME
    , tbl.MEMO_NO										AS memo_no
    , tbl.enabled											as enabled
    , tbl.start_date										AS start_date
    , tbl.end_date											AS end_date
    , tbl.IS_HOT												AS IS_HOT
    , tbl.created_date 										AS created_date
    , tbl.created_by											AS created_by
    , tbl.updated_date 										AS updated_date
    , tbl.updated_by											AS updated_by
FROM M_CONTEST_SUMMARY tbl

WHERE
	tbl.deleted_date is null
	AND ENABLED = '1'
  AND
    (
            tbl.EXPIRED_DATE IS NULL
            OR tbl.EXPIRED_DATE >= CONVERT(DATE, GETDATE() ,103)
        )
	AND tbl.CONTEST_TYPE =  /*searchDto.contestType*/''
ORDER BY tbl.SORT ASC