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
<p>  Dear <b>${receiverName}</b>,
    <br/>
    <br/>
    ${line1} <b> ${actionBy} </b> ${line1End} 
    <br/>
    ${line2Start} ${url} ${line2End}
    <br/><br/>
    
	<b>Cost Center Information</b><br/>
    <table border="1">
	    <tr>
	        <th style="width: 60px;">Budget Year</th>
	        <th style="width: 60px;">Expense Type</th>
	        <th>Cost Center</th>
	        <th style="min-width: 300px;">Account Code</th>
	        <th style="min-width: 100px;">Activity</th>
	        <th>Amount (VND)</th>
	        <th>Remaining Budget (VND)</th>
	    </tr>
	    <#list budgetList as budget>
	        <tr>
	            <td>${budget.budgetYear}</td>
				<td>${budget.expenseCatL1}</td>
				<td>${budget.costCenterCode} - ${budget.costCenterName}</td>
				<td>${budget.accountCode} - ${budget.accountName}</td>
				<#if budget.activityCode != "">
				<td>${budget.activityName}</td>
		        <#else>
		        <td></td>
				</#if>
				<td align="right">${budget.allocateAmount}</td>
				<td align="right">${budget.remainingBudget}</td>
	        </tr>
	    </#list>
    </table>
    <br/> 
    
    <b>Goods/Services Information</b><br/>
    <table border="1">
    <tr>
        <th>Goods / Services Description</th>
        <th>Unit</th>
        <th>Quantity</th>
        <th style="min-width: 100px;">Unit Price (VND)</th>
        <th>Amount (VND)</th>
    </tr>
    
	<#list prdetails as prdetail>
	    <tr>
	        <td>${prdetail.productDescription}</td>
			<td>${prdetail.uomName}</td>
			<td align="right">${prdetail.stockQuantity}</td>
			<td align="right">${prdetail.unitPrice}</td>
			<td align="right">${prdetail.amountVnd}</td>
	    </tr>
	</#list>
		<tr>
    		<td colspan="4" style="text-align: right;"><b>Total Amount</b></td>
			<td style="text-align: right;">${p2pGeneral.totalAmountGood}</td>
    	</tr>
    </table>
    <br/> 
    
    <#if p2pGeneral.purpose??>
		<b>Purpose</b><br/>
		<table>
			<tr>
				<td><pre style='font-size:12.0pt;font-family:"Times New Roman",serif'>${p2pGeneral.purpose}</pre></td>
			</tr>
	    </table>
	    <br/>
	</#if>
    <br/> 
    
    <#if note!="">
	${note} <br/> <br/> 
	</#if>
    
    Thanks & Best Regards,<br/>
    ${systemName}
    
    </p>
</BODY>
</HTML>