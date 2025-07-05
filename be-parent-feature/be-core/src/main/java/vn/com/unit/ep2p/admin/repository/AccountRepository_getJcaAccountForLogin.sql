--
-- AccountRepository_getJcaAccountForLogin.sql

select *
from JCA_ACCOUNT
where
	1=1
	/*IF providerId == 'facebook.com'*/
	and FACEBOOK_LOGIN = 1
	/*END*/
	/*IF providerId == 'google.com'*/
	and GOOGLE_LOGIN = 1
	/*END*/
	/*IF providerId == 'apple.com'*/
	and APPLE_LOGIN = 1
	/*END*/
	and
		(
			EMAIL = /*email*/
			or
			PHONE = /*phone*/
			or
			ACCOUNT_TYPE = /*accountType*/
		)
