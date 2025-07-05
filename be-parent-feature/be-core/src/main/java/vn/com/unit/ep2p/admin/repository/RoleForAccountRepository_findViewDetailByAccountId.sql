SELECT 
	rfa.account_id as user_id,
	rfa.role_id as role_id,
	role.name as role_name
FROM 
	JCA_ROLE_FOR_ACCOUNT rfa LEFT JOIN JCA_ROLE role ON rfa.role_id = role.id
WHERE rfa.account_id = /*accountId*/