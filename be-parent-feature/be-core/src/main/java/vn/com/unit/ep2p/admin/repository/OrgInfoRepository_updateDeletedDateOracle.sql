UPDATE 
	jca_m_org
SET 
	DELETED_BY = /*orgDto.deletedBy*/,
	deleted_date = /*orgDto.deletedDate*/
WHERE
	id = /*orgDto.id*/''