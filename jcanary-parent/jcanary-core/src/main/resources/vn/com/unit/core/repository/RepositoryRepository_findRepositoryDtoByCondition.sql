SELECT
	repository.id 	       	     AS id
	, repository.code            AS code
    , repository.name   	 	 AS name
    , repository.physical_path 	 AS physical_path
    , repository.sub_folder_rule AS sub_folder_rule
    , repository.type_repo       AS type_repo
    , type_repo.name    AS type_repo_name
    , repository.CREATED_DATE     AS CREATED_DATE
    , status_code.code           AS status_code
    , repository.company_id      AS company_id
    , company.name 				AS company_name
    , case when company.name is null then N'ZZZ' else company.name end AS order_name
FROM
	jca_repository repository
LEFT JOIN
    JCA_CONSTANT status_code
ON
    status_code.kind = 'CHECKBOX_ACTIVED'
    AND status_code.code = to_char(repository.actived)
LEFT JOIN
    JCA_CONSTANT type_repo
ON
    type_repo.kind = 'REPO_TYPE'
    AND type_repo.code = to_char(repository.type_repo)
LEFT JOIN 
	jca_company company
ON
	company.id = repository.company_id and company.deleted_by is null
WHERE
	repository.DELETED_ID IS NULL
	/*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND repository.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
	/*IF searchDto.companyId == null*/
	AND repository.COMPANY_ID IS NULL
	/*END*/
	/*IF searchDto.companyId == 0 && !searchDto.companyAdmin*/
	AND (repository.COMPANY_ID  IN /*searchDto.companyIdList*/()
	OR repository.COMPANY_ID IS NULL)
	/*END*/
	/*BEGIN*/
	AND (
	/*IF searchDto.code != null && searchDto.code != ''*/
    UPPER(repository.code) LIKE CONCAT(CONCAT('%',  UPPER(/*searchDto.code*/)''), '%')
    /*END*/
	/*IF searchDto.name != null && searchDto.name != ''*/
	OR UPPER(repository.name) LIKE CONCAT(CONCAT('%',  UPPER(/*searchDto.name*/)''), '%')
	/*END*/
	/*IF searchDto.physicalPath != null && searchDto.physicalPath != ''*/
	OR UPPER(repository.physical_path) LIKE CONCAT(CONCAT('%',  UPPER(/*searchDto.physicalPath*/)''), '%')
	/*END*/
	/*IF searchDto.subFolderRule != null && searchDto.subFolderRule != ''*/
	OR UPPER(repository.sub_folder_rule) LIKE CONCAT(CONCAT('%',  UPPER(/*searchDto.subFolderRule*/)''), '%')
	/*END*/
	)
	/*END*/
	ORDER BY
		order_name,
    	repository.CREATED_DATE DESC
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY