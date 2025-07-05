SELECT
	job.id 					AS job_id,
	job. CODE 				AS job_code,
	job. NAME 				AS job_name,
	job.career 				AS career_id,	
	job.note				AS note,
	job.position			AS position,	
	job.location			AS location,
	job.urgent				AS urgent,
	job.enabled				AS enabled,
	job.sort				AS sort,
	job.expiry_date			AS expiry_date,
	job.status_code			AS status_code,
	job.process_id			AS process_id,
	job.id 					AS reference_id,
	job.create_date			AS create_date,
	job.experience			AS experience,	
	job.effective_date		AS effective_date,
	job.salary				AS salary,
	job.link_alias 			AS link_alias,	
	job.create_by			AS create_by,
	cd1.code 				AS position_name,
	cd2.code 				AS division_name
FROM
	m_job job
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
		con_lan.m_language_code = /*jobDto.languageCode*/
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
		con_lan.m_language_code = /*jobDto.languageCode*/
) cd2 ON cd2. CODE = job.division
WHERE
	job.delete_by IS NULL	
	/*IF jobDto.jobId != null && jobDto.jobId != ''*/
	AND job.id =/*jobDto.jobId*/
	/*END*/	
	/*IF jobDto.careerId != null && jobDto.careerId != ''*/
	AND job.career =/*jobDto.careerId*/
	/*END*/
	/*IF jobDto.position != null && jobDto.position != ''*/
	AND job.position =/*jobDto.position*/
	/*END*/
	/*IF jobDto.location != null && jobDto.location != ''*/
	AND job.location =/*jobDto.location*/
	/*END*/
	/*IF jobDto.urgent != null && jobDto.urgent != ''*/
	AND job.urgent =/*jobDto.urgent*/
	/*END*/
	/*IF jobDto.expiryDate != null && jobDto.expiryDate != ''*/
	AND job.expiry_date =/*jobDto.expiryDate*/
	/*END*/
	/*IF jobDto.linkAlias != null && jobDto.linkAlias != ''*/
	AND job.link_alias =/*jobDto.linkAlias*/
	/*END*/
	ORDER BY job.create_date DESC