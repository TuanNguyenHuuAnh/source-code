select distinct OFFICE_CODE
from RPT_ODS.D_CURRENT_AGENT_HIERARCHY 
where (BDOH_CODE = /*agentCode*/'154293'
or BDRH_CODE = /*agentCode*/'154293'
or BDAH_CODE = /*agentCode*/'154293'
or BDTH_CODE = /*agentCode*/'154293')
