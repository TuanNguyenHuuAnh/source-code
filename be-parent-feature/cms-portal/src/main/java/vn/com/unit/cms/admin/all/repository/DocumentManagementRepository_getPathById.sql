--
-- DocumentManagementRepository_getPathById.sql

with DOCUMENT_MANAGEMENT_TREE
as
	(
		select
			ID
			-- , ID as ROOT_ID

			, CAST(CODE as nvarchar(max)) as ROOT_CODE
			, CAST(CODE as nvarchar(max)) as CODE

			, 0 as LEVEL
		from CMS_DOCUMENT_MANAGEMENT with (nolock)
		where
			ISNULL(DELETED_ID, 0) = 0

		union all

		select
			document_management.ID
			-- , document_management_tree.ROOT_ID

			, CAST(document_management_tree.ROOT_CODE as nvarchar(max)) as ROOT_CODE
			, CAST( (document_management_tree.CODE + '/' + document_management.CODE) as nvarchar(max)) as CODE

			, LEVEL + 1 as LEVEL
		from DOCUMENT_MANAGEMENT_TREE
		inner join CMS_DOCUMENT_MANAGEMENT document_management with (nolock)
			on document_management.PARENT_ID = document_management_tree.ID
			and ISNULL(DELETED_ID, 0) = 0
			and document_management.ID <> document_management_tree.ID
	)
select TOP 1 REPLACE(document_management_tree.CODE, '//', '/')

	-- document_management_tree.ID
	-- , document_management_tree.ROOT_ID
	-- , document_management_tree.CODE

	--  document_management_tree.LEVEL
from DOCUMENT_MANAGEMENT_TREE document_management_tree
where
	document_management_tree.ID = /*id*/
order by LEVEL DESC, ID
