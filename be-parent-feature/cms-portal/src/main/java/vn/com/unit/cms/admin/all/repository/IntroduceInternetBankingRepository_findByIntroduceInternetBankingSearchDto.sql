SELECT
	 introduce.id				AS	id
  	, introduce.code				AS	code
  	, introduce.introduction_type				AS	introduction_type
  	, introduce.enabled			AS	enabled
  	, introduceLan.title			AS	title
  	, introduceLan.description		AS	description
  	, introduce.create_date		AS  create_date
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
ORDER BY introduce.create_date DESC
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY