select count(1)
from M_EVENTS_APPLICABLE_DETAIL A
where 1=1
and A.EVENT_ID = /*search.eventId*/'148'
/*IF search.search != null && search.search != ''*/
/*$search.search*/''
/*END*/