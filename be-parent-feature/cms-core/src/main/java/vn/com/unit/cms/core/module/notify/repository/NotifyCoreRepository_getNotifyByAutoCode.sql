select 
    *
from M_NOTIFYS notify
where 
    1=1
    and delete_date IS NULL
    and notify.NOTIFY_CODE = /*notifyCode*/'NOT9999.0011'