SELECT
	repository.id 	       	     AS id
	, repository.code            AS code
    , repository.name   	 	 AS name
    , repository.physical_path 	 AS physical_path
    , repository.sub_folder_rule AS sub_folder_rule
    , repository.type_repo       AS type_repo
    , type_repo.cat_abbr_name    AS type_repo_name
    , repository.description     AS description
    , repository.active          AS active
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
	AND repository.id = /*id*/