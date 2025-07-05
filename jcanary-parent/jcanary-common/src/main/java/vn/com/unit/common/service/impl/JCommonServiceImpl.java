/*******************************************************************************
 * Class        CoreServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       KhoaNA
 * Change log   2016/06/21 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.constant.CommonExceptionCodeConstant;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.exception.DetailException;

/**
 * CoreServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
// Field commonService in vn.com.unit.ep2p.core.efo.service.impl.EfoDocVersionServiceImpl required a bean of type 'vn.com.unit.common.service.JCommonService' that could not be found.
// Consider defining a bean of type 'vn.com.unit.common.service.JCommonService' in your configuration.
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JCommonServiceImpl implements JCommonService {

//    @Autowired
//    private DBRepository commonRepository;
//    
    private static String COLUMN_DEFAULT = "ID";

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.CoreService#getSystemDate()
     */
    @Override
    public Date getSystemDate() {
        return CommonDateUtil.getSystemDateTime();
    }

    @Override
    public String generateCodeFromIdWithFormat(Long seqId, String format) {
        if (null == format) {
            format = CommonConstant.YYYYMMDD_TIME;
        }
        // get next id from sequence
        Date sysDate = CommonDateUtil.getSystemDateTime();
        // get date with format yyyyMMdd
        String dateFormat = CommonDateUtil.formatDateToString(sysDate, format);
        return CommonConstant.SYSTEM_PREFIX.concat(dateFormat.concat(CommonConstant.HYPHEN).concat(String.valueOf(seqId)));
    }

    @Override
    public String generateCodeFromId(Long seqId) {
        return generateCodeFromIdWithFormat(seqId, null);
    }

//    @Override
//    public Sort buildSortAlias(Sort sort, Class<?> clazz, String alias) throws DetailException {
//        List<String> columList = CommonObjectUtil.getColumnListFromEntity(clazz);
//        List<Order> ordersRs = new ArrayList<>();
//        for (Order order : sort) {
//            String property = order.getProperty().toUpperCase();
//            if (columList.indexOf(property) >= 0) {
//                order = order.isAscending() ? Order.asc(alias.concat(CommonConstant.DOT).concat(property))
//                        : Order.desc(alias.concat(CommonConstant.DOT).concat(property));
//                ordersRs.add(order);
//            } else {
//                throw new DetailException(CommonExceptionCodeConstant.E101700_SORT_DYNAMIC_TABLE, new String[] { order.getProperty() },
//                        true);
//            }
//        }
//        return Sort.by(ordersRs);
//    }
    
    @Override
    public Sort buildSortAlias(Sort sort, Class<?> clazz, String alias) throws DetailException {
        List<Order> ordersRs = new ArrayList<>();
        String propertiesError = CommonStringUtil.EMPTY;
        try {
            for (Order order : sort) {
                String property = order.getProperty();
                propertiesError = property;
                String columnSort = CommonObjectUtil.getColumnListFromEntity(clazz,property);
                if (CommonStringUtil.isNotBlank(columnSort)) {
                    order = order.isAscending() ? Order.asc(alias.concat(CommonConstant.DOT).concat(columnSort))
                            : Order.desc(alias.concat(CommonConstant.DOT).concat(columnSort));
                    ordersRs.add(order);
                } else {
                    throw new DetailException(CommonExceptionCodeConstant.E101700_SORT_DYNAMIC_TABLE, new String[] { order.getProperty() },
                            true);
                }
            }
        }catch (Exception e) {
            throw new DetailException(CommonExceptionCodeConstant.E101700_SORT_DYNAMIC_TABLE, new String[] { propertiesError },
                    true);
        }
        
        return Sort.by(ordersRs);
    }
    
    @Override
    public Sort buildSortAliasNotUseDefault(Sort sort, Class<?> clazz, String alias) throws DetailException {
        List<Order> ordersRs = new ArrayList<>();
        String propertiesError = CommonStringUtil.EMPTY;
        try {
            for (Order order : sort) {
                String property = order.getProperty();
                propertiesError = property;
                String columnSort = CommonObjectUtil.getColumnListFromEntity(clazz,property);
                
                /** IF property not equal column default sort and property not found clazz then break function */
                if (COLUMN_DEFAULT.equals(columnSort) && !columnSort.equals(property)) {
                    continue;
                }
                if (CommonStringUtil.isNotBlank(columnSort)) {
                    order = order.isAscending() ? Order.asc(alias.concat(CommonConstant.DOT).concat(columnSort))
                            : Order.desc(alias.concat(CommonConstant.DOT).concat(columnSort));
                    ordersRs.add(order);
                } else {
                    throw new DetailException(CommonExceptionCodeConstant.E101700_SORT_DYNAMIC_TABLE, new String[] { order.getProperty() },
                            true);
                }
            }
        }catch (Exception e) {
            throw new DetailException(CommonExceptionCodeConstant.E101700_SORT_DYNAMIC_TABLE, new String[] { propertiesError },
                    true);
        }
        
        return Sort.by(ordersRs);
    }
    
    @Override
    public <T extends Enum<T>> Sort buildSortEnums(Sort sort, T[] enumsDatas,Class<?> clazz) throws DetailException {
        List<Order> ordersRs = new ArrayList<>();
        String propertiesError = CommonStringUtil.EMPTY;
        try {
            for (Order order : sort) {
                String property = order.getProperty();
                propertiesError = property;
                String valueMapingEnum = "";
                
                if (enumsDatas != null) {
                    valueMapingEnum = Stream.of(enumsDatas).filter(item -> item.toString().equalsIgnoreCase(property))
                            .map(Enum::name).findFirst().orElse(null);
                }
                
                String columnSort = CommonStringUtil.isNotBlank(valueMapingEnum) ? valueMapingEnum
                        : CommonObjectUtil.getColumnListFromEntity(clazz, property);
                
                if (CommonStringUtil.isNotBlank(columnSort)) {
                    order = order.isAscending() ? Order.asc(columnSort)
                            : Order.desc(columnSort);
                    ordersRs.add(order);
                } else {
                    throw new DetailException(CommonExceptionCodeConstant.E101700_SORT_DYNAMIC_TABLE, new String[] { order.getProperty() },
                            true);
                }
            }
        }catch (Exception e) {
            throw new DetailException(CommonExceptionCodeConstant.E101700_SORT_DYNAMIC_TABLE, new String[] { propertiesError },
                    true);
        }
        
        return Sort.by(ordersRs);
    }

    @Override
    public <T extends Enum<T>> Sort buildSortEnums(Sort sort) throws DetailException {
        List<Order> ordersRs = new ArrayList<>();
        String propertiesError = CommonStringUtil.EMPTY;
        try {
            for (Order order : sort) {
                String columnSort = order.getProperty();

                if (CommonStringUtil.isNotBlank(columnSort)) {
                    order = order.isAscending() ? Order.asc(columnSort) : Order.desc(columnSort);
                    ordersRs.add(order);
                } else {
                    throw new DetailException(CommonExceptionCodeConstant.E101700_SORT_DYNAMIC_TABLE,
                            new String[] { order.getProperty() }, true);
                }
            }
        } catch (Exception e) {
            throw new DetailException(CommonExceptionCodeConstant.E101700_SORT_DYNAMIC_TABLE,
                    new String[] { propertiesError }, true);
        }
        return Sort.by(ordersRs);
    }
}
