select trigger_name, trigger_group, job_name, job_group
from qrtz_triggers
where job_name = /*jobName*/''