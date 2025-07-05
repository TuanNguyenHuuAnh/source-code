select count(*)
from STG_DMS.DMS_AA_GA_OFFICE a
where a.GAD_CODE = /*agentCode*/'123567'
and INACTIVE = '0'
