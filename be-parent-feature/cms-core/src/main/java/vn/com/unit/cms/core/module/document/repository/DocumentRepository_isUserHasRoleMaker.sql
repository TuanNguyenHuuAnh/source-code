SELECT  count(1)
	FROM VW_AUTHORITY_DETAIL vw
	INNER JOIN JCA_ACCOUNT acc
		ON ACC.USERNAME = vw.username
	inner join JCA_ROLE role
		ON role.ID = VW.ROLE_ID
	WHERE
		VW.ACTIVED = 1
		AND ROLE_ACTIVED = 1
		and ACC.USERNAME = /*username*/'linhmtt_moderator'
		and VW.ROLE_ID = 4
	GROUP BY ACC.id, vw.USERNAME, VW.ROLE_ID, role.NAME;