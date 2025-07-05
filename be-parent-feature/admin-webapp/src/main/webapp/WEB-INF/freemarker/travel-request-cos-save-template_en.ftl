<HTML>
<BODY>
	Dear <b>${dearFullname}</b>,<br/>
	<br/>
	
	Kindly be informed that Travel Request ${transactionNo} has been updated by <b>${userRequest}</b> successfully. <br/>
	Please access to ${url} to view.
	
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
	
	Thanks & Best Regards,<br/>
	${systemName}<br/><br/>
	
</BODY>
</HTML>