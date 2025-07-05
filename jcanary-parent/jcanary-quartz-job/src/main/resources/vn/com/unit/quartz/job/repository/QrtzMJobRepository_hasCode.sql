SELECT COUNT(*) FROM QRTZ_M_JOB 
WHERE
	VALIDFLAG = 1 
	AND COMPANY_ID = /*companyId*/0 
	AND JOB_CODE = /*code*/''
	/*IF id != null*/
	AND ID <> /*id*/
	/*END*/