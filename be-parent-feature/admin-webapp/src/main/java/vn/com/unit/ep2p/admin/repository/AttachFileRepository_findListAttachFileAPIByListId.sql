SELECT
	 item.id			AS file_id
	,item.FILE_TYPE	
	,item.FILE_MINE_TYPE
	,item.FILE_NAME
	,item.REFERENCE
	,item.REFERENCE_ID
	,item.PATH_FILE
	,item.REPOSITORY_ID
	,item.FILE_SIZE
FROM
	JCA_ATTACHFILE item
WHERE
	item.DELETED_ID = 0
	AND item.ID IN /*listId*/()