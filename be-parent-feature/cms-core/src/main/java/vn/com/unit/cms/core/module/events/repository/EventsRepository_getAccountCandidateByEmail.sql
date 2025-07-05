SELECT 
	  acc.FULLNAME
	  , acc.EMAIL
	  , acc.PHONE
	  , acc.REGISTER_DATE
FROM
  	JCA_ACCOUNT_REGISTER acc

WHERE acc.EMAIL = /*email*/''
AND acc.FULLNAME = /*fullname*/''
AND acc.PHONE = /*phone*/''