<HTML>
<BODY>
	Dear <b>${dearFullname}</b>,<br/>
	<br/>
	
	<#if nextStepNo == 1 >
	Kindly be informed that requested Asset Return <b>${transactionNo}</b> has been submitted by <b>${fullname}</b> successfully. <br/>
	Please access to ${url} to view.
	
	<#-- 2. Khi Verifier Verify  -->
	<#elseif nextStepNo == 99 > 
	Kindly be informed that your requested Asset Return <b>${transactionNo}</b> has been verified by <b>${fullname}</b> <br/>
	Please access to ${url} to view.
		
	<#-- 3. Khi Rejected  -->
	<#elseif nextStepNo == 98 > 
	Kindly be informed that your requested Asset Return <b>${transactionNo}</b> has been rejected by <b>${fullname}</b> with comment "<i><b>${comment}</b></i>" <br/>
	Please access to ${url} to view.
	
	<#else>
		Kindly be informed that Asset Return <b>${transactionNo}</b> has been updated by <b>${fullname}</b> successfully. <br/>
		Please access to ${url} to view.

	</#if>
	
	<br/><br/>
	
	Thanks & Best Regards,<br/>
	${systemName}<br/><br/>
	
</BODY>
</HTML>