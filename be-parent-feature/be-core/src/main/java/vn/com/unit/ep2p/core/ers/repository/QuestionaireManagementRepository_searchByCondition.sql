--
-- QuestionaireManagementRepository_searchByCondition.sql

select
	*
	, ROW_NUMBER() OVER(ORDER BY ID)		as no
from ERS_QUESTION_INTERVIEW
where
	1=1
	/*IF searchDto.typeQuestion != null && searchDto.typeQuestion != ''*/
		and TYPE_QUESTION = /*searchDto.typeQuestion*/
	/*END*/
	/*IF searchDto.applyForPosition != null && searchDto.applyForPosition != ''*/
		and /*searchDto.applyForPosition*/
			in
				(
					select value
					from STRING_SPLIT(APPLY_FOR_POSITION, ',')
				)
	/*END*/
	/*IF searchDto.statusItem != null && searchDto.statusItem != ''*/
		and STATUS_ITEM = /*searchDto.statusItem*/
	/*END*/
	/*IF searchDto.deletedFlag != null*/
		and ISNULL(DELETED_FLAG, 0) = /*searchDto.deletedFlag*/
	/*END*/

order by id

/*IF offset != null && pageSize != null*/
OFFSET /*offset*/0 ROWS FETCH NEXT /*pageSize*/5 ROWS ONLY
/*END*/

