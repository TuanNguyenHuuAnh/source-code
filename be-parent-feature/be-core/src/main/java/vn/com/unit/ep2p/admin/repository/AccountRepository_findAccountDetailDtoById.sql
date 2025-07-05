SELECT
    acc.id                	AS id
    , acc.code        		AS code
	, acc.username        	AS username
	, acc.fullname        	AS fullname
	, acc.email           	AS email
	, acc.phone           	AS phone
	, acc.birthday        	AS birthday
	, acc.enabled 			AS enabled
	, acc.avatar 			AS avatar
	, acc.avatar_repo_Id 	AS repository_id
	, acc_code.CODE         AS status_code
	, case acc.position_id when 0 then N'Unknown' else position.name_abv end as position_name
	, co.id 			  	AS company_id
	, co.name 			 	AS company_name
	, acc.actived 			AS actived
	, acc.code        		AS code
	, acc.gender			AS gender
	, acc.RECEIVED_NOTIFICATION AS push_notification
	, acc.RECEIVED_EMAIL		AS push_email
	, acc.ACTIVED		AS ARCHIVE_FLAG
	, acc.CHANNEL 		AS channel
	, acc.PARTNER		AS PARTNER
FROM
	 (jca_account acc 
	  
	  LEFT JOIN jca_position position ON acc.position_id = position.id
	)
LEFT JOIN
    JCA_CONSTANT acc_code
ON
    	acc_code.GROUP_CODE = 'JCA_ADMIN_CHECKBOX'
    AND acc_code.KIND = 'CHECKBOX_ACTIVED'
    AND acc_code.CODE = cast(acc.enabled as char)
LEFT JOIN
	JCA_COMPANY co ON acc.company_id = co.id and co.DELETED_ID = 0
WHERE
	acc.id = /*id*/