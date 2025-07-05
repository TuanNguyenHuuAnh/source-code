SELECT
    ma.USERNAME as username,
    ma.FULLNAME as fullname,
    ma.EMAIL as email,
    mo1.ORG_NAME as department_name,
    mo2.ORG_NAME as branch_name
FROM
    jca_account ma left join JCA_M_ORG mo1 on ma.department_id = mo1.id
    				 left join JCA_M_ORG mo2 on ma.branch_id = mo2.id
WHERE
	ACTIVED = 1 AND (UPPER(ma.FULLNAME) LIKE CONCAT(CONCAT('%',UPPER(/*inputQuery*/'')),'%') OR 
									 UPPER(ma.EMAIL) LIKE CONCAT(CONCAT('%',UPPER(/*inputQuery*/'')),'%'))
ORDER BY FULLNAME
OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY