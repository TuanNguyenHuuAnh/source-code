SELECT tbr.recruitment_id AS id,
	   tbr.m_branch_id AS branch_id,
       tbr.position AS position,
       tbr.position_en AS position_en,
       tbr.address AS address,
       tbr.address_en AS address_en,
       tbr.description AS description,
       tbr.description_en AS description_en,
       tbr.deadline_time AS deadlinetime
FROM m_recruitment_test tbr
WHERE tbr.deleted_date is null
	/*BEGIN*/AND (
	/*IF recDto.position != null && recDto.position != ''*/
	OR tbr.position LIKE concat('%',  /*recDto.position*/, '%')
	/*END*/
	)/*END*/
LIMIT /*sizeOfPage*/ OFFSET /*offset*/