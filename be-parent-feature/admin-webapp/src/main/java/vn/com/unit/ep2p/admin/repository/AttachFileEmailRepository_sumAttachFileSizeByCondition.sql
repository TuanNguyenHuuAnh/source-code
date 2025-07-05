select COALESCE(sum(file_size), 0)
from JCA_M_ATTACH_FILE_EMAIL
where 
/*BEGIN*/
	/*IF emailUuid != null or emailUuid != ''*/
		uuid_email = /*emailUuid*/
	/*END*/
	/*IF emailId != null*/
		and email_id = /*emailId*/
	/*END*/
/*END*/