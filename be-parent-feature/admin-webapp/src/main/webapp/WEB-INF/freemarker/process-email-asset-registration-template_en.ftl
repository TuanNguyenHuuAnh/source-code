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
<p>	Dear <b>${lstFullName}</b>, <br/><br/>
	
	${line1}	</br>
	Please access to ${url} to view.<br/><br/>

	<#if note!="">
	${note} <br/>
	</#if>
	
	Thanks & Best Regards,	<br/>
	eP2P System.	

<br/>      
</p>       
</BODY>
</HTML>