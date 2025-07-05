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
				Kindly be informed that a new Payment Requisition General Expense <b>${param.paymentNo}</b> has been created by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 2. Khi Verifier Verify  -->
			<#elseif param.buttonId == param.paymentEnum.BTN_GE_VERIFY_VERIFY> 
				Kindly be informed that the below Payment Requisition General Expense <b>${param.paymentNo}</b> has been verified by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 5. Khi CFO Approve, 6. Khi Finance Staff Approve, 7. Khi Finance Assistant Manager Approve-->
			<#elseif param.buttonId == param.paymentEnum.BTN_GE_HOD_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_GE_CFO_APPROVE
				|| param.buttonId == param.paymentEnum.BTN_GE_CEO_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_GE_CA_APPROVE  
				|| param.buttonId == param.paymentEnum.BTN_GE_FINANCE_STAFF_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_GE_FINANCE_MANAGER_APPROVE >
				Kindly be informed that Payment Requisition General Expense <b>${param.paymentNo}</b> has been approved by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 8. Khi Verifier Reject, 9. Khi HOD Reject, 14. Khi CFO Reject, 16. Khi Finance Staff Reject-->
			<#elseif param.buttonId == param.paymentEnum.BTN_GE_VERIFY_REJECT
				|| param.buttonId == param.paymentEnum.BTN_GE_HOD_REJECT 
				|| param.buttonId == param.paymentEnum.BTN_GE_CFO_REJECT
				|| param.buttonId == param.paymentEnum.BTN_GE_CEO_REJECT 
				|| param.buttonId == param.paymentEnum.BTN_GE_CA_REJECT  
				|| param.buttonId == param.paymentEnum.BTN_GE_FINANCE_STAFF_REJECT >
				Kindly be informed that the following Payment Requisition General Expense <b>${param.paymentNo}</b> has been rejected by <b>${param.userRequest}</b> with comment "<i><b>${param.comment}</b></i>"<br/>
				Please go to ${url} to re-validate, edit information and submit again.
			
			<#-- 10. Khi HOD Return, 13. Khi CFO Return, 15. Khi Finance Staff Return, 17. Khi Finance Assistant Manager Return -->
			<#elseif  param.buttonId == param.paymentEnum.BTN_GE_HOD_RETURN
				|| param.buttonId == param.paymentEnum.BTN_GE_CFO_RETURN
				|| param.buttonId == param.paymentEnum.BTN_GE_CEO_RETURN 
				|| param.buttonId == param.paymentEnum.BTN_GE_CA_RETURN  
				|| param.buttonId == param.paymentEnum.BTN_GE_FINANCE_STAFF_RETURN  
				|| param.buttonId == param.paymentEnum.BTN_GE_FINANCE_MANAGER_RETURN >
				Kindly be informed that the following Payment Requisition General Expense <b>${param.paymentNo}</b> has been returned by <b>${param.userRequest}</b> with comment "<i><b>${param.comment}</b></i>"<br/>
				Please go to ${url} to re-validate, edit info and submit again.

			<#-- 18. Khi Requester Submit sau khi  b\u1ecb HOD Return, 19. Khi Requester Submit sau khi  b\u1ecb Procurement Manager Return, 
				20. Khi Requester Submit sau khi  b\u1ecb CFO Return , 21. Khi Requester Submit sau khi  b\u1ecb Finance Staff Return  -->
			<#else>
				Kindly be informed that Payment Requisition General Expense <b>${param.paymentNo}</b> has been updated by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.

			</#if>
			
	    <br/><br/>

	    <table>
			<tr>
				<th>Invoice Desciption</th>
				<th>Invoice Amount (${param.currency})</th>
				<th>Payable Amount (${param.currency})</th>
				<#if param.currency != "VND">
					<th>Amount (VND)</th>
				</#if>
			</tr>
			<#list param.invoiceInforList as item>
				<tr>
					<td>${item.description}</td>
					<td style="text-align: right;">${item.invoiceAmount}</td>
					<td style="text-align: right;">${item.payableAmount}</td>
					<#if param.currency != "VND">
						<td style="text-align: right;">${item.amount}</td>
					</#if>
				</tr>
			</#list>
			<tr>
				<td style="text-align: right;"><b>Total</b></td>
				<td style="text-align: right;">${param.invoiceAmount}</td>
				<td style="text-align: right;">${param.payableAmount}</td>
				<#if param.currency != "VND">
					<td style="text-align: right;">${param.amount}</td>
				</#if>
			</tr>
	    </table>
		<br/>
		<#if param.purpose??>
	    	<b>Purpose</b><br/>
	    	<table>
				<tr>
					<td><pre style='font-size:12.0pt;font-family:"Times New Roman",serif'>${param.purpose}</pre></td>
				</tr>
		    </table>
		    <br/>
	    </#if>
		<b>Expense to be charged to</b><br/>
	    <table>
			<tr>
				<th>Budget Year</th>
				<th>Expense Type</th>
				<th>Cost Center</th>
				<th>Account Code</th>
				<th>Activity</th>
				<th>Amount(VND)</th>
				<th>Remaining Budget(VND)</th>
			</tr>
			<#list param.paymentBudgetList as budget>
				<tr>
					<td><#if budget.stringBudgetYear??>${budget.stringBudgetYear}</#if></td>
					<td><#if budget.expenseType??>${budget.expenseType}</#if></td>
					<td><#if budget.costCenterCode??>${budget.costCenterCode}</#if><#if budget.costCenterName??> - ${budget.costCenterName}</#if></td>
					<td><#if budget.accountCode??>${budget.accountCode}</#if><#if budget.accountName??> - ${budget.accountName}</#if></td>
					<td><#if budget.activityName??>${budget.activityName}<#else><#if budget.activityCode??>${budget.activityCode}</#if></#if></td>
					<td style="text-align: right;"><#if budget.amountVND??>${budget.amountVND}</#if></td>
					<td style="text-align: right;"><#if budget.remainingBudget??>${budget.remainingBudget}</#if></td>
				</tr>
			</#list>
	    </table>
		<br/>
		<#if param.actionLower??>
		   Note: You can also ${param.actionLower} this Payment Requisition General Expense directly by replying this email with <b>"${param.actionUpper}"</b>.
		   <br/><br/>
		</#if>
	    Thanks & Best Regards,<br/>
	    ${param.systemName}<br/><br/>
	</BODY>
</HTML>
