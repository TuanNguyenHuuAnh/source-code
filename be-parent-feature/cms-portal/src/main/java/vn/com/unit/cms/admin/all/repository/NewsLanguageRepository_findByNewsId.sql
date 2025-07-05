SELECT *
FROM m_news_language
WHERE delete_date is null
	AND m_news_id = /*newsId*/