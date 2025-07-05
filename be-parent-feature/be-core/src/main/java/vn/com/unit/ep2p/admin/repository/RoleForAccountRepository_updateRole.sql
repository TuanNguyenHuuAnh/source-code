UPDATE JCA_ROLE_FOR_ACCOUNT 
SET 
	role_id = /*roleForAccountUpdate.roleId*/,
	start_date = /*roleForAccountUpdate.startDate*/,
	end_date = /*roleForAccountUpdate.endDate*/,
	UPDATED_DATE = /*roleForAccountUpdate.updatedDate*/,
	UPDATED_BY = /*roleForAccountUpdate.updatedBy*/
WHERE 
	id = /*roleForAccountUpdate.id*/