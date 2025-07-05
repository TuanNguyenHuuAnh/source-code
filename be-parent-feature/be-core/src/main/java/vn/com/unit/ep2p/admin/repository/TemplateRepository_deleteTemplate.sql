UPDATE JCA_EMAIL_TEMPLATE
set deleted_date = GETDATE(),
	DELETED_BY = /*user*/,
	actived = 0
where 
	id = /*templateId*/