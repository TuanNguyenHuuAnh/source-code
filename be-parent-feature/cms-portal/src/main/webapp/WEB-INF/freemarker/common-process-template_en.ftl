<HTML>
	<BODY>
		<h4>Kính gửi Anh/Chị</h4>
		
		<b>${param.userName}</b> đã thực hiện <b>${param.action}</b> cho chức năng <b>${param.actionName}</b> trên hệ thống ACB Admin.<br/><br/>
		
		Anh chị vui lòng đăng nhập vào bằng liên kết dưới đây để thực hiện công việc tiếp theo (nếu có): 
		${param.url}
		
		<br/><br/>
		
		<table border=1 cellspacing=0 cellpadding=0 style='border-collapse:collapse;border:none'>
			<thead>
				<tr>
					<#list param.content?keys as prop>
					    <th>${prop}</th>
					</#list>
				</tr>
			</thead>
			<tbody>
					<tr>
						<#list param.content?keys as prop>
							<td width=160 valign=top style='width:160pt;border:solid windowtext 1.0pt;padding:0in 5.4pt 0in 5.4pt'>${param.content[prop]}</td>
						</#list>
					</tr>
			</tbody>
		</table>
		
		<br/>
		
		Ghi chú: ${param.comment}
		
		<br/><br/>
		
		<i style="font-size:18px;color:#4286ad;"><b>Trân trọng!<br/>ACB Admin</b></i>
		
		<br/><br/>
		
		<i><b>
			<u style="font-size:25px;color:red;">LƯU Ý:</u>
			<span style="font-size:18px;color:#4286ad;"> Đây là email được gửi tự động từ hệ thống, vui lòng không reply lại email này.</span>
		</b></i>
	</BODY>
</HTML>