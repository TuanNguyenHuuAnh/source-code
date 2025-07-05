SELECT
    count(*)
FROM M_ECARD tbl
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