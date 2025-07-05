select acc.ID as user_id
		, acc.USERNAME as username
		, acc.EMAIL
from jca_account acc
left join JCA_ACCOUNT_TEAM act on act.ACCOUNT_ID = acc.ID
where 
/*IF id != null*/
	act.TEAM_ID = /*id*/1
	/*END*/