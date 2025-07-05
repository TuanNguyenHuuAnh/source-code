--
-- CmsContactRepository_searchByCondition.sql

select *
from CMS_CONTACT
order by id desc

OFFSET /*offset*/ ROWS FETCH NEXT /*sizeOfPage*/ ROWS ONLY
