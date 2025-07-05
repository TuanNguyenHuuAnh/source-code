<HTML>

<head>
<style>
table {
  border-collapse: collapse;
}
 td, th {
  border: 1px solid black;
  padding: 3px 5px 3px 5px;
}

</style>
</head>

<BODY>
    Dear <b>${checkerFullName}</b>,
    <br/>
    <br/>
    Kindly be informed that the following Purchase Order <b>${poName}</b> has been ${typeSubmit} by <b>${currentUser.fullname}</b> successfully.
    <br/>
    Please go to ${url} to view.
    <br/>
    <br/>
    <table>
		<tr>
			<th>Vendor</th>
			<th>Goods / Services Description</th>
			<th>Unit</th>
			<th>Quantity</th>
			<th>Unit Price<br/>(${currencyCode})</th>
			<th>Amount<br/>(${currencyCode})</th>
		</tr>
		<#list prdetails as prdetail>
			<tr>
				<td>${vendorName}</td>
				<td>${prdetail.poDescription}</td>
				<td>${prdetail.uomName}</td>
				<td style="text-align: right;">${prdetail.approvedQuantity}</td>
				<td style="text-align: right;">${prdetail.unitPrice}</td>
				<td style="text-align: right;">${prdetail.approvedAmount}</td>
			</tr>
		</#list>
	
		<tr>
			<td style="text-align: right;" colspan="5" ><b>Total amount after VAT ${vat}</b></td>
			<td style="text-align: right;">${totalAmount}</td>
		</tr>
    </table>    
	<br/> <br/>
	Note: You can also ${actionLower} this Purchase Order directly by replying this email with <b>"${actionUpper}"</b>. 
	<br/>
	<br/>
    Thanks & Best Regards,<br/>             
    ${systemName}
</BODY>
</HTML>