SELECT ROW_NUMBER() OVER(ORDER BY DOC_ID ASC) AS NO
	, DOC_ID
	, DOC_NAME
	, case DOC_ID
		when 'DOC_25' then 'Phu luc 02_Bo Hop dong Dai ly.pdf'
		else 'Phu luc 03_Bo Hop dong Quan ly Dai ly.pdf'
	end as FILE_NAME
	, '01/01/2023' AS APPLICABLE_DATE
	, NOTES
FROM STG_DS.M_LETTER_TEMPLATE 
WHERE  DOC_ID IN /*docIds*/()