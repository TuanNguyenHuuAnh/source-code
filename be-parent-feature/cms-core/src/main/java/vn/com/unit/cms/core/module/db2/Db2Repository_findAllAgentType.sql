select distinct AGENT_TYPE as id,  AGENT_TYPE|| ' - '||DESCRIPTION as name 
from STG_DMS.DMS_AGENT_TYPE
where channel = 'AG'
    and status = 1