/*******************************************************************************
* Class        JpmStatusDefaultLangRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmStatusLangDto;

/**
 * JpmStatusDefaultLangRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmStatusDefaultLangRepository extends DbRepository<Object, Long>{
    List<JpmStatusLangDto> getListStatusLangDefault();
}