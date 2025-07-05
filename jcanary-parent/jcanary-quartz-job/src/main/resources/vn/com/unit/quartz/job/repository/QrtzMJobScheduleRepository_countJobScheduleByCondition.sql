SELECT count(*)
FROM (SELECT ID, JOB_CODE, JOB_GROUP, JOB_NAME_REF, SCHED_CODE, STATUS, START_TIME, NEXT_FIRE_TIME, END_TIME, DESCRIPTION, 
		CREATED_ID, CREATED_DATE, VALIDFLAG, COMPANY_ID, START_DATE 
		FROM QRTZ_M_JOB_SCHEDULE 
		UNION
		SELECT ID, JOB_CODE, JOB_GROUP, JOB_NAME_REF, SCHED_CODE, STATUS, START_TIME, NEXT_FIRE_TIME, END_TIME, DESCRIPTION, 
		CREATED_ID, CREATED_DATE, VALIDFLAG, COMPANY_ID, START_DATE 
		FROM QRTZ_M_JOB_LOG jl 
            WHERE  NOT EXISTS (
                SELECT js.ID 
                FROM QRTZ_M_JOB_SCHEDULE js 
                where js.JOB_NAME_REF = jl.JOB_NAME_REF and js.START_DATE = jl.START_DATE and js.status = jl.status)) js
LEFT JOIN qrtz_m_schedule sm on js.SCHED_CODE = sm.SCHED_CODE and sm.DELETED_ID = 0 and js.company_id = sm.company_id
LEFT JOIN qrtz_m_job jm on js.JOB_CODE = jm.JOB_CODE and jm.DELETED_ID = 0 
left join jca_company company ON  js.COMPANY_ID = company.ID 
WHERE js.validflag <> 6
	AND jm.validflag is not null 
	/*IF qMJobSchedule.companyId != null && qMJobSchedule.companyId != 0*/
	AND js.company_id = /*qMJobSchedule.companyId*/1
	/*END*/
	/*IF qMJobSchedule.companyId == null*/
	AND js.company_id IS NULL
	/*END*/
	/*IF !qMJobSchedule.companyAdmin && qMJobSchedule.companyId == 0*/
	AND (js.company_id  IN /*qMJobSchedule.companyIdList*/()
	OR js.company_id IS NULL)
	/*END*/
	/*IF qMJobSchedule.status != null */
    AND js.status = /*qMJobSchedule.status*/ 
	/*END*/
/*IF qMJobSchedule.schedCode != null && qMJobSchedule.schedCode != ''*/
    AND js.sched_code = /*qMJobSchedule.schedCode*/''
	/*END*/
	/*IF qMJobSchedule.jobCode != null && qMJobSchedule.jobCode != ''*/
	    AND js.job_code = /*qMJobSchedule.jobCode*/''
	/*END*/
	/*IF qMJobSchedule.startDate != null*/ 
		AND js.START_DATE >= /*qMJobSchedule.startDate*/
	/*END*/
	/*IF qMJobSchedule.endDate != null*/ 
		AND js.START_DATE <= /*qMJobSchedule.endDate*/
	/*END*/ 
