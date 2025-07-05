/*******************************************************************************
 * Class        ：ApiProductServiceImpl
 * Created date ：2023/08/04
 * Lasted date  ：2023/08/04
 * Author       ：thu.thai
 * Change log   ：2023/08/04 hand create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.product.entity.Product;
import vn.com.unit.cms.core.module.product.repository.ApiProductRepository;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.service.ApiProductService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiProductServiceImpl implements ApiProductService{
	@Autowired
	ApiProductRepository productRepository ; 
	
	@Autowired
    private SystemConfig systemConfig;

	public List<Product> getAllProduct() throws IOException {
        List<Product> listProduct = productRepository.getAllProduct();
		String repo = systemConfig.getConfig(systemConfig.REPO_UPLOADED_MAIN);
        String path = systemConfig.getPhysicalPathById(repo, null);

        for (Product product : listProduct) {
        	boolean flag = true;
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
        return listProduct;
    }
}
