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
	       ,repo.CODE                AS CODE
	       ,repo.DESCRIPTION         AS DESCRIPTION
	       ,repo.USERNAME            AS USERNAME
	       ,repo.PASSWORD            AS PASSWORD
	       ,repo.COMPANY_ID          AS COMPANY_ID
	       ,repo.FILE_PROTOCOL       AS FILE_PROTOCOL
	       ,con_protocol.NAME		 AS FILE_PROTOCOL_NAME
	       ,repo.SITE                AS SITE
	       ,repo.PATH                AS PATH
FROM        
	JCA_REPOSITORY repo
LEFT JOIN   JCA_COMPANY com 
	ON repo.COMPANY_ID = com.ID
LEFT JOIN   JCA_CONSTANT con 
	ON repo.TYPE_REPO = con.CODE
	AND con.ACTIVED = 1
	AND con.GROUP_CODE = 'JCA_ADMIN_REPO'
  	AND con.KIND = 'REPO_TYPE'
  	and UPPER(con.LANG_CODE) = UPPER(/*langCode*/)
LEFT JOIN   JCA_CONSTANT con_protocol 
	ON repo.FILE_PROTOCOL = con_protocol.CODE
	AND con_protocol.ACTIVED = 1
	AND con_protocol.GROUP_CODE = 'JCA_ADMIN_REPO'
  	AND con_protocol.KIND = 'FILE_PROTOCOL'
  	and UPPER(con_protocol.LANG_CODE) = UPPER(/*langCode*/)
WHERE       
	repo.DELETED_ID = 0
	AND repo.id = /*id*/