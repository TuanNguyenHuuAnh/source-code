SELECT
	job.id 					AS job_id,
	job. CODE 				AS job_code,		
	job.note				AS note,
	job.position			AS position,	
	job.urgent				AS urgent,
	job.enabled				AS enabled,
	job.sort				AS sort,
	job.expiry_date			AS expiry_date,
	job.status_code			AS status_code,
	job.process_id			AS process_id,
	job.id 					AS reference_id,
	job.create_date			AS create_date,	
	job.experience			AS experience, 
	job.location			AS location,	
	job.division			AS division,
	job.effective_date		AS effective_date,
	job.salary				AS salary,
	job.career 				AS career_id,
	job.link_alias 			AS link_alias,
	job.create_by			AS create_by,
	job.recruitment_number 	AS recruitment_number,
	cd1.name 				AS position_name,
	cd2.name 				AS division_name
FROM
	m_job job
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
		con_lan.m_language_code = /*languageCode*/
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
		con_lan.m_language_code = /*languageCode*/
) cd2 ON cd2. CODE = job.division
WHERE
	job.delete_by IS NULL		
	AND job.id = /*jobId*/	
	ORDER BY job.create_date DESC