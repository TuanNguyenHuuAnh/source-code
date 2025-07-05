SELECT ID 
FROM dbo.JCA_TEAM
WHERE 1=1
/*IF lstGroup != null && !lstGroup.isEmpty() */
AND CODE IN /*lstGroup*/('')
/*END*/
AND COMPANY_ID = /*companyId*/''