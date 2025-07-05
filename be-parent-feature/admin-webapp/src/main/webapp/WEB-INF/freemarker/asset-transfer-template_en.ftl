<HTML>
<BODY>
	Dear <b>${dearFullname}</b>,<br/><br/>
	<#if nextStepNo == 1 >
	Kindly be informed that requested Asset Transfer ${transferNo} has been submitted by <b>${actionFullName}</b> successfully. <br/>
	Please access to ${url} to view.
	<#-- 2. Khi Verifier Verify  -->
	<#elseif nextStepNo == 99 > 
	Kindly be informed that requested Asset Transfer ${transferNo} has been verified by <b>${actionFullName}</b> <br/>
	Please access to ${url} to view.
	<#-- 3. Khi PM Asset duoc  Verify  -->
	<#elseif nextStepNo == 2 > 
	Kindly be informed that requested Asset Transfer ${transferNo} has been approved by checker <b>${actionFullName}</b> <br/>
	Please access to ${url} to view.
	<#-- 3. Khi PM Asset duoc  HOD  -->
	<#elseif nextStepNo == 3 > 
	Kindly be informed that requested Asset Transfer ${transferNo} has been approved by  hod <b>${actionFullName}</b> <br/>
	Please access to ${url} to view.
	<#-- 3. Khi Rejected  -->
	<#elseif nextStepNo == 98 > 
	Kindly be informed that your requested Asset Transfer ${transferNo} has been rejected by <b>${actionFullName}</b> with comment "<i><b>${comment}</b></i>" <br/>
	Please access to ${url} to view.
	</#if>
	<br/><br/>
	Thanks & Best Regards,<br/>
	${systemName}<br/><br/>
</BODY>
</HTML>