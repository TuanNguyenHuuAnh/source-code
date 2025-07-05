SELECT
	*
FROM
	jca_repository repository
WHERE
	repository.DELETED_ID = 0	
	/*IF companyId != null */
	AND (repository.company_id = /*companyId*/ OR repository.company_id is null)
	/*END*/
	/*IF repoId != null */
	AND (repository.id = /*repoId*/)
	/*END*/