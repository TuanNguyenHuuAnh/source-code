SELECT
	repository.id 	       	     AS id
	, repository.code            AS code
    , repository.name   	 	 AS name
    , repository.physical_path 	 AS physical_path
    , repository.sub_folder_rule AS sub_folder_rule
    , repository.type_repo       AS type_repo
    , type_repo.cat_abbr_name    AS type_repo_name
    , repository.CREATED_DATE     AS CREATED_DATE
    , status_code.code           AS status_code
FROM
	jca_m_repository repository
LEFT JOIN
    jca_constant_display status_code
ON
    status_code.type = 'M06'
    AND status_code.cat = repository.active
LEFT JOIN
    jca_constant_display type_repo
ON
    type_repo.type = 'M10'
    AND type_repo.cat = repository.type_repo
WHERE
	repository.DELETED_BY IS NULL	
	/*BEGIN*/
    AND (
    /*IF searchDto.code != null && searchDto.code != ''*/
    repository.code LIKE concat('%',  /*searchDto.code*/, '%')
    /*END*/
    /*IF searchDto.name != null && searchDto.name != ''*/
    OR repository.name LIKE concat('%',  /*searchDto.name*/, '%')
    /*END*/
    /*IF searchDto.physicalPath != null && searchDto.physicalPath != ''*/
    OR repository.physical_path LIKE concat('%',  /*searchDto.physicalPath*/, '%')
    /*END*/
    /*IF searchDto.subFolderRule != null && searchDto.subFolderRule != ''*/
    OR repository.sub_folder_rule LIKE concat('%',  /*searchDto.subFolderRule*/, '%')
    /*END*/
	)
	/*END*/
	ORDER BY repository.CREATED_DATE DESC
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY