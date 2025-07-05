select group_code
from qrtz_m_job_store
group by group_code
order by min(exec_order) asc