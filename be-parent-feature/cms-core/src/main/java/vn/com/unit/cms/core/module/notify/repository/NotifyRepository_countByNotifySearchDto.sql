WITH TBL_DATA AS (
	SELECT
		tbl.id												AS	id
	  	, tbl.notify_code									AS	notify_code
	  	, tbl.notify_title									AS	notify_title
	  	, tbl.is_active										AS	is_active
	  	, tbl.is_send										AS 	is_send
	  	, tbl.create_date									AS  create_date
	  	, tbl.create_by										AS	create_by
	  	, tbl.update_by										AS	update_by
	  	, tbl.update_date									AS	update_date
	FROM M_NOTIFYS tbl
	WHERE
		tbl.delete_date is null
)
SELECT COUNT(*)
FROM TBL_DATA TBL
WHERE
	1 = 1
	
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/
	)
	/*END*/