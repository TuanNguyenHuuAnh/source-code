SELECT mntl.*    
FROM m_news_type mnt
join M_NEWS_TYPE_LANGUAGE mntl on mntl.M_NEWS_TYPE_ID = mnt.id
WHERE
	mnt.delete_date IS NULL
	AND mnt.ENABLED = 1    
	/*IF customerId != null*/
	AND mnt.m_customer_type_id = /*customerId*/
	/*END*/
    AND mntl.link_alias = /*linkAlias*/	
    and mntl.M_LANGUAGE_CODE = /*languageCode*/
	
