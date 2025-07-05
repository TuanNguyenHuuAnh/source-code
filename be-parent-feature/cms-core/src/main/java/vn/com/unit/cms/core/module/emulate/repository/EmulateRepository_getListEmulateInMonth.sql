SELECT 
	tb.ID															AS ID
	, tb.MEMO_NO													AS MEMO_CODE
	, tb.CONTEST_NAME												AS TITLE
	, tb.description 												AS description

FROM M_CONTEST_SUMMARY tb
INNER JOIN M_CONTEST_APPLICABLE_DETAIL detail
on tb.id=detail.CONTEST_ID
WHERE
	tb.DELETED_DATE is null
	AND CONVERT(DATE, tb.START_DATE) <= CONVERT(DATE, GETDATE())
	AND CONVERT(DATE, ISNULL(tb.END_DATE, GETDATE())) >= CONVERT(DATE, GETDATE())
	AND CONVERT(DATE, tb.EFFECTIVE_DATE) <= CONVERT(DATE, GETDATE())
	AND CONVERT(DATE, ISNULL(tb.EXPIRED_DATE, GETDATE())) >= CONVERT(DATE, GETDATE())
	--AND MONTH(GETDATE()) = MONTH(tb.EFFECTIVE_DATE)
	AND tb.ENABLED = 1
	/*IF searchDto.agentCode != null && searchDto.agentCode != ''*/
	AND detail.AGENT_CODE=/*searchDto.agentCode*/
	/*END*/
ORDER BY tb.CODE desc

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/
