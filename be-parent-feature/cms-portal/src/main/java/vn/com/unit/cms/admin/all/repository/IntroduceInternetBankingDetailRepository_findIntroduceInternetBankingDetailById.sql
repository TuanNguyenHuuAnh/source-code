SELECT
	  id						AS	id
	, introduce_internet_banking_id		AS	introduce_internet_banking_id
	, m_language_code			AS	language_code
	, title				AS	title
	, content					AS	content
	, group_content				AS	group_content
FROM m_introduce_internet_banking_detail
WHERE delete_date is null
	AND introduce_internet_banking_id = /*introduceInternetBankingId*/
ORDER BY group_content