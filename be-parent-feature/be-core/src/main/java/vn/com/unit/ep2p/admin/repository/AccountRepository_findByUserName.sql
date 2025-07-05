--
-- AccountRepository_getJcaAccountForLogin.sql

select *
from JCA_ACCOUNT with (nolock)
where
	1=1
	/*IF username != null && username != '' */
	and USERNAME = /*username*/
	/*END*/
