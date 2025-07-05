SELECT id as id, template_name AS name, file_name AS text
FROM
    JCA_EMAIL_TEMPLATE 
where 
	1 = 1
/*IF fileFormat != null && fileFormat != ''*/	
AND file_format = /*fileFormat*/
/*END*/
/*IF term!= null && term != ''*/
AND template_name like '%' + /*term*/ + '%'
/*END*/
