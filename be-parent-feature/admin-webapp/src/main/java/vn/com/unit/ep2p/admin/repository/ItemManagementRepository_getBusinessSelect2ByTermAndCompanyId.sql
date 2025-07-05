/*IF companyId != null && companyId ==0L*/
	SELECT 
		bu.CODE         AS id,
		bu.NAME         AS name,
		bu.NAME         AS text
	FROM 
		JPR_BUSINESSDEFINITION bu				
	WHERE 1 = 1
	/*IF !companyAdmin*/
		AND (bu.company_id in /*companyIds*/() OR bu.company_id IS NULL)
	/*END*/
	/*IF term != null && term != ''*/
		AND UPPER(bu.NAME) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')), '%')
	/*END*/
UNION
	SELECT 
		jbu.CODE           AS id,
		jbu.NAME         AS name,
		jbu.NAME         AS text
	FROM 
		JPM_BUSINESS jbu				
	WHERE jbu.DELETED_ID = 0
	/*IF !companyAdmin*/
		AND (jbu.company_id in /*companyIds*/() OR jbu.company_id IS NULL)
	/*END*/
	/*IF term != null && term != ''*/
		AND UPPER(jbu.NAME) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')), '%')
	/*END*/
	ORDER BY NAME
/*END*/
/*IF companyId == null || companyId !=0L*/
		SELECT 
		bu.CODE         AS id,
		bu.NAME         AS name,
		bu.NAME         AS text
	FROM 
		JPR_BUSINESSDEFINITION bu				
	WHERE 1 = 1
	/*IF companyId != null*/
		AND bu.company_id = /*companyId*/1
	/*END*/
	/*IF companyId == null*/
		AND bu.company_id is null
	/*END*/
	/*IF term != null && term != ''*/
		AND UPPER(bu.NAME) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')), '%')
	/*END*/
UNION
	SELECT 
		jbu.CODE         AS id,
		jbu.NAME         AS name,
		jbu.NAME         AS text
	FROM 
		JPM_BUSINESS jbu				
	WHERE jbu.DELETED_ID = 0
	/*IF companyId != null*/
		AND jbu.company_id = /*companyId*/1
	/*END*/
	/*IF companyId == null*/
		AND jbu.company_id is null
	/*END*/
	/*IF term != null && term != ''*/
		AND UPPER(jbu.NAME) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')), '%')
	/*END*/
	ORDER BY NAME
/*END*/