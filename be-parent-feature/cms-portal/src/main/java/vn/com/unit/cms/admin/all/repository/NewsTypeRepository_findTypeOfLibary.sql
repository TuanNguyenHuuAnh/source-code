SELECT
    *
FROM
    m_news_type
WHERE
	delete_date IS NULL
	AND ENABLED = 1
	AND M_CUSTOMER_TYPE_ID = /*customerId*/
	/*IF typeOfLibary == 1 || typeOfLibary == 0*/
	AND TYPE_OF_LIBRARY = /*typeOfLibary*/
	/*END*/
	/*IF typeOfLibary != 1 && typeOfLibary != 0*/
	AND TYPE_OF_LIBRARY is NULL
	/*END*/
	
