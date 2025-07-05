SELECT 
	rfa."id" as id,
	rfa."account_id" as account_id,
	rfa."role_id" as role_id,
	rfa."start_date" as start_date,
	rfa."end_date" as end_date,
	rfa."del_flg" as del_flg,
	role."name" as role_name
FROM 
	"HSSA"."JCA_ROLE_FOR_ACCOUNT" rfa LEFT JOIN "HSSA"."JCA_ROLE" role ON rfa."role_id" = role."id"
WHERE rfa."account_id" = /*accountId*/