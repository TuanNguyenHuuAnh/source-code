SELECT sla.ID AS id,
	   sla.SLA_NAME AS sla_name,
	   sla.BUSINESS_ID AS business_id,
	   sla.PROCESS_ID AS process_id,
	   sla.CALENDAR_TYPE AS calendar_type,
	   jb.NAME AS business_code,
	   jd.NAME AS process_code
	    , sla.CREATED_BY					AS created_by
	    , sla.CREATED_DATE				AS created_date
	    , sla.UPDATED_BY					AS updated_by
	    , sla.UPDATED_DATE				AS updated_date
	    , sla.DELETED_ID					AS DELETED_ID
	    , sla.DELETED_DATE				AS deleted_date
	    , c.NAME 						AS company_name
FROM APP_SLA_INFO sla
LEFT JOIN JPM_BUSINESS jb on jb.ID = sla.BUSINESS_ID
LEFT JOIN JPM_PROCESS_DEPLOY jd on jd.ID = sla.PROCESS_ID
LEFT JOIN jca_company c on sla.COMPANY_ID = c.ID
WHERE
	sla.DELETED_ID is null
	AND jb.DELETED_ID is null
	AND jd.DELETED_ID is null
	AND c.DELETED_ID is null
	/*IF searchDto.businessCode != null && searchDto.businessCode != ''*/
	AND sla.BUSINESS_ID = /*searchDto.businessCode*/''
	/*END*/
	/*IF searchDto.processCode != null && searchDto.processCode != ''*/
	AND sla.PROCESS_ID = /*searchDto.processCode*/''
	/*END*/
	/*IF searchDto.name != null && searchDto.name != ''*/
	AND sla.SLA_NAME LIKE CONCAT(CONCAT('%',/*searchDto.name*/''),'%')
	/*END*/
	/*IF searchDto.companyIdList != null*/
	AND sla.COMPANY_ID IN /*searchDto.companyIdList*/()
	/*END*/
	ORDER BY sla.CREATED_DATE DESC 
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY