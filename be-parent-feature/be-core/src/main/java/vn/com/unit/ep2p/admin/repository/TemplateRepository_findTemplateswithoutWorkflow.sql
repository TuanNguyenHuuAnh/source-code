SELECT id as id, template_name AS name, file_name AS text
FROM
    JCA_EMAIL_TEMPLATE 
where 
DELETED_ID = 0 
AND company_id IS NULL 
AND file_name IN /*names*/()
