SELECT
	CONCAT(N'Thời gian đặt mua ấn phẩm năm mới ',
			FORMAT(DATEADD(year, 1, GETDATE()), 'yyyy'),
			N' bắt đầu từ ',
			FORMAT(min(p.EFFECTIVE_DATE), 'dd/MM/yyyy'),
			N' đến ', FORMAT(max(EXPIRED_DATE), 'dd/MM/yyyy'),
			N'. Bạn còn ',
			DATEDIFF(DAY, GETDATE(), max(EXPIRED_DATE)),
			N' ngày để thực hiện đặt mua ấn phẩm.')
FROM
	M_PRODUCT p
WHERE
	p.PRODUCT_TYPE=FORMAT(DATEADD(year, 1, GETDATE()), 'yyyy')
GROUP BY PRODUCT_TYPE
HAVING GETDATE() <= max(EXPIRED_DATE)