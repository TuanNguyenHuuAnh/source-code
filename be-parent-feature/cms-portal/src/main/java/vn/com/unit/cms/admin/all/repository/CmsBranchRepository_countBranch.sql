
WITH TBL_DATA AS (
    SELECT
        br.id												AS	ID
         ,br.type											AS	TYPE
         ,br.name											AS	TITLE
         ,br.address										AS	ADDRESS
         ,br.CITY_NAME											AS	CITY
         ,br.DISTRICT_NAME										as  DISTRICT
         ,br.active_flag									AS	ACTIVE_FLAG
         ,br.create_by										AS	create_by
         ,br.create_date									AS	create_date
         ,br.update_by										AS	update_by
         ,br.update_date									AS	update_date
         ,cb.name 											AS	TYPE_NAME
         ,br.active_flag									AS	ENABLED
         ,br.name											AS	NAME
    FROM
        jca_m_branch br
            LEFT JOIN JCA_CONSTANT cb
                      ON (cb.CODE = br.TYPE
                          AND cb.LANG_CODE = UPPER(/*searchDto.languageCode*/'vi')
                          AND cb.GROUP_CODE = 'BRANCH_TYPE')
    WHERE
        br.delete_by IS NULL
)
SELECT
	count(*)
FROM TBL_DATA TBL
WHERE
	1 = 1

	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/'bao'
	)
	/*END*/