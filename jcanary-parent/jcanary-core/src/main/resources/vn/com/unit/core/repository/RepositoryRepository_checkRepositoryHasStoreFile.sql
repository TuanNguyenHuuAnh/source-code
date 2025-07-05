--
-- RepositoryRepository_checkRepositoryHasStoreFile.sql

select sum(temp.has_store_file)
from
	(
		SELECT count(1) as has_store_file FROM JCA_ACCOUNT WHERE DELETED_ID = 0 AND AVATAR_REPO_ID IS NOT NULL AND AVATAR_REPO_ID = /*repositoryId*/
		UNION
		SELECT count(1) as has_store_file FROM efo_form WHERE DELETED_ID = 0 AND ICON_REPO_ID IS NOT NULL AND ICON_REPO_ID = /*repositoryId*/
		UNION
		SELECT count(1) as has_store_file FROM jca_company WHERE DELETED_ID = 0 AND LOGIN_BACKGROUND_REPO_ID IS NOT NULL AND LOGIN_BACKGROUND_REPO_ID = /*repositoryId*/
		UNION
		SELECT count(1) as has_store_file FROM jca_company WHERE DELETED_ID = 0 AND SHORTCUT_ICON_REPO_ID IS NOT NULL AND SHORTCUT_ICON_REPO_ID = /*repositoryId*/
		UNION
		SELECT count(1) as has_store_file FROM jca_company WHERE DELETED_ID = 0 AND LOGO_LARGE_REPO_ID IS NOT NULL AND LOGO_LARGE_REPO_ID = /*repositoryId*/
		UNION
		SELECT count(1) as has_store_file FROM jca_company WHERE DELETED_ID = 0 AND LOGO_MINI_REPO_ID IS NOT NULL AND LOGO_MINI_REPO_ID = /*repositoryId*/
	) temp