SELECT
	homepage.id								AS id
	, homepage.speed_roll 					AS speed_roll
	, homepage.effective_date_from			AS effective_date_from
	, homepage.effective_date_to			AS effective_date_to
	, homepage.status_code 					AS status_code
	, homepage.process_id 					AS process_id
	, homepage.description					AS description
	, homepage.m_banner_top_id				AS banner_top_id
	, homepage.m_banner_fix_id 				AS banner_fix_id
	, homepage.m_banner_top_mobile_id 		AS banner_top_mobile_id
	, homepage.m_banner_fix_mobile_id 		AS banner_fix_mobile_id
	, homepage.note							AS note
	, homepage.banner_page					AS banner_page
	, homepage.status						AS status
FROM m_homepage_setting homepage
	LEFT JOIN
	    jca_constant_display acc_code
	ON
	    acc_code.type = 'B01'
	    AND acc_code.cat = homepage.banner_page
WHERE
	homepage.delete_date is NULL
		
	/*IF id != null*/
	AND homepage.id	 = /*id*/
	/*END*/
ORDER BY
	homepage.create_date DESC
