select A.AGENT_CODE
from M_EVENTS_IMPORT A
where A.SESSION_KEY = /*sessionKey*/''
and (A.MESSAGE_WARNING is null or A.MESSAGE_WARNING NOT LIKE '%F009.W003%')
and not exists
(
	select AGENT_CODE
	from M_EVENTS_APPLICABLE_DETAIL B
	where B.EVENT_ID = /*eventId*/182
	and ((B.GUEST_TYPE = '1' and B.AGENT_CODE = A.AGENT_CODE)
	or (B.GUEST_TYPE = '3' and B.ID_NUMBER = A.AGENT_CODE))
)