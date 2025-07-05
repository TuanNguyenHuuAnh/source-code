/*******************************************************************************
 * Class        ：TitlePersonServiceImpl
 * Created date ：2020/02/26
 * Lasted date  ：2020/02/26
 * Author       ：KhuongTH
 * Change log   ：2020/02/26：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.admin.service.TitlePersonService;


/**
 * TitlePersonServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TitlePersonServiceImpl implements TitlePersonService {

}