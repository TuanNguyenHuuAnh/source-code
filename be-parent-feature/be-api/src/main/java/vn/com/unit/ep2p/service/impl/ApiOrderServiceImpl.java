package vn.com.unit.ep2p.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.cms.core.module.order.dto.OrderDto;
import vn.com.unit.cms.core.module.order.dto.OrderInformationDto;
import vn.com.unit.cms.core.module.order.dto.OrderProductReviewDto;
import vn.com.unit.cms.core.module.order.dto.OrderReviewDto;
import vn.com.unit.cms.core.module.order.entity.Order;
import vn.com.unit.cms.core.module.order.entity.OrderDetail;
import vn.com.unit.cms.core.module.order.repository.OrderDetailRepository;
import vn.com.unit.cms.core.module.order.repository.OrderRepository;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.service.ApiOrderService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiOrderServiceImpl implements ApiOrderService {
	@Autowired 
	OrderRepository orderRepository;
	@Autowired 
	OrderDetailRepository orderDetailRepository; 
	@Autowired
    private SystemConfig systemConfig;
	private static final Logger logger = LoggerFactory.getLogger(ApiOrderServiceImpl.class);
	private static final String PREFIX = "ORD";

	public List<OrderInformationDto> getListOrderByAgent (String agentCode){
		return orderRepository.getListOrderByAgentCode(agentCode);
	}
	
	public OrderDto saveOrder(OrderDto orderDto) {
		Order order = new Order() ; 
		order.setCode(CommonUtil.getNextOrderCode(PREFIX, orderRepository.getMaxOrderCode(PREFIX)));
		order.setAgentCode(orderDto.getAgentCode()) ; 
		order.setAgentName(orderDto.getAgentName()) ; 
		order.setManagerCode(orderDto.getManagerCode()) ; 
		order.setManagerName(orderDto.getManagerName()) ; 
		order.setOfficeCode(orderDto.getOfficeCode()) ;
		order.setPhone(orderDto.getPhone()) ; 
		order.setDeliveryAgentCode(orderDto.getDeliveryAgentCode()) ; 
		order.setDeliveryAgentName(orderDto.getDeliveryAgentName()) ; 
		order.setDeliveryOfficeCode(orderDto.getDeliveryOfficeCode()) ; 
		order.setDeliveryProvince(orderDto.getDeliveryProvince()) ;
		if (orderDto.isCheckBill()) {
			order.setInvoiceOfficeCode(orderDto.getInvoiceOfficeCode()) ; 
			order.setInvoiceTaxCode(orderDto.getInvoiceTaxCode()) ; 
			order.setInvoiceCompanyName(orderDto.getInvoiceCompanyName());
			order.setInvoiceCompanyAddress(orderDto.getInvoiceCompanyAddress()) ;
		} else {
			order.setInvoiceOfficeCode(null) ; 
			order.setInvoiceTaxCode(null) ; 
			order.setInvoiceCompanyName(null);
			order.setInvoiceCompanyAddress(null) ;
		}
		order.setTotalAmount(orderDto.getTotalAmount()) ; 
		order.setAttachmentImg(orderDto.getAttachmentImg()) ; 
		order.setAttachmentPhysicalImg(orderDto.getAttachmentPhysicalImg()) ; 
		order.setStatusOrder(orderDto.getStatusOrder()) ; 
		order.setCreateDate(new Date()) ; 
		order.setCreateBy(orderDto.getAgentCode()) ; 

		order.setChannel(orderDto.getChannel()) ; 
		order.setOfficeName(orderDto.getOfficeName()) ;
		orderRepository.save(order);
		Long orderId = orderRepository.getId(order);
		
		JSONParser parser = new JSONParser();
		try {
			JSONArray productJson = (JSONArray) parser.parse(orderDto.getProductCodeJson());			
			for (Object obj :  productJson) {		
				OrderDetail orderDetail = new OrderDetail() ; 
				JSONObject jsonObject = (JSONObject) obj;
				String productCode = (String) jsonObject.get("productCode");
				Long quantity = (long) jsonObject.get("quantity");
				orderDetail.setOrderId(orderId); 
				orderDetail.setProductCode(productCode); 
				orderDetail.setQuantity(quantity); 
				orderDetailRepository.save(orderDetail);
			}
		}
		catch (ParseException e){
			e.printStackTrace();
		}
		
		return orderDto;
	}
	
	public OrderReviewDto getOrderReview(Long orderId, String userName) throws IOException {
		OrderInformationDto information = orderRepository.getOrderInfomation(orderId, userName);
		if (information == null) {
			return null;
		}
		// get product information
		List<OrderProductReviewDto> productList = orderDetailRepository.getOrderProductReview(orderId);
		String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
        String path = systemConfig.getPhysicalPathById(repo, null);
    	boolean flag = true;

		for (OrderProductReviewDto product : productList) {
            File file = new File(path + "/" + product.getProductPhysicalImg());
            byte[] fileContent = new byte[(int) file.length()];
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                inputStream.read(fileContent);
            } catch (IOException e) {
//                throw new IOException("Unable to convert file to byte array. " + e.getMessage());
            	flag = false ; 
            } finally {
                // close input stream
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            if (flag == true) {
               	product.setProductImg(Base64.getEncoder().encodeToString(fileContent));    
            }
            else {
            	product.setProductImg("");
            }
        }
		
		OrderReviewDto order = new OrderReviewDto();
		
		order.setOrderId(information.getId()) ; 
		order.setAgentCode(information.getAgentCode()) ; 
		order.setAgentName(information.getAgentName()) ; 
		order.setManagerCode(information.getManagerCode()) ; 
		order.setManagerName(information.getManagerName()) ; 
		order.setOfficeCode(information.getOfficeCode()) ;
		order.setOfficeName(information.getOfficeName()) ;
		order.setPhone(information.getPhone()); 
		order.setDeliveryAgentCode(information.getDeliveryAgentCode()) ; 
		order.setDeliveryAgentName(information.getDeliveryAgentName()) ; 
		order.setDeliveryOfficeCode(information.getDeliveryOfficeCode()) ; 
		order.setDeliveryProvince(information.getDeliveryProvince()) ; 
		order.setInvoiceOfficeCode(information.getInvoiceOfficeCode()) ; 
		order.setInvoiceCompanyName(information.getInvoiceCompanyName()) ; 
		order.setInvoiceTaxCode(information.getInvoiceTaxCode()) ; 
		order.setInvoiceCompanyAddress(information.getInvoiceCompanyAddress()) ; 
		order.setTotalAmount(information.getTotalAmount()) ; 
		order.setStatusOrder(information.getStatusOrder()) ; 
		order.setStatusName(information.getStatusName()) ;
		
		order.setProductInformation(productList) ; 
		return order;
	}

	public OrderDto updateOrderInformation(OrderDto orderDto) {
		Order order = orderRepository.findOne(orderDto.getOrderId());
		/* delete old product */
		orderRepository.deleteOldProductInOrder(orderDto.getOrderId());
		
		order.setAgentCode(orderDto.getAgentCode()) ; 
		order.setAgentName(orderDto.getAgentName()) ; 
		order.setManagerCode(orderDto.getManagerCode()) ; 
		order.setManagerName(orderDto.getManagerName()) ; 
		order.setOfficeCode(orderDto.getOfficeCode()) ;
		order.setPhone(orderDto.getPhone()) ; 
		order.setDeliveryAgentCode(orderDto.getDeliveryAgentCode()) ; 
		order.setDeliveryAgentName(orderDto.getDeliveryAgentName()) ; 
		order.setDeliveryOfficeCode(orderDto.getDeliveryOfficeCode()) ; 
		order.setDeliveryProvince(orderDto.getDeliveryProvince()) ;
		if (orderDto.isCheckBill()) {
			order.setInvoiceOfficeCode(orderDto.getInvoiceOfficeCode()) ; 
			order.setInvoiceTaxCode(orderDto.getInvoiceTaxCode()) ; 
			order.setInvoiceCompanyName(orderDto.getInvoiceCompanyName());
			order.setInvoiceCompanyAddress(orderDto.getInvoiceCompanyAddress()) ;
		} else {
			order.setInvoiceOfficeCode(null) ; 
			order.setInvoiceTaxCode(null) ; 
			order.setInvoiceCompanyName(null);
			order.setInvoiceCompanyAddress(null) ;
		}
		order.setTotalAmount(orderDto.getTotalAmount()) ; 
		order.setAttachmentImg(orderDto.getAttachmentImg()) ; 
		order.setAttachmentPhysicalImg(orderDto.getAttachmentPhysicalImg()) ; 

		order.setUpdateDate(new Date()) ; 
		order.setUpdateBy(orderDto.getAgentCode()) ; 

		order.setChannel(orderDto.getChannel()) ; 
		order.setOfficeName(orderDto.getOfficeName()) ; 
		
		orderRepository.save(order);
		
		this.saveOrderDetail(orderDto, order.getId());
		
		return orderDto;	
	}

	
	public OrderDto submit(List<MultipartFile> document, OrderDto orderDto) {
		Order order = null;
		// check neu la submit moi
		if (orderDto.getOrderId() == null) {
			order = new Order() ; 
			
			order.setCode(CommonUtil.getNextOrderCode(PREFIX, orderRepository.getMaxOrderCode(PREFIX)));
			order.setCreateDate(new Date()) ; 
			order.setCreateBy(orderDto.getAgentCode()) ; 
		} else {
			order = orderRepository.findOne(orderDto.getOrderId());
			/* delete old product */
			orderRepository.deleteOldProductInOrder(orderDto.getOrderId());
			order.setUpdateDate(new Date()) ; 
			order.setUpdateBy(orderDto.getAgentCode()) ;
		}
		order.setAgentCode(orderDto.getAgentCode()) ; 
		order.setAgentName(orderDto.getAgentName()) ; 
		order.setManagerCode(orderDto.getManagerCode()) ; 
		order.setManagerName(orderDto.getManagerName()) ; 
		order.setOfficeCode(orderDto.getOfficeCode()) ;
		order.setPhone(orderDto.getPhone()) ; 
		order.setDeliveryAgentCode(orderDto.getDeliveryAgentCode()) ; 
		order.setDeliveryAgentName(orderDto.getDeliveryAgentName()) ; 
		order.setDeliveryOfficeCode(orderDto.getDeliveryOfficeCode()) ; 
		order.setDeliveryProvince(orderDto.getDeliveryProvince()) ; 
		if (orderDto.isCheckBill()) {
			order.setInvoiceOfficeCode(orderDto.getInvoiceOfficeCode()) ; 
			order.setInvoiceTaxCode(orderDto.getInvoiceTaxCode()) ; 
			order.setInvoiceCompanyName(orderDto.getInvoiceCompanyName());
			order.setInvoiceCompanyAddress(orderDto.getInvoiceCompanyAddress()) ;
		} else {
			order.setInvoiceOfficeCode(null) ; 
			order.setInvoiceTaxCode(null) ; 
			order.setInvoiceCompanyName(null);
			order.setInvoiceCompanyAddress(null) ;
		}
		order.setTotalAmount(orderDto.getTotalAmount()) ; 
		order.setStatusOrder(orderDto.getStatusOrder()) ; 
		order.setStatusOrder("2");
		order.setChannel(orderDto.getChannel()) ; 
		order.setOfficeName(orderDto.getOfficeName()) ; 
		this.saveBill(document, order);
		orderRepository.save(order);
		
		this.saveOrderDetail(orderDto, order.getId());
		
		return orderDto;
	}

	private void saveBill(List<MultipartFile> document, Order order) {
		SimpleDateFormat formatDateExport = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDate = formatDateExport.format(new Date());
		String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
		String path = systemConfig.getPhysicalPathById(repo, null);
		int length = path.length();
		path = Paths.get(path, "Order_Image").toString();		
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Path pathFile = Paths.get(path, order.getCode() + "_" + currentDate + ".png");
		for (MultipartFile image : document) {    
			try {
				byte[] imageCode = image.getBytes();
				Files.write(pathFile, imageCode);
				order.setAttachmentImg(image.getOriginalFilename());
				order.setAttachmentPhysicalImg(pathFile.toString().substring(length + 1));
			} catch (IOException e) {
				logger.error("Exception ", e);
			}
		}
		
	}
	
	private void saveOrderDetail(OrderDto orderDto, Long orderId) {
		JSONParser parser = new JSONParser();
		try {
			JSONArray productJson = (JSONArray) parser.parse(orderDto.getProductCodeJson());			
			for (Object obj :  productJson) {		
				OrderDetail orderDetail = new OrderDetail() ; 
				JSONObject jsonObject = (JSONObject) obj;
				String productCode = (String) jsonObject.get("productCode");
				Long quantity = (long) jsonObject.get("quantity");
				orderDetail.setOrderId(orderId); 
				orderDetail.setProductCode(productCode); 
				orderDetail.setQuantity(quantity); 
				orderDetailRepository.save(orderDetail);
			}
		}
		catch (ParseException e){
			e.printStackTrace();
		}
	}
}
