select count(*)
from RPT_ODS.F_POLICY_DUE_TO_DATE
where VARCHAR_FORMAT(POL_PAID_TO_DATE, 'MMdd') = VARCHAR_FORMAT(/*date*/, 'MMdd')
AND AGENT_KEY = /*agentCode*/