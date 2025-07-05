select count(1)
from M_EVENTS
where ID = /*id*/'100'
and PROCESSING_FLG = '1'
and DEL_FLG = 0