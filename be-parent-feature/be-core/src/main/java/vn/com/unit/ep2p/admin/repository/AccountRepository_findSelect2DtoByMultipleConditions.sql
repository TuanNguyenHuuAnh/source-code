SELECT
    m_acc.id AS id,
    concat(m_acc.FULLNAME, concat(' (', concat(m_acc.EMAIL, ')'))) AS name,
    concat(m_acc.FULLNAME, concat(' (', concat(m_acc.EMAIL, ')'))) AS text
FROM
    jca_account m_acc
WHERE m_acc.DELETED_ID = 0
	AND m_acc.ENABLED = 1
	AND m_acc.company_id = /*companyId*/
	/*IF key != null && key != ''*/
	AND (UPPER(m_acc.username) like concat('%',  concat(UPPER(/*key*/''), '%'))
		OR UPPER(m_acc.FULLNAME) like concat('%',  concat(UPPER(/*key*/''), '%'))
		OR UPPER(m_acc.EMAIL) like concat('%',  concat(UPPER(/*key*/''), '%')))
	/*END*/
ORDER BY m_acc.FULLNAME
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/