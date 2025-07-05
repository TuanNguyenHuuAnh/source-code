<HTML>
<BODY>
	Dear <b>${dearFullname}</b>,<br/>
	<br/>
	
	<#if nextStepNo == 1 || isSMT>
	Kindly be informed that a new Travel Request ${transactionNo} has been submitted by <b>${userRequest}</b> successfully. <br/>
	Please access to ${url} to view.
	
	<#-- 2. Khi Verify -->
	<#elseif buttonId == buttonEnum.BTN_VERIFY_VERIFY
		|| buttonId == buttonEnum.BTN_CORPORATE_SERVICE_SUBMIT> 
	Kindly be informed that Travel Request ${transactionNo} has been verified by <b>${userRequest}</b> <#if comment != "">with comment "<i><b>${comment}</b></i>"</#if> <br/>	
	Please access to ${url} to view.
	
	<#-- 3. Khi HOD Approve, CEO Approve  -->
	<#elseif buttonId == buttonEnum.BTN_HOD_APPROVE
		|| buttonId == buttonEnum.BTN_CEO_APPROVE> 
	Kindly be informed that Travel Request ${transactionNo} has been approved by <b>${userRequest}</b> <#if comment != "">with comment "<i><b>${comment}</b></i>"</#if> <br/>
	Please access to ${url} to view.
		
	<#-- 4. Khi Rejected  -->
	<#elseif nextStepNo == 98 
		|| nextStepNo == 96> 
	Kindly be informed that Travel Request ${transactionNo} has been rejected by <b>${userRequest}</b> <#if comment != "">with comment "<i><b>${comment}</b></i>"</#if> <br/>
	Please access to ${url} to view.
	
	<#-- 5. Khi Cancel-->
	<#elseif buttonId == buttonEnum.BTN_REQUESTER_CANCEL> 
	Kindly be informed that Travel Request ${transactionNo} has been cancalled by <b>${userRequest}</b> <#if comment != "">with comment "<i><b>${comment}</b></i>"</#if> <br/>
	Please access to ${url} to view.
	
	<#-- 6. Khi Corporate Serive Confirm Cancel-->
	<#elseif buttonId == buttonEnum.BTN_CORPORATE_SERVICE_CONFIRM_CANCEL> 
	Kindly be informed that Travel Request ${transactionNo} has been confirmed to cancel by <b>${userRequest}</b> <#if comment != "">with comment "<i><b>${comment}</b></i>"</#if> <br/>
	Please access to ${url} to view.
	
	<#-- 7. Khi Requester Complete-->
	<#elseif buttonId == buttonEnum.BTN_REQUESTER_COMPLETE || nextStepNo == 99> 
	Kindly be informed that Travel Request ${transactionNo} has been completed by <b>${userRequest}</b> <#if comment != "">with comment "<i><b>${comment}</b></i>"</#if> <br/>
	Please access to ${url} to view.
	
	<#else>
		Kindly be informed that Travel Request ${transactionNo} has been updated by <b>${userRequest}</b> successfully. <br/>
		Please access to ${url} to view.

	</#if>
	
	<br/><br/>
	
	<table style="border-collapse: collapse; width: 685px;">
		<tr>
			<th style="border: 1px solid black;padding: 3px 5px 3px 5px;">Traveler</th>
			<th style="border: 1px solid black;padding: 3px 5px 3px 5px;">Destination(s)</th>
			<th style="border: 1px solid black;padding: 3px 5px 3px 5px;">From date</th>
			<th style="border: 1px solid black;padding: 3px 5px 3px 5px;">To Date</th>
		</tr>
		<#list travelRequestDetails as item>
			<tr>
				<#if item?index == 0>
				<td style="border: 1px solid black; padding: 3px 5px 3px 5px;" rowspan="${travelRequestDetails?size}">${nameOfTraveler}</td>
				</#if>
				<td style="border: 1px solid black; padding: 3px 5px 3px 5px;">${item.destination}</td>
				<td style="border: 1px solid black; padding: 3px 5px 3px 5px; text-align: center;">${item.fromDate?string["dd/MM/yyyy"]}</td>
				<td style="border: 1px solid black; padding: 3px 5px 3px 5px; text-align: center;">${item.toDate?string["dd/MM/yyyy"]}</td>
			</tr>
		</#list>
    </table>
    
	<br/>
	<#if purpose??>
    	<b>Purpose</b><br/>
    	<table style="border-collapse: collapse; width: 685px;">
			<tr>
				<td style="border: 1px solid black; padding: 3px 5px 3px 5px;">
					<pre style='font-size:12.0pt;font-family:"Times New Roman",serif'>${purpose}</pre>
				</td>
			</tr>
	    </table>
	    <br/>
    </#if>
	<br/>
	
	<#if actionLower??>
	   Note: You can also ${actionLower} this Travel Request directly by replying this email with <b>"${actionUpper}"</b>.
	   <br/><br/>
	</#if>
	
	Thanks & Best Regards,<br/>
	${systemName}<br/><br/>
	
</BODY>
</HTML>