select T1.AGENT_CODE
from STG_DMS.DMS_AGENT_DETAIL T1
inner join STG_DMS.DMS_AGENT_TYPE T2
on T1.channel= T2.channel
and  T1.AGENT_TYPE= T2.AGENT_TYPE
where T1.channel = /*channel*/
and T2.STATUS='1'
/*IF channel == 'AG'*/
and T1.AGENT_STATUS='Inforce'
/*END*/
/*IF channel == 'AD'*/
and T1.AGENT_STATUS='Active'
and T1.AGENT_TYPE in ('SM','IL','IO','IS','BANK','NBANK','VNP','ZD','RD','AD')
/*END*/