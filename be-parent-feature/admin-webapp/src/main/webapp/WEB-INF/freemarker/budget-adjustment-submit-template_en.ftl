<#if nextStepNo == 1>
	<HTML>
	<BODY>
		Dear <b>${fullname}</b>,
		<br/>
		<br/>
		Kindly be informed that requested budget adjustment has been submitted by <b>${user}</b> successfully.	
		<br/>
		Please access to <b>${url}</b> to view.	
		<br/>
		<br/>
		Thanks & Best Regards,<br/>				
		${systemName}
	</BODY>
	</HTML>
</#if>

<#if nextStepNo == 98>
	<HTML>
	<BODY>
		Dear <b>${fullname}</b>,
		<br/>
		<br/>
		Kindly be informed that your requested budget adjustment has been rejected by <b>${user}</b> with comment <b>"<i>${comment}</i>"</b>
		<br/>
		Please access to <b>${url}</b> to view.
		<br/>
		<br/>
		Thanks & Best Regards,<br/>				
		${systemName}
	</BODY>
	</HTML>

</#if>

<#if nextStepNo == 99>
	<HTML>
	<BODY>
		Dear <b>${fullname}</b>,
		<br/><br/>
		Kindly be informed that your requested budget adjustment has been verified by <b>${user}</b> 	
		<br/>
		Please access to <b>${url}</b> to view.
		<br/><br/>
		Thanks & Best Regards,<br/>				
		${systemName}
	</BODY>
	</HTML>

</#if>