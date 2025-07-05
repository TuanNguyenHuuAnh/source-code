<HTML>

<head>
<style>

</style>
</head>

<BODY>
    Dear <b>${acc.fullname}</b>,
    <br/><br/>
    
    <#list listReminder as param>
    	Kindly be informed that the Cash Advance Request <b>${param.refNo}</b> will be expired on <b>${param.dueDateStr}</b> <br/>
    	
		Please access to ${param.url} to views.
		<br/><br/>
	</#list>
	
    <br/><br/>
    
    Thanks & Best Regards,<br/> 
    ${systemName}
    <br/>
	<br/>
    Note: Please don't reply this email.
</BODY>
</HTML>