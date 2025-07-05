select * 
from qrtz_m_job_store
where group_code = /*groupCode*/'' and validflag <> 6
order by exec_order asc
