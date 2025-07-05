SELECT * 
FROM M_CONTEST_SUMMARY tb
	INNER JOIN M_CONTEST_DETAIL detail
	ON tb.MEMO_NO=detail.MEMO_NO
	WHERE tb.IS_CHALLENGE=0
	AND tb.DELETED_DATE is null
	AND tb.ENABLED=1
	/*IF searchDto.contestType != null && searchDto.contestType != ''*/
	AND tb.CONTEST_TYPE= /*searchDto.contestType*/''
	/*END*/
	/*IF searchDto.agentCode != null && searchDto.agentCode != ''*/
	AND detail.AGENT_CODE=/*searchDto.agentCode*/''
	/*END*/
	AND CONVERT(DATE, tb.EFFECTIVE_DATE) <= CONVERT(DATE, GETDATE())
	AND CONVERT(DATE, ISNULL(tb.EXPIRED_DATE, GETDATE())) >= CONVERT(DATE, GETDATE())
/*IF orders != null*/
ORDER BY /*$orders*/tb.ID
-- ELSE ORDER BY ISNULL(tb.updated_date, tb.created_date) DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/