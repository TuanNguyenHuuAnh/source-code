<HTML>

<head>
<style>
	table {
	  border-collapse: collapse;
	}
	th {
		text-align: center
	}
	 td, th {
	  border: 1px solid black;
	  padding: 3px 5px 3px 5px;
	}
</style>
</head>

<BODY>
    Dear <b><#if param.ownerFullName??>${param.ownerFullName}</#if></b>,
    <br/><br/>
    
    	Kindly be informed that the following Contract <b>${param.contractNo}</b> will be expired on <b>${param.stringExpiryDate}</b> <br/>
		Please go to <a href="${param.url}" style="text-decoration: underline;">${param.url}</a> to views.
		<br/><br/>
		
		<table>
			<thead>
				<tr>
					<th><b>Vendor</b></th>
					<th><b>Category</b></th>
					<th><b>Contract value</b></th>
					<th><b>Start date</b></th>
					<th><b>Expiry Date</b></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><#if param.supplierName??>${param.supplierName}</#if></td>
					<td><#if param.productCategoryName??>${param.productCategoryName}</#if></td>
					<td style="text-align:right;"><#if param.valueAmount??>${param.valueAmount}<#else>N/A</#if></td>
					<td>${param.stringStartDate}</td>
					<td>${param.stringExpiryDate}</td>
				</tr>
			</tbody>
		</table>
		<br/>
    <br/><br/>
    
    Thanks & Best Regards,<br/> 
    ${systemName}
    <br/>
	<br/>
    Note: Please don't reply this email.
</BODY>
</HTML>