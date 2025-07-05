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
		Dear <b>${param.delegateFullname}</b>,<br/>
		<br/>
			
		Kindly be informed that the Delegation from <b>${param.ownerFullname}</b> will be expired on ${param.expiredDateString}
		<br/> 
		Please go to eP2P system to clear all your pending tasks.
		<br/>
		Pending task(s) as below : 
	    <br/><br/>
	    <#list lstPendingTask as red>
			${red.rowNum}. ${red.nameTodo} <b>${red.transactionNo}</b> created date ${red.createdDateStr} is ${red.statusName} : 
			<br/>
			${red.url}
			<br/><br/>
		</#list>  
	    <br>
	    Thanks & Best Regards,<br/>             
	    ${param.systemName}<br/><br/>
	</BODY>
</HTML>
