select 
		acc.id as account_id, 
		acc.username as username,
		acc.fullname as fullname,
		acc.email as email
from JCA_ACCOUNT acc
where acc.enabled = '1'
and acc.DELETED_ID = 0
/*IF companyId != null*/
AND acc.company_id = /*companyId*/1
/*END*/