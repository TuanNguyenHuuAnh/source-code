SELECT
	COUNT(*)
FROM
    jca_repository repository   
WHERE
    repository.DELETED_ID = 0
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