SELECT
	count(*)
FROM
    m_job job
    LEFT JOIN m_job_language ml ON job.id = ml.m_job_id   	
	LEFT JOIN jca_m_branch m_branch ON m_branch.`name` = job.location	
LEFT JOIN (
	SELECT
		con.id AS id,
		con. CODE AS CODE,
		con.type AS type,
		con_lan.m_constant_code AS constant_code,
		con_lan. NAME AS NAME
	FROM
		m_constant con
	LEFT JOIN m_constant_language con_lan ON con. CODE = con_lan.m_constant_code
	WHERE
		con_lan.m_language_code = /*jobSearchDto.languageCode*/
) cd1 ON cd1. CODE = job.position
LEFT JOIN (
	SELECT
		con.id AS id,
		con. CODE AS CODE,
		con.type AS type,
		con_lan.m_constant_code AS constant_code,
		con_lan. NAME AS NAME
	FROM
		m_constant con
	LEFT JOIN m_constant_language con_lan ON con. CODE = con_lan.m_constant_code
	WHERE
		con_lan.m_language_code = /*jobSearchDto.languageCode*/
) cd2 ON cd2. CODE = job.division
WHERE
    job.delete_by IS NULL
    AND ml.m_language_code = UPPER(/*jobSearchDto.languageCode*/)
	/*BEGIN*/
	AND (
	/*IF jobSearchDto.jobTitle != null && jobSearchDto.jobTitle != ''*/
	OR ml.job_title LIKE concat('%',  /*jobSearchDto.jobTitle*/, '%')
	/*END*/		
	/*IF jobSearchDto.status != null && jobSearchDto.status != ''*/
	AND job.status_code = /*jobSearchDto.status*/
	/*END*/
	/*IF jobSearchDto.location != null && !jobSearchDto.location.isEmpty()*/
	AND job.location REGEXP /*jobSearchDto.location*/
	/*END*/
	/*IF jobSearchDto.career != null && !jobSearchDto.career.isEmpty()*/
	AND job.career REGEXP /*jobSearchDto.career*/
	/*END*/
	/*IF jobSearchDto.division != null && !jobSearchDto.division.isEmpty()*/
	AND cd2.name REGEXP /*jobSearchDto.division*/
	/*END*/
	/*IF jobSearchDto.expiryDateFrom != null*/
	AND job.expiry_date >= /*jobSearchDto.expiryDateFrom*/ 
	/*END*/
	/*IF jobSearchDto.expiryDateTo != null*/
	AND job.expiry_date <= /*jobSearchDto.expiryDateTo*/
	/*END*/
	/*IF jobSearchDto.position != null && jobSearchDto.position != ''*/
	AND cd1.name = /*jobSearchDto.position*/
	/*END*/
		)
	/*END*/