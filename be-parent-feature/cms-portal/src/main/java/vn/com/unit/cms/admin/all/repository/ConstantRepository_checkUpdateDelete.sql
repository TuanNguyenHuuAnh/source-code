SELECT		
	CASE
WHEN job.`code` IS NULL THEN
	0
ELSE
	1
END AS check_update_delete
FROM
    m_constant const
    LEFT JOIN m_constant_language ml ON const.code = ml.m_constant_code      
		LEFT JOIN m_job job ON const. CODE = job.position OR const. CODE = job.division AND job.delete_by IS NULL
WHERE
    const.delete_by IS NULL
AND const.code = /*constantCode*/
    