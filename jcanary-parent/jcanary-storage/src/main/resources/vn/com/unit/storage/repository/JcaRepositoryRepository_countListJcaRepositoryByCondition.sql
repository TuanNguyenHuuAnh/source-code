--
-- JcaRepositoryRepository_countListJcaRepositoryByCondition.sql

SELECT      COUNT(1)
FROM        JCA_REPOSITORY repo
WHERE       repo.DELETED_ID = 0
	/*IF jcaRepositorySearchDto.companyId != null && jcaRepositorySearchDto.companyId != 0*/
	AND repo.COMPANY_ID = /*jcaRepositorySearchDto.companyId*/1
	/*END*/
	/*IF jcaRepositorySearchDto.companyIdList != null && jcaRepositorySearchDto.companyId == 0 && !jcaRepositorySearchDto.companyAdmin*/
	AND (repo.COMPANY_ID IS NULL OR repo.COMPANY_ID IN /*jcaRepositorySearchDto.companyIdList*/() )
	/*END*/
	/*IF jcaRepositorySearchDto.companyIdList == null || jcaRepositorySearchDto.companyId != 0 || jcaRepositorySearchDto.companyAdmin*/
		/*IF jcaRepositorySearchDto.companyId == null || jcaRepositorySearchDto.companyId == ''*/
		AND repo.COMPANY_ID IS NULL
		/*END*/
	/*END*/
	/*BEGIN*/
	AND
	(
		/*IF jcaRepositorySearchDto.name != null && jcaRepositorySearchDto.name != ''*/
		OR UPPER(repo.NAME) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaRepositorySearchDto.name*/''), '%' ))
		/*END*/
		/*IF jcaRepositorySearchDto.code != null && jcaRepositorySearchDto.code != ''*/
	    OR UPPER(repo.CODE) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaRepositorySearchDto.code*/''), '%' ))
	    /*END*/
	    
	    /*IF jcaRepositorySearchDto.physicalPath != null && jcaRepositorySearchDto.physicalPath != ''*/
		OR UPPER(repo.PHYSICAL_PATH) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaRepositorySearchDto.physicalPath*/''), '%' ))
		/*END*/
		/*IF jcaRepositorySearchDto.subFolderPath != null && jcaRepositorySearchDto.subFolderPath != ''*/
	    OR UPPER(repo.SUB_FOLDER_RULE) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaRepositorySearchDto.subFolderPath*/''), '%' ))
	    /*END*/
    )
    /*END*/
