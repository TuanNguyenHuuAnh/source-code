UPDATE m_faqs_type
SET sort = /*cond.sortValue*/
WHERE id = /*cond.objectId*/
AND delete_date IS NULL