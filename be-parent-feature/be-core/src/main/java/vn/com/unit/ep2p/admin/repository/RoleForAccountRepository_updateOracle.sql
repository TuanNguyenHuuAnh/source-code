UPDATE "HSSA"."JCA_ROLE_FOR_ACCOUNT" rfa
SET 
	rfa."role_id" = /*roleForAccountUpdate.roleId*/,
	rfa."start_date" = /*roleForAccountUpdate.startDate*/,
	rfa."end_date" = /*roleForAccountUpdate.endDate*/,
	rfa."UPDATED_DATE" = /*roleForAccountUpdate.updatedDate*/,
	rfa."UPDATED_BY" = /*roleForAccountUpdate.updatedBy*/
WHERE 
	rfa."id" = /*roleForAccountUpdate.id*/