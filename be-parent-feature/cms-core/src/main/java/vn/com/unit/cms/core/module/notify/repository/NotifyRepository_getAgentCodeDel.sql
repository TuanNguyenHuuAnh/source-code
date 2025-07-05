SELECT case when A.GUEST_TYPE = '1' then A.AGENT_CODE else A.ID_NUMBER end
FROM M_EVENTS_APPLICABLE_DETAIL A
WHERE A.EVENT_ID = /*eventId*/182
and A.GUEST_TYPE in ('1','3')
and not exists 
(
	select 1
	from M_EVENTS_IMPORT B
	where B.SESSION_KEY = /*sessionKey*/''
	and B.AGENT_CODE =  A.AGENT_CODE
)