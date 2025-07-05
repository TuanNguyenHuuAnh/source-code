<HTML>

<head>
<style>

</style>
</head>

<BODY>
    Dear <b>${acc.fullname}</b>,
    <br/>
    <br/>
	Just remind.
	<br/>
	You have pending task(s) in eP2P system as below :
    <br/>
    <br/>
    
    <#list listReminder as red>
		${red.rowNum}. ${red.nameTodo} ${red.transactionNo} create date ${red.strCreatedDate} is ${red.statusName} : 
		<br/>
		${red.url}
		<br/><br/>
	</#list>  
    
	<br/>
    Thanks & Best Regards,<br/>             
    ${systemName}
    <br/>
	<br/>
    Note: Please don't reply this email.
</BODY>
</HTML>