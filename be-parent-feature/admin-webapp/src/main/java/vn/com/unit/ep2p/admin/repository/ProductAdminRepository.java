package vn.com.unit.ep2p.admin.repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.entity.ProductAdmin;

public interface ProductAdminRepository extends DbRepository<ProductAdmin, Long> {
    
    String getNotifyContent();
}
