SELECT
	job.id 				AS job_id,
	job. CODE 			AS job_code,	
	job.position		AS position,
	job.location 		AS location,
	job.urgent			AS urgent,
	job.enabled			AS enabled,
	job.sort			AS sort,
	job.expiry_date		AS expiry_date,
	ml.job_title		AS job_title,
	ml.job_detail		AS job_detail,
	ml.job_description	AS job_description,
	job.process_id		AS process_id,
	job.id 				AS reference_id,
	job.create_date		AS create_date,
	job.status_code		AS status_code,	
	job.experience		AS experience,	
	job.career 			AS career_id,	
	job.salary			AS salary,	
	job.link_alias 		AS link_alias,
	job.create_by		AS create_by,
	job.recruitment_number AS recruitment_number,
	cd1. CODE 			AS status_code_disp,
	cd2. CODE 			AS position_name,
	cd3.CODE 			AS division_name
FROM
	m_job job
LEFT JOIN m_job_language ml ON job.id = ml.m_job_id
LEFT JOIN jca_m_branch m_branch ON m_branch.`name` = job.location
LEFT JOIN jca_constant_display cd1 ON cd1.type = 'M06'
AND cd1.cat = job.status_code
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
) cd2 ON cd2. CODE = job.position
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
) cd3 ON cd3. CODE = job.division
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
	AND cd3.code = /*jobSearchDto.division*/
	/*END*/
	/*IF jobSearchDto.expiryDateFrom != null*/
	AND job.expiry_date >= /*jobSearchDto.expiryDateFrom*/ 
	/*END*/
	/*IF jobSearchDto.expiryDateTo != null*/
	AND job.expiry_date <= /*jobSearchDto.expiryDateTo*/
	/*END*/
	/*IF jobSearchDto.position != null && jobSearchDto.position != ''*/
	AND cd2.code = /*jobSearchDto.position*/
	/*END*/
	)
	/*END*/
	ORDER BY job.create_date DESC
	LIMIT /*sizeOfPage*/ OFFSET /*startIndex*/