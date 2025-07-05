SELECT
    *
FROM
(
	SELECT
		 ROW_NUMBER() OVER (ORDER BY id) as pageNo
	     ,id
	     ,org_name
	FROM
	    jca_m_org
	WHERE
		deleted_date IS NULL
		AND org_type = 'B'
		/*IF condition.branchName != null && condition.branchName != ''*/
	    AND org_name LIKE '%' + /*condition.branchName*/ + '%'
	    /*END*/
) TBL
WHERE pageNo BETWEEN ((/*currentPage*/ - 1) * /*sizeOfPage*/ + 1)
AND (/*currentPage*/ * /*sizeOfPage*/)