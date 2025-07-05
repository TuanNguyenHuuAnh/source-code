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
    Dear <b>${acc.fullname}</b>,
    <br/><br/>
    
    	Kindly be informed that the following <b>[${param.paymentTypeName}] ${param.refNo}</b> is due to submit Invoice</b> <br/>
		
		Please go to ${param.url} to attach Invoices, supporting documents and send the original to FIN staff.
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
				<td style="text-align: right;">${param.totalInvoiceAmount}</td>
				<td style="text-align: right;">${param.paymentAmount}</td>
				<#if param.currency != "VND">
					<td style="text-align: right;">${param.amount}</td>
				</#if>
			</tr>
	    </table>
	
    <br/><br/>
    
    Thanks & Best Regards,<br/> 
    ${systemName}
    <br/>
	<br/>
    Note: Please don't reply this email.
</BODY>
</HTML>