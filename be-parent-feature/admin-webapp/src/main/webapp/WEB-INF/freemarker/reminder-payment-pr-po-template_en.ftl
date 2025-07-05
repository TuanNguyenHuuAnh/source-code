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
		Dear <b>${param.requester}</b>,
		<br/><br/>
		
				Kindly be informed that the following PO No <b>${param.poNo}</b> is due to clear deposit <br/>
    			Please go to <b>Payment – Purchase Order</b> module to clear deposit and supporting invoices
    	<br/>
    	<a href="${param.url}" style="text-decoration: underline;">${param.url}</a>
	    <br/><br/>
	    
	    Thanks & Best Regards,<br/>             
	    ${systemName}<br/><br/>
	</BODY>
</HTML>
