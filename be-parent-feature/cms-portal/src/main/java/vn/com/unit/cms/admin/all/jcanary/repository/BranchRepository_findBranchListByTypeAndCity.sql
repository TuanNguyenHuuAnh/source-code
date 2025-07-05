SELECT
	  branch.latitude			AS	latitude
	, branch.longtitude			AS	longtitude
    , branch.name				AS	name
	, branch.address			AS	address
	, branch.phone				AS	phone
	, branch.fax				AS	fax
	, branch.icon				AS	icon
	, branch.working_hours		AS	working_hours
	, branch.email 				AS  email
FROM
	jca_m_branch	branch	
WHERE 
	branch.delete_by IS NULL	
	AND REPLACE(branch.city,' ','') LIKE REPLACE(concat('%', /*cityName*/, '%'),' ','')
	AND branch.type = /*locationType*/
	/*IF keyword != null && keyword != ''*/
	AND branch.address LIKE concat('%',  /*keyword*/, '%')
	/*END*/
ORDER BY create_date DESC