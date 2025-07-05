DELETE FROM M_EVENTS_APPLICABLE_DETAIL
WHERE EVENT_ID = /*eventId*/
/*IF agentCodes != null && agentCodes.size() > 0*/
AND AGENT_CODE NOT IN /*agentCodes*/()
/*END*/
/*IF idNumbers != null && idNumbers.size() > 0*/
AND ID_NUMBER NOT IN /*idNumbers*/()
/*END*/