SELECT
	count(*)
FROM
    m_popup pop
    LEFT JOIN JCA_ACCOUNT acc WITH (NOLOCK)
    ON acc.ID = pop.CREATED_ID
WHERE
	pop.deleted_id IS NULL
	/*BEGIN*/
	AND  (
			/*IF searchDto.name != null && searchDto.name != ''*/
			OR UPPER(RTRIM(LTRIM(pop.NAME))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*searchDto.name*/))), '%' ))
			/*END*/
			
			/*IF searchDto.code != null && searchDto.code != ''*/
			OR UPPER(RTRIM(LTRIM(pop.CODE))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*searchDto.code*/))), '%' ))
			/*END*/
			
			/*IF searchDto.create != null && searchDto.create != ''*/
			OR UPPER(RTRIM(LTRIM(acc.USERNAME))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*searchDto.create*/))), '%' ))
			/*END*/
		)
	/*END*/