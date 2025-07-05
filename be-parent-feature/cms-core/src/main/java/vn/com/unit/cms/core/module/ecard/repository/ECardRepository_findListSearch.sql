SELECT 
	tbl.id													AS id
	, tbl.code 												AS code
	, tbl.title 											AS title
    , tbl.enabled											as enabled
    , tbl.create_date 										AS create_date
    , tbl.create_by											AS create_by
    , tbl.update_date 										AS update_date
    , tbl.update_by											AS update_by
FROM M_ECARD tbl WITH (NOLOCK)
WHERE
	tbl.delete_date is null
	/*IF searchDto.channel == null || searchDto.channel == ''*/
	AND isnull(tbl.CHANNEL, 'AG') = 'AG'
	/*END*/
	/*IF searchDto.channel != null && searchDto.channel == 'AG'*/
	AND isnull(tbl.CHANNEL, 'AG') = /*searchDto.channel*/
	/*END*/
	/*IF searchDto.channel != null && searchDto.channel == 'AD'*/
	AND tbl.CHANNEL = /*searchDto.channel*/
	/*END*/
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/
	)
	/*END*/
/*IF orders != null*/
ORDER BY /*$orders*/tbl.ID
-- ELSE ORDER BY tbl.sort ASC, ISNULL(tbl.update_date, tbl.create_date) DESC, tbl.ENABLED DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/