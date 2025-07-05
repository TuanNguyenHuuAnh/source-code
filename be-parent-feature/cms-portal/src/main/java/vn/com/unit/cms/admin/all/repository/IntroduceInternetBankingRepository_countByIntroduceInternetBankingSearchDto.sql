SELECT
	count(*)
FROM m_introduce_internet_banking introduce
LEFT JOIN m_introduce_internet_banking_language introduceLan ON introduceLan.introduce_internet_banking_id = introduce.id

WHERE
	introduce.delete_date is null
	AND UPPER(introduceLan.m_language_code) = UPPER(/*searchCond.languageCode*/)
   
	/*IF searchCond.code != null && searchCond.code != ''*/
	AND introduce.code LIKE concat('%',/*searchCond.code*/,'%')
	/*END*/
	/*IF searchCond.introductionType != null && searchCond.introductionType != ''*/
	AND introduce.introduction_type LIKE concat('%',/*searchCond.introductionType*/,'%')
	/*END*/
	
	/*IF searchCond.title != null && searchCond.title != ''*/
	AND introduceLan.title LIKE concat('%',/*searchCond.title*/,'%')
	/*END*/
	/*IF searchCond.description != null && searchCond.description != ''*/
	AND introduceLan.description LIKE concat('%',/*searchCond.description*/,'%')
	/*END*/