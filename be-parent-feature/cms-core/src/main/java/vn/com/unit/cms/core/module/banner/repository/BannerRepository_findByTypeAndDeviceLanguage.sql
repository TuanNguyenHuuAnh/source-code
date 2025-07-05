SELECT
      b.id							AS	id
  	, b.code						AS	code
  	, bl.title						AS	name
    , b.note  						AS	note
    , b.create_date					AS	create_date
    , b.is_mobile					AS	is_mobile
FROM
    m_banner b
LEFT JOIN m_banner_language bl ON (b.id = bl.banner_id and bl.delete_date is null)
WHERE
	b.delete_date IS NULL
	AND
	UPPER(bl.M_LANGUAGE_CODE) = UPPER(/*m_language_code*/)
	AND
	banner_type = /*bannerType*/
	AND
	is_mobile = /*isMobile*/
	/*IF status != null*/
        AND b.status = /*status*/
    /*END*/
ORDER BY bl.title ASC