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
			<#if param.nextStepNo == 1 >
				Kindly be informed that a new Payment Travel Request <b>${param.paymentNo}</b> has been created by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 2. Khi Verifier Verify  -->
			<#elseif param.buttonId == param.paymentEnum.BTN_VERIFY> 
				Kindly be informed that the below Payment Travel Request <b>${param.paymentNo}</b> has been verified by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 3. Khi HOD Approve -->
			<#elseif param.buttonId == param.paymentEnum.BTN_HOD_APPROVE >
				Kindly be informed that the following Payment Travel Request <b>${param.paymentNo}</b> has been approved by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 5. Khi CFO Approve, 6. Khi Finance Staff Approve, 7. Khi Finance Assistant Manager Approve-->
			<#elseif param.buttonId == param.paymentEnum.BTN_CFO_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_CEO_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_CA_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_FINANCE_STAFF_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_FINANCE_MANAGER_APPROVE >
				Kindly be informed that Payment Travel Request <b>${param.paymentNo}</b> has been approved by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 8. Khi Verifier Reject, 9. Khi HOD Reject, 14. Khi CFO Reject, 16. Khi Finance Staff Reject-->
			<#elseif param.buttonId == param.paymentEnum.BTN_VERIFY_REJECT 
				|| param.buttonId == param.paymentEnum.BTN_HOD_REJECT
				|| param.buttonId == param.paymentEnum.BTN_CFO_REJECT
				|| param.buttonId == param.paymentEnum.BTN_CEO_REJECT
				|| param.buttonId == param.paymentEnum.BTN_CA_REJECT
				|| param.buttonId == param.paymentEnum.BTN_FINANCE_STAFF_REJECT >
				Kindly be informed that the following Payment Travel Request <b>${param.paymentNo}</b> has been rejected by <b>${param.userRequest}</b> with comment "<i><b>${param.comment}</b></i>"<br/>
				Please go to ${url} to re-validate, edit information and submit again.
			
			<#-- 10. Khi HOD Return, 13. Khi CFO Return, 15. Khi Finance Staff Return, 17. Khi Finance Assistant Manager Return -->
			<#elseif param.buttonId == param.paymentEnum.BTN_HOD_RETURN 
				|| param.buttonId == param.paymentEnum.BTN_CFO_RETURN
				|| param.buttonId == param.paymentEnum.BTN_CEO_RETURN
				|| param.buttonId == param.paymentEnum.BTN_CA_RETURN
				|| param.buttonId == param.paymentEnum.BTN_FINANCE_STAFF_RETURN
				|| param.buttonId == param.paymentEnum.BTN_FINANCE_MANAGER_RETURN >
				Kindly be informed that the following Payment Travel Request <b>${param.paymentNo}</b> has been returned by <b>${param.userRequest}</b> with comment "<i><b>${param.comment}</b></i>"<br/>
				Please go to ${url} to re-validate, edit info and submit again.

			<#-- 18. Khi Requester Submit sau khi HOD Return, 19. Khi Requester Submit sau khi Procurement Manager Return, 
				20. Khi Requester Submit sau khi CFO Return , 21. Khi Requester Submit sau khi Finance Staff Return  -->
			<#else>
				Kindly be informed that Payment Travel Request <b>${param.paymentNo}</b> has been updated by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.

			</#if>
			
	    <br/><br/>
	    
	    <table>
			<tr>
				<th>Invoice Desciption</th>
				<th>Invoice Amount</th>
			</tr>
			<#list param.invoiceInforList as item>
				<tr>
					<td>${item.description}</td>
					<td style="text-align: right;">${item.invoiceAmount}</td>
				</tr>
			</#list>
			<tr>
				<td style="text-align: right;"><b>Total</b></td>
				<td style="text-align: right;">${param.invoiceAmount}</td>
			</tr>
	    </table>
	    <br/>
	    
	    <#if param.purpose??>
	    	<b>Purpose</b><br/>
	    	<table>
				<tr>
					<td>
						<pre style='font-size:12.0pt;font-family:"Times New Roman",serif'>${param.purpose}</pre>
					</td>
				</tr>
		    </table>
		    <br/>
	    </#if>
		<br/>
		<#if param.actionLower??>
		   Note: You can also ${param.actionLower} this Payment Travel Request directly by replying this email with <b>"${param.actionUpper}"</b>.
		   <br/><br/>
		</#if>
	    Thanks & Best Regards,<br/>
	    ${param.systemName}<br/><br/>
	</BODY>
</HTML>
