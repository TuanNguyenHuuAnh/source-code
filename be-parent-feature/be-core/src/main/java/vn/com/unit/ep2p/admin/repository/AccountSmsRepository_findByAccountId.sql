select *
from JCA_ACCOUNT_OTP
where ACCOUNT_ID = /*accountId*/''
and datediff(day,EFFECTIVE_DATE,/*currentDate*/'') = 0
and TYPE_OTP = /*typeOtp*/''