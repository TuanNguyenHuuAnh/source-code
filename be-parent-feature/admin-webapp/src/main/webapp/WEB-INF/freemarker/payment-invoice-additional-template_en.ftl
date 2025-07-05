<HTML>
	<head>
		<style>
			table {
			  border-collapse: collapse;
			  width: 685px;
			}
			 td, th {
			  border: 1px solid black;
			  padding: 3px 5px 3px 5px;
			}
		</style>
	</head>

	<BODY>
		Dear <b>${dearFullname}</b>,<br/>
		<br/>
			<#if nextStepNo == 1 >
			Kindly be informed that Payment Requisition <b>${refNo}</b> has been submitted invoice by <b>${fullname}</b> <br/>
			Please access to ${link} to view.
			
			<#-- 2. Khi Verifier Verify  -->
			<#elseif nextStepNo == 99 > 
			Kindly be informed that Payment Requisition <b>${refNo}</b> has been verified by <b>${fullname}</b> <br/>
			Please access to ${link} to view.
			<#-- 2. Khi reject  -->
			<#elseif nextStepNo == 98 > 
			Kindly be informed that additional invoice for <b>${refNo}</b> has been rejected by <b>${fullname}</b> with comment <b>"${comment}"</b> <br/>
			Please access to ${link} to view.
			</#if>
	
		<br/><br/>
	    Thanks & Best Regards,<br/>
	    ${systemName}<br/><br/>
	</BODY>
</HTML>
