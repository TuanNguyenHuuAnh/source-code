SELECT COUNT(*) FROM QRTZ_M_SCHEDULE 
WHERE
	VALIDFLAG = 1 
	AND COMPANY_ID = /*companyId*/0 
	AND SCHED_CODE = /*code*/'' 
	/*IF id != null*/
	AND ID <> /*id*/
	/*END*/