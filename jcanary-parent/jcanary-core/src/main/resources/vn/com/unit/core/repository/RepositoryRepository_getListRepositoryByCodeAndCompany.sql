--
-- RepositoryRepository_getListRepositoryByCodeAndCompany.sql

SELECT 
	*
FROM
	jca_repository
where
	ACTIVED = 1
	and ISNULL(DELETED_ID, 0) = 0
	and CODE = /*code*/
	and COMPANY_ID = /*companyId*/
