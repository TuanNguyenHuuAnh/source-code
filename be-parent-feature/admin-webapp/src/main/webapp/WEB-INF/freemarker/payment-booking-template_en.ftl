<HTML>
<BODY>
	Dear <b>${fullname}</b>,<br/>
	<br/>
	
	<#if param.nextStepNo == 1 >
	Kindly be informed that requested Payment Booking has been submitted by <b>${param.userRequest}</b> successfully. <br/>
	Please access to ${url} to view.
	
	<#-- 2. Khi Verifier Verify  -->
	<#elseif param.nextStepNo == 99 > 
	Kindly be informed that your requested Payment Booking has been verified by <b>${param.userRequest}</b> <br/>
	Please access to ${url} to view.
		
	<#-- 3. Khi Rejected  -->
	<#elseif param.nextStepNo == 98 > 
	Kindly be informed that your requested Payment Booking has been rejected by <b>${param.userRequest}</b> with comment "<i><b>${param.comment}</b></i>" <br/>
	Please access to ${url} to view.
	</#if>
	
	<br/><br/>
	<#if actionLower??>
		Note: You can also ${param.actionLower} this Payment Booking directly by replying this email with <b>"${param.actionUpper}"</b>.
		<br/><br/>
	</#if>
	
	Thanks & Best Regards,<br/>
	${param.systemName}<br/><br/>
	
</BODY>
</HTML>