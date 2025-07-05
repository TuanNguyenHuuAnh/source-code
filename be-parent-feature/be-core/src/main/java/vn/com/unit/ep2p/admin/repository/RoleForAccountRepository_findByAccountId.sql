SELECT 
	rfa.account_id as user_id,
	rfa.role_id as role_id
FROM 
	JCA_ROLE_FOR_ACCOUNT rfa
WHERE rfa.account_id = /*accountId*/