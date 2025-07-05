SELECT
    tbl.id												AS	id
     , tbl.notify_code									AS	notify_code
     , tbl.notify_title									AS	notify_title
     , tbl.contents										AS	contents
     , tbl.is_active										AS	is_active
     , tbl.is_send										AS 	is_send
     , tbl.create_date									AS  create_date
     , tbl.create_by										AS	create_by
     , tbl.update_by										AS	update_by
     , tbl.update_date									AS	update_date
     ,tbl.send_date										as send_date
FROM M_NOTIFYS tbl
WHERE DELETE_DATE is null
  AND IS_SEND = 0
  AND SAVE_DETAIL = 0
  and  SEND_DATE <= /*sendDate*/'2022-10-01 10:37'