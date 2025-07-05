SELECT count(*)
FROM M_CONTEST_SUMMARY tb
	INNER JOIN M_CONTEST_DETAIL detail
	ON tb.MEMO_NO=detail.MEMO_NO
	WHERE IS_CHALLENGE=0
	AND tb.DELETED_DATE is null
	AND tb.ENABLED=1
	/*IF searchDto.contestType != null && searchDto.contestType != ''*/
	AND tb.CONTEST_TYPE= /*searchDto.contestType*/'Ngắn hạn'
	/*END*/
	/*IF searchDto.agentCode != null && searchDto.agentCode != ''*/
	AND detail.AGENT_CODE=/*searchDto.agentCode*/'130747'
	/*END*/
	AND CONVERT(DATE, EFFECTIVE_DATE) <= CONVERT(DATE, GETDATE())
	AND CONVERT(DATE, ISNULL(EXPIRED_DATE, GETDATE())) >= CONVERT(DATE, GETDATE())