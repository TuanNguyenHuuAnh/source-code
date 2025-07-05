SELECT *
FROM APP_SLA_INFO sla
WHERE
	sla.DELETED_ID is null 
	AND sla.COMPANY_ID = /*companyId*/0
	AND sla.PROCESS_ID = /*processId*/0
	ORDER BY sla.ID ASC