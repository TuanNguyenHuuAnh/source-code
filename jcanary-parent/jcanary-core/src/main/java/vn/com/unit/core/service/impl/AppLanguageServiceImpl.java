/*******************************************************************************
 * Class        LanguageServiceImpl
 * Created date 2017/02/16
 * Lasted date  2017/02/16
 * Author       hand
 * Change log   2017/02/1601-00 hand create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.service.AppLanguageService;

/**
 * LanguageServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Service
@Primary
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppLanguageServiceImpl extends LanguageServiceImpl implements AppLanguageService {


	
}
