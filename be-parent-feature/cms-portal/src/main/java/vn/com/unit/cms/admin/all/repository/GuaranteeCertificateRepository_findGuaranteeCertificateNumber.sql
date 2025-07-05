SELECT *
FROM m_guarantee_certificate guaranteeCertificate
WHERE 
	guaranteeCertificate.delete_by IS NULL
	AND certificate_number = /*certificateNumber*/
