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
<p>	Dear ${lstFullName}, <br/><br/>
	
	${contents}  ${fullname}  ${extends}  <br/>		
	${lineSecond} ${url} to ${nexAction}<br/><br/>
<#if true =  isShowTable>
	<table border="1">
	<tr>
		<th>Vendor Name</th>
		<th>Catalog Description</th>
		
		<#if typeSubmit == 0>
			<th>Active Date</th>
			<th>Expiry Date</th>
		</#if>
		
		<#if typeSubmit == 2>
			<th>De-active Date</th>
		</#if>
		
		<#if typeSubmit == 1>
			<th>Current Expiry Date</th>
			<th>New Expiry Date</th>
		</#if>
		
		
		
		
	</tr>
	
	
	<tr>
		<td>${vendorName}</td>
		<td>${catalogDescription}</td>
		
		
		<#if typeSubmit == 0>
			<td>${activeDate}</td>
			<td>${expiredDate}</td>
		
		</#if>
		
		<#if typeSubmit == 2>
			<td>${deactiveDate}</td>
		
		</#if>
		
		<#if typeSubmit == 1>
			<td>${curExpiryDate}</td>
			<td>${newExpiryDate}</td>
		</#if>
		
		
		
	</tr>
	
	</table>
				<br/> <br/>
				
				
	</#if>
	<#if note!="">
	${note} <br/>
	</#if>
	
	Thanks & Best Regards,	<br/>
	eP2P System.	

<br/>      
</p>       
</BODY>
</HTML>