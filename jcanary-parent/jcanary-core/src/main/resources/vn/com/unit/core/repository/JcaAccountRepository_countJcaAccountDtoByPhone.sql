SELECT 
	COUNT(1)
FROM
  	JCA_ACCOUNT acc
WHERE
  	acc.DELETED_ID = 0
	AND acc.PHONE = /*phone*/
	/*IF userId != null*/
	AND acc.ID != /*userId*/
	/*END*/
