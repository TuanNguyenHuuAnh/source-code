SELECT 
	  acc.ID 						as USER_ID
	  ,acc.CODE 					as CODE
	  ,acc.FULLNAME 				as FULLNAME
	  ,acc.USERNAME 				as USERNAME
	  ,acc.EMAIL 					as EMAIL
	  ,acc.RECEIVED_NOTIFICATION 	as RECEIVED_NOTIFICATION
	  ,acc.RECEIVED_EMAIL 			as RECEIVED_EMAIL
	  ,acc.PHONE 					as PHONE
	  ,acc.GENDER 					as GENDER
	  ,acc.BIRTHDAY 				as BIRTHDAY
	  ,acc.ACTIVED 					as ACTIVED
	  ,acc.ENABLED 					as ENABLED
	  ,acc.AVATAR 					as AVATAR
	  ,acc.COMPANY_ID 				as COMPANY_ID
	  ,acc.AVATAR_REPO_ID 			as AVATAR_REPO_ID
	  ,com.NAME 					as COMPANY_NAME
	  ,pos.NAME 					as POSITION_NAME
FROM
  	JCA_ACCOUNT acc
LEFT JOIN
  	JCA_POSITION pos
ON
  	pos.ID = acc.POSITION_ID 
LEFT JOIN
  	JCA_COMPANY com
ON
  	com.ID = acc.COMPANY_ID
WHERE
  	acc.DELETED_ID = 0
	AND acc.ID = /*id*/
