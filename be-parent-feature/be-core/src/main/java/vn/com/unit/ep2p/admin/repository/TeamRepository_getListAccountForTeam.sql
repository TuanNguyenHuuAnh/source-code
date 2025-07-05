select 
		acc.id as user_id, 
		acc.username as username,
		acc.fullname as fullname,
		acc.email
		
from JCA_ACCOUNT acc
LEFT JOIN jca_account_team acctea ON acc.id = acctea.account_id
where 
acc.DELETED_ID = 0
AND acc.enabled = '1'
AND acctea.team_id = /*teamId*/