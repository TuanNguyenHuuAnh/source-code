SELECT *
FROM SAM_ROLE_PERMISSION
WHERE 1 = 1
/*IF agentTypes != null && agentTypes.size() > 0*/ 
	AND AGENT_TYPE IN /*agentTypes*/()
/*END*/