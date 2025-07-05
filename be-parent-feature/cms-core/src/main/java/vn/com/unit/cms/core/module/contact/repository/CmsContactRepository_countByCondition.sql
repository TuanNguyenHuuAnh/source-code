--
-- CmsContactRepository_countByCondition.sql

with tmp
as
	(
		select *
		from CMS_CONTACT
	)

select count(*)
from tmp
