SELECT
	count(1)
FROM
    jca_item item
LEFT JOIN
    vw_authority_pivot m_author_pivot
ON
    m_author_pivot.item_id = item.id
    AND m_author_pivot.role_id = /*jcaAuthoritySearchDto.roleId*/
WHERE
    item.deleted_date IS NULL
    --AND item.display_flag = '1'
    /*IF jcaAuthoritySearchDto.companyId != null*/
	AND (item.company_id is null or item.company_id = /*jcaAuthoritySearchDto.companyId*/1)
	/*END*/
	/*IF jcaAuthoritySearchDto.companyId == null*/
	AND item.company_id is null
	/*END*/
	AND ITEM.FUNCTION_TYPE in /*jcaAuthoritySearchDto.functionTypes*/()
	
