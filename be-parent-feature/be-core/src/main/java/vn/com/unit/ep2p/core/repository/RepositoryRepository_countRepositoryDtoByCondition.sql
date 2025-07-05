SELECT
	count(*)
FROM
    jca_m_repository repository   
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