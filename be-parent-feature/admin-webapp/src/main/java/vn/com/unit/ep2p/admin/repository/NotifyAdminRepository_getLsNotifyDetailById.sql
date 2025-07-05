select 
      notify.ID                     AS ID
    , notify.NOTIFY_CODE            AS NOTIFY_CODE
	,notifyDetail.AGENT_CODE        AS AGENT_CODE
from M_NOTIFYS notify
inner join M_NOTIFYS_APPLICABLE_DETAIL notifyDetail
    on notify.id = notifyDetail.NOTIFY_ID
WHERE 1 = 1
AND DELETE_DATE IS NULL
and notify.id= /*id*/'131'