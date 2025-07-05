SELECT count(1)
FROM   RPT_ODS.D_CURRENT_AGENT_HIERARCHY   A 
WHERE  LOCATE(/*parentCode*/'119720', A.TREE_PATH) > 0
and (cast(A.AGENT_KEY as varchar) = /*childrenCode*/'1137221'
	or A.LV1_AGENTCODE=/*childrenCode*/'1137221' 
	or A.LV2_AGENTCODE=/*childrenCode*/'1137221'
	or A.LV3_AGENTCODE=/*childrenCode*/'1137221')