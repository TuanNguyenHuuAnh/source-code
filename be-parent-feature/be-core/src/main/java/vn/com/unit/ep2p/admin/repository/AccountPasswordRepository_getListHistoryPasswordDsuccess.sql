select top(/*numOfChangePass*/) *
from JCA_ACCOUNT_PASSWORD
where IS_CHANGE_PASS = 1
AND ACCOUNT_ID = /*accountId*/
order by id desc