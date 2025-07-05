SELECT COUNT(product.ID) from M_PRODUCT product
JOIN M_PRODUCT_LANGUAGE productLanguage ON product.ID = productLanguage.M_PRODUCT_ID
JOIN M_BANNER_LANGUAGE bannerLanguage ON productLanguage.BANNER_DESKTOP = bannerLanguage.ID
JOIN M_BANNER banner ON bannerLanguage.BANNER_ID = banner.ID
where productLanguage.DELETE_DATE is null
AND product.STATUS != 100
AND banner.DELETE_DATE is null
AND bannerLanguage.DELETE_DATE is null
AND product.DELETE_DATE is null
AND banner.ID = /*bannerId*/