
WITH TMP_DATA AS(
SELECT  
	  tbl.id												AS id
	, tbl.CODE 										AS code
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
	  , CASE WHEN tbl.APPLICABLE_OBJECT = 'ALL' THEN '1'
	         WHEN tbl.APPLICABLE_OBJECT = 'IMP' THEN '2'
	         ELSE '3' END AS APPLICABLE_OBJECT
	  ,tbl.AGENT_CODE                    AS AGENT_CODE
	  ,tbl.TERRITORY					 AS TERRITORY
	  ,tbl.area							 AS AREA
	  ,tbl.REGION						 AS REGION
	  ,tbl.OFFICE						 AS OFFICE 
	  ,tbl.POSITION						 AS POSITION
	  ,tbl.REPORTINGTO_CODE				 AS REPORTINGTO_CODE
FROM M_CONTEST_SUMMARY tbl
WHERE tbl.DELETED_DATE is null
)
SELECT count(*) FROM TMP_DATA tbl
WHERE 1 =1 

	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/'BANNER'
	)
	/*END*/
	/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/