--
-- JcaRepositoryRepository_getListJcaRepositoryDtoByCondition.sql

SELECT      com.NAME 				 AS COMPANY_NAME
	       ,repo.ID				     AS ID
	       ,repo.NAME                AS NAME
	       ,repo.PHYSICAL_PATH       AS PHYSICAL_PATH
--	       ,repo.DURATION_START      AS DURATION_START
--	       ,repo.DURATION_END        AS DURATION_END
	       ,repo.SUB_FOLDER_RULE     AS SUB_FOLDER_RULE
	       ,repo.TYPE_REPO           AS TYPE_REPO
	       ,con.NAME				 AS TYPE_REPO_NAME
	       ,repo.ACTIVED             AS ACTIVED
	       ,CASE WHEN repo.ACTIVED = 1 THEN 'Active' ELSE 'Inactive' END            AS STATUS
	       ,repo.CODE                AS CODE
	       ,repo.DESCRIPTION         AS DESCRIPTION
	       ,repo.USERNAME            AS USERNAME
	       ,repo.PASSWORD            AS PASSWORD
	       ,repo.COMPANY_ID          AS COMPANY_ID
	       ,repo.FILE_PROTOCOL       AS FILE_PROTOCOL
	       ,con_protocol.NAME		 AS FILE_PROTOCOL_NAME
	       ,repo.SITE                AS SITE
	       ,repo.PATH                AS PATH
	        , ROW_NUMBER() OVER(ORDER BY    com.NAME, repo.NAME) as no
FROM        
	JCA_REPOSITORY repo
LEFT JOIN   JCA_COMPANY com 
	ON repo.COMPANY_ID = com.ID
LEFT JOIN   JCA_CONSTANT con 
	ON repo.TYPE_REPO = con.CODE
	AND con.ACTIVED = 1
	AND con.GROUP_CODE = 'JCA_ADMIN_REPO'
  	AND con.KIND = 'REPO_TYPE'
  	and UPPER(con.LANG_CODE) = UPPER(/*jcaRepositorySearchDto.langCode*/)
LEFT JOIN   JCA_CONSTANT con_protocol 
	ON repo.FILE_PROTOCOL = con_protocol.CODE
	AND con_protocol.ACTIVED = 1
	AND con_protocol.GROUP_CODE = 'JCA_ADMIN_REPO'
  	AND con_protocol.KIND = 'FILE_PROTOCOL'
  	and UPPER(con_protocol.LANG_CODE) = UPPER(/*jcaRepositorySearchDto.langCode*/)
WHERE       
	repo.DELETED_ID = 0
	/*IF jcaRepositorySearchDto.companyId != null && jcaRepositorySearchDto.companyId != 0*/
	AND repo.COMPANY_ID = /*jcaRepositorySearchDto.companyId*/
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
ORDER BY    com.NAME, repo.NAME