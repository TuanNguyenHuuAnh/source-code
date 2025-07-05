SELECT 
	count(*)
FROM
(
		SELECT
		 ROW_NUMBER() OVER (ORDER BY org_id) as pageNo
	     ,org_id
	     ,org_name
	FROM
	    org_info
	WHERE
		deleted_date IS NULL
		AND org_type = 'B'
		/*IF condition.branchName != null && condition.branchName != ''*/
	    AND org_name LIKE '%' + /*condition.branchName*/ + '%'
	    /*END*/
) TBL