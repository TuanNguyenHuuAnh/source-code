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
			.text-right{
				text-align: right;
			}
			.bold{
				font-weight: bold;
			}
			.text-center{
				text-align: center;
			}
			tr.total > td {
				text-align: right;
			}
			tr.total-final > td {
				text-align: right;
				font-weight: bold;
			}
		</style>
	</head>

	<BODY>
		Dear <b>${param.fullName}</b>,<br/>
		<br/>
			<#-- 1. Khi Requester Submit  -->
			<#if param.nextStepNo == 1 >
				Kindly be informed that a new Quotation Summary <b>${param.quotationNo}</b> has been created by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			<#-- 2. Khi Procurement Verify  -->
			<#elseif param.buttonId == param.paymentEnum.BTN_PROCUMENT_MANAGER_APPROVE> 
				Kindly be informed that the below Quotation Summary <b>${param.quotationNo}</b> has been verified by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
				
			<#-- 2. Khi Verifier Verify  -->
			<#elseif param.buttonId == param.paymentEnum.BTN_VERIFY_VERIFY> 
				Kindly be informed that the below Quotation Summary <b>${param.quotationNo}</b> has been verified by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 3. Khi HOD Approve, 4. Khi Procurement Manager Approve -->
			<#elseif param.buttonId == param.paymentEnum.BTN_HOD_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_PROCUMENT_MANAGER_APPROVE>
				Kindly be informed that Quotation Summary <b>${param.quotationNo}</b> has been approved by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 5. Khi CFO Approve, 7. Khi Finance Assistant Manager Approve-->
			<#elseif param.buttonId == param.paymentEnum.BTN_CFO_APPROVE 
				|| param.buttonId == param.paymentEnum.BTN_CEO_APPROVE
				|| param.buttonId == param.paymentEnum.BTN_FINANCE_MANAGER_APPROVE >
				Kindly be informed that Quotation Summary <b>${param.quotationNo}</b> has been approved by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.
			
			<#-- 8. Khi Verifier Reject, 9. Khi HOD Reject, 12. Khi Procurement Manager Reject,  14. Khi CFO Reject, 16. Khi Finance Staff Reject-->
			<#elseif param.nextStepNo == 98>
				Kindly be informed that Quotation Summary <b>${param.quotationNo}</b> has been rejected by <b>${param.userRequest}</b> with comment "<i><b>${param.comment}</b></i>"<br/>
				Please go to ${url} to view.
			
			<#-- 10. Khi HOD Return, 11. Khi Procurement Manager Return, 13. Khi CFO Return, 15. Khi Finance Staff Return, 17. Khi Finance Assistant Manager Return -->
			<#elseif param.nextStepNo == 91
				|| param.nextStepNo == 92 
				|| param.nextStepNo == 93 
				|| param.nextStepNo == 94 
				|| param.nextStepNo == 95 
				|| param.nextStepNo == 96 >
				Kindly be informed that Quotation Summary <b>${param.quotationNo}</b> has been returned by <b>${param.userRequest}</b> with comment "<i><b>${param.comment}</b></i>"<br/>
				Please go to ${url} to re-validate, edit info and submit again.

			<#-- 18. Khi Requester Submit sau khi  b\u1ecb HOD Return, 19. Khi Requester Submit sau khi  b\u1ecb Procurement Manager Return, 
				20. Khi Requester Submit sau khi  b\u1ecb CFO Return , 21. Khi Requester Submit sau khi  b\u1ecb Finance Staff Return  -->
			<#else>
				Kindly be informed that Quotation Summary <b>${param.quotationNo}</b> has been updated by <b>${param.userRequest}</b> successfully.<br/>
				Please access to ${url} to view.

			</#if>
			
	    <br/><br/>
	    
	    <table>
			<tr>
				<th>Subject</th>
				<th>Vendor Nominated</th>
				<th>Estimated Total Value (${param.currency})</th>
			</tr>
			<tr>
				<td>${param.subject}</td>
				<td>${param.vendorNominateName}</td>
				<td class="text-right">${param.totalValue}</td>
			</tr>
	    </table>
	    
		
		<br/>
	    <br/>
		<b>Reasons to nominate this vendor</b>
		<br/>
		<table>
			<tr>
				<td>${param.recommendation}</td>
			</tr>
		</table>
		
		<br/>
	    <br/>
		<b>Goods/Services Information</b>
		<#if param.type == 0>
			<table>
				<tr>
					<th>Descriptions</th>
					<th>Quantity</th>
					<th>Unit</th>
					<th>Final price</th>
				</tr>
				<#list param.lstGoodService as item>
					<tr>
						<td>${item.description}</td>
						<td class="text-center">${item.quantity}</td>
						<td class="text-center">${item.unitName}</td>
						<td class="text-right">${item.price}</td>
					</tr>
				</#list>
				<tr class="total-final">
                	<td colspan="3" class="text-right bold">Total Cost (include VAT)</td>
                	<td colspan="3" class="text-right">${param.totalCostVat}</td>
                </tr>
		    </table>
		<#else>
			<table>
				<tr>
					<th>PR No.</th>
					<th>Descriptions</th>
					<th>Quantity</th>
					<th>Unit</th>
					<th>Final price</th>
				</tr>
				<#list param.lstGoodService as item>
					<tr>
						<td>${item.prNo}</td>
						<td>${item.description}</td>
						<td class="text-center">${item.quantity}</td>
						<td class="text-center">${item.unitName}</td>
						<td class="text-right">${item.price}</td>
					</tr>
				</#list>
				<tr class="total-final">
                	<td colspan="4" class="text-right bold">Total Cost (include VAT)</td>
                	<td colspan="4" class="text-right">${param.totalCostVat}</td>
                </tr>
		    </table>
		</#if>
		
		<br/>
		<br/>
		
		<b>Quotation Comparison</b>
	    <table>
			<tr>
				<th>Vendor</th>
				<th>Estimated Total Value (VND, VAT / Tax Included)</th>
				<th>Description</th>
			</tr>
			<#list param.lstVendor as item>
				<tr>
					<td>${item.vendorName}</td>
					<td class="text-right">${item.totalValue}</td>
					<td>${item.description}</td>
				</tr>
			</#list>
	    </table>
	    
	    <br/>
		<br/>
		<#if param.actionLower??>
		   Note: You can also ${param.actionLower} this Quotation Summary directly by replying this email with <b>"${param.actionUpper}"</b>.
		   <br/><br/>
		</#if>
	    Thanks & Best Regards,<br/>             
	    ${param.systemName}<br/><br/>
	</BODY>
</HTML>
