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
    ${line1} <b>${prNo}</b> ${line1Mid}
    <br/><br/>
    
    <b>Goods/Services Information</b><br/>
    <table border="1">
    <tr>
        <th>Goods / Services Description</th>
        <th>Unit</th>
        <th>Quantity</th>
        <th style="min-width: 100px;">Unit Price (${p2pGeneral.currencyCode})</th>
        <th>Amount (${p2pGeneral.currencyCode})</th>
    </tr>
     <#list prdetails as prdetail>
        <tr>
            <td>${prdetail.productDescription}</td>
			<td>${prdetail.uomName}</td>
			<td align="right">${prdetail.quantity}</td>
			<td align="right">${prdetail.estimatedUnitPrice}</td>
			
			<#if p2pGeneral.currencyCode =="VND">
				<td align="right">${prdetail.estimatedAmountVnd}</td>
			
			<#else>
				<td align="right">${prdetail.amountFcy}</td>
			</#if>
        </tr>
    </#list>
		<tr>
    		<td colspan="4" style="text-align: right;"><b>Total Amount</b></td>
			<td style="text-align: right;">${p2pGeneral.totalAmountGood}</td>
    	</tr>
    </table>

    
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
    
    Thanks & Best Regards,<br/>
    ${systemName}
    
    </p>
</BODY>
</HTML>