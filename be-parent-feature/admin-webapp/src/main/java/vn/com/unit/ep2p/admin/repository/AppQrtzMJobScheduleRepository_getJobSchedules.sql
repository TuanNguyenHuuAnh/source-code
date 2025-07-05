SELECT
    sm.SCHED_NAME,
    jm.JOB_NAME,
    js.ID,
    js.JOB_ID,
    js.SCHED_ID,
    js.STATUS,
    js.START_TIME as START_TIME,
    js.NEXT_FIRE_TIME as NEXT_FIRE_TIME,
    js.END_TIME as END_TIME,
    js.DESCRIPTION,
    js.CREATED_ID,
    js.CREATED_DATE,
    js.VALIDFLAG,
    js.ERROR_MESSAGE
    , company.name	AS COMPANY_NAME 
FROM (SELECT ID, JOB_ID, JOB_GROUP, JOB_NAME_REF, SCHED_ID, STATUS, START_TIME, NEXT_FIRE_TIME, END_TIME, DESCRIPTION, 
		CREATED_ID, CREATED_DATE, VALIDFLAG, COMPANY_ID, START_DATE, ERROR_MESSAGE
		FROM QRTZ_M_JOB_SCHEDULE 
		UNION
		SELECT ID, JOB_ID, JOB_GROUP, JOB_NAME_REF, SCHED_ID, STATUS, START_TIME, NEXT_FIRE_TIME, END_TIME, DESCRIPTION, 
		CREATED_ID, CREATED_DATE, VALIDFLAG, COMPANY_ID, START_DATE, ERROR_MESSAGE
		FROM QRTZ_M_JOB_LOG jl 
            WHERE  NOT EXISTS (
                SELECT js.ID 
                FROM QRTZ_M_JOB_SCHEDULE js 
                where js.JOB_NAME_REF = jl.JOB_NAME_REF and js.START_DATE = jl.START_DATE and js.status = jl.status)) js
LEFT JOIN qrtz_m_schedule sm on js.SCHED_ID = sm.ID and sm.DELETED_ID = 0 and js.company_id = sm.company_id
LEFT JOIN qrtz_m_job jm on js.JOB_ID = jm.ID and jm.DELETED_ID = 0 
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
	/*IF qMJobSchedule.statusList != null && qMJobSchedule.statusList.size() > 0*/
    AND js.status IN /*qMJobSchedule.statusList*/()
	/*END*/
/*IF qMJobSchedule.schedId != null && qMJobSchedule.schedId != ''*/
    AND js.SCHED_ID = /*qMJobSchedule.schedId*/''
	/*END*/
	/*IF qMJobSchedule.jobId != null && qMJobSchedule.jobId != ''*/
	    AND js.JOB_ID = /*qMJobSchedule.jobId*/''
	/*END*/
	/*IF qMJobSchedule.startDate != null*/ 
		AND js.START_DATE >= /*qMJobSchedule.startDate*/
	/*END*/
	/*IF qMJobSchedule.endDate != null*/ 
		AND js.START_DATE <= /*qMJobSchedule.endDate*/
	/*END*/ 
ORDER BY js.status desc, js.START_TIME desc, js.id desc 
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY