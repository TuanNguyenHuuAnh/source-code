select * from M_CONTEST_DETAIL detail
inner join M_CONTEST_SUMMARY contest
on contest.MEMO_NO = detail.MEMO_NO
WHERE 
	contest.IS_CHALLENGE=0
	AND contest.DELETED_DATE is null
	AND contest.ENABLED=1
	/*IF searchDto.agentCode != null*/
	AND detail.AGENT_CODE= /*searchDto.agentCode*/
	/*END*/
	/*IF searchDto.contestType != null*/
	AND contest.CONTEST_TYPE= /*searchDto.contestType*/
	/*END*/
	AND CONVERT(DATE, contest.EFFECTIVE_DATE) <= CONVERT(DATE, GETDATE())
	AND CONVERT(DATE, ISNULL(contest.EXPIRED_DATE, GETDATE())) >= CONVERT(DATE, GETDATE())