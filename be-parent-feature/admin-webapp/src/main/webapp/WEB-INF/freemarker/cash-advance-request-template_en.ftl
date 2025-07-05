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
		Dear <b>${param.fullName}</b>,<br/>
		<br/>
			<#-- 1. Khi Requester Submit  -->
			<#if param.nextStepNo == 1 || isSMT>
				Kindly be informed that a new Cash Advance Request <b>${param.paymentNo}</b> has been submitted by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 2. Khi Verifier Verify  -->
			<#elseif param.buttonId == param.paymentEnum.BTN_CA_VERIFY_VERIFY> 
				Kindly be informed that Cash Advance Request <b>${param.paymentNo}</b> has been verified by <b>${param.userRequest}</b> <#if param.comment != "">with comment "<i><b>${param.comment}</b></i>"</#if> <br/>
				Please access to ${url} to view.
				
			<#-- 3. Khi CFO Approve, 6. Khi Finance Staff Approve, 7. Khi Finance Assistant Manager Approve-->
			<#elseif param.buttonId == param.paymentEnum.BTN_CA_HOD_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_CA_CEO_APPROVE
				|| param.buttonId == param.paymentEnum.BTN_CA_CFO_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_CA_FINANCE_STAFF_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_CA_FINANCE_MANAGER_APPROVE >
				Kindly be informed that Cash Advance Request <b>${param.paymentNo}</b> has been approved by <b>${param.userRequest}</b> <#if param.comment != "">with comment "<i><b>${param.comment}</b></i>"</#if> <br/>
				Please access to ${url} to view.
			
			<#-- 8. Khi Verifier Reject, 9. Khi HOD Reject, 12. Khi Procurement Manager Reject,  14. Khi CFO Reject, 16. Khi Finance Staff Reject-->
			<#elseif param.nextStepNo == 98>
				Kindly be informed that Cash Advance Request <b>${param.paymentNo}</b> has been rejected by <b>${param.userRequest}</b> <#if param.comment != "">with comment "<i><b>${param.comment}</b></i>"</#if> <br/>
				Please access to ${url} to view.
			
			<#-- 10. Khi Return -->
			<#elseif param.nextStepNo == 92 
			|| param.nextStepNo == 93 
			|| param.nextStepNo == 94 
			|| param.nextStepNo == 95 
			|| param.nextStepNo == 96>
				Kindly be informed that Cash Advance Request <b>${param.paymentNo}</b> has been returned by <b>${param.userRequest}</b> <#if param.comment != "">with comment "<i><b>${param.comment}</b></i>"</#if> <br/>
				Please access to ${url} to view.

			<#-- 18. Khi Requester Submit sau khi  b\u1ecb HOD Return, 19. Khi Requester Submit sau khi  b\u1ecb Procurement Manager Return, 
				20. Khi Requester Submit sau khi  b\u1ecb CFO Return , 21. Khi Requester Submit sau khi  b\u1ecb Finance Staff Return  -->
			<#else>
				Kindly be informed that Cash Advance Request <b>${param.paymentNo}</b> has been updated by <b>${param.userRequest}</b> <#if param.comment != "">with comment "<i><b>${param.comment}</b></i>"</#if> <br/>
				Please access to ${url} to view.

			</#if>
			
	    <br/><br/>
	    
		<table>
			<tr>
				<th>Receiver</th>
				<th>Purpose</th>
				<th>Amount (VND)</th>
			</tr>
			<tr>
				<td style="text-align: left;">${param.employeeName}</td>
				<td style="text-align: left;">${param.purpose}</td>
				<td style="text-align: right;">${param.payableAmount}</td>
			</tr>
	    </table>
	    
		<br/>
		
		<#if param.actionLower??>
		   Note: You can also ${param.actionLower} this Cash Advance Request directly by replying this email with <b>"${param.actionUpper}"</b>.
		   <br/><br/>
		</#if>
		
	    Thanks & Best Regards,<br/>
	    ${param.systemName}<br/><br/>
	</BODY>
</HTML>
