select count(1)
from STG_DMS.DMS_AGENT_DETAIL a
inner join STG_DMS.DMS_CLIENT_BANK c on a.CLIENT_CODE = c.CLIENT_CODE
where a.AGENT_CODE <> /*agentCode*/'117781'
and c.BANK_ACCOUNT_NUMBER = /*bankAccountNumber*/'11624742455012'