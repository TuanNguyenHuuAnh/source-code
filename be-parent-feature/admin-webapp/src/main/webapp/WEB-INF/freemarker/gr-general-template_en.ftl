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
<p>  Dear <b>${receiverName}</b>,
    <br/>
    <br/>
    ${line1} <b> ${actionBy} </b> ${line1End} 
    <br/>
    ${line2Start} ${url} ${line2End}
    <br/><br/>
    
    <table border="1">
    <tr>
    	<th>PO No</th>
    	<th>PR No</th>
        <th>Goods / Services Description</th>
        <th>Vendor</th>
        <th>Unit</th>
        <#if p2pGeneral.isService != 1>
		<th>PO Quantity</th>
        <th>Received Quantity</th>
        </#if>
        <th style="min-width: 100px;">Unit Price (${p2pGeneral.currencyCode})</th>
		<th>PO Amount (${p2pGeneral.currencyCode})</th>
        <th>Received Amount (${p2pGeneral.currencyCode})</th>
    </tr>
     <#list prdetails as prdetail>
        <tr>
        	<td>${p2pGeneral.poNoRef}</td>
        	<td>${prdetail.prNo}</td>
            <td>${prdetail.description}</td>
            <td>${p2pGeneral.supplierName}</td>
			<td>${prdetail.unit}</td>
			<#if p2pGeneral.isService != 1>
			<td align="right">${prdetail.approvedQuantity}</td>
			<td align="right">${prdetail.receivedQuantity}</td>
			</#if>
			<td align="right">${prdetail.unitPrice}</td>
			<td align="right">${prdetail.approvedAmount}</td>
			<td align="right">${prdetail.payableAmount}</td>
        </tr>
    </#list>
		<tr>
			<#if p2pGeneral.isService != 1>
    		<td style="text-align: right;" colspan="9"><b>Total Amount (VND)</b></td>
    		</#if>
    		<#if p2pGeneral.isService == 1>
    		<td style="text-align: right;" colspan="7"><b>Total Amount (VND)</b></td>
    		</#if>
			<td style="text-align: right;">${p2pGeneral.totalAmount}</td>
    	</tr>
    </table>
    <br/> <br/>
    <#if note!="">
	${note} <br/> <br/> 
	</#if>
    
    Thanks & Best Regards,<br/>
    ${systemName}
    
    </p>
</BODY>
</HTML>