--
-- DocumentManagementRepository_count.sql

with temp
as
	(
		SELECT *
		FROM CMS_DOCUMENT_MANAGEMENT
		WHERE
			ISNULL(DELETED_ID, 0) = 0
	)
select count(*)
from temp
