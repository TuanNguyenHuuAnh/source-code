SELECT 
	  COUNT(1)
FROM
  	JCA_ACCOUNT acc

WHERE
  	acc.DELETED_ID = 0
	AND acc.EMAIL = /*email*/
	/*IF userId != null*/
	AND acc.ID != /*userId*/
	/*END*/
