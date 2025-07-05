SELECT
      COUNT(*)
FROM JCA_COMPANY COMPANY
WHERE COMPANY.DELETED_ID = 0
/*BEGIN*/
	AND (
		/*IF companySearchDto.name != null && companySearchDto.name != ''*/
			 UPPER(COMPANY.NAME) LIKE concat(concat('%',  UPPER(/*companySearchDto.name*/'')), '%')
		/*END*/
		/*IF companySearchDto.description != null && companySearchDto.description != ''*/
			OR UPPER(COMPANY.DESCRIPTION) LIKE concat(concat('%',  UPPER(/*companySearchDto.description*/'')), '%')
		/*END*/
		/*IF companySearchDto.systemCode != null && companySearchDto.systemCode != ''*/
			OR UPPER(COMPANY.SYSTEM_CODE) LIKE concat(concat('%',  UPPER(/*companySearchDto.systemCode*/'')), '%')
		/*END*/
		/*IF companySearchDto.systemName != null && companySearchDto.systemName != ''*/
			OR UPPER(COMPANY.SYSTEM_NAME) LIKE concat(concat('%',  UPPER(/*companySearchDto.systemName*/'')), '%')
		/*END*/
	)
/*END*/
;