SELECT
    count(*)
FROM M_CONTEST_SUMMARY
WHERE
    deleted_date is null
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