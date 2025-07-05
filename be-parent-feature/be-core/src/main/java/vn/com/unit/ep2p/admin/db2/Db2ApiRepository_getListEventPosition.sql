select distinct AGENT_TYPE as id, DESCRIPTION as name
from STG_DMS.DMS_AGENT_TYPE
where channel = 'AG'
and group_type = 'AGENT'