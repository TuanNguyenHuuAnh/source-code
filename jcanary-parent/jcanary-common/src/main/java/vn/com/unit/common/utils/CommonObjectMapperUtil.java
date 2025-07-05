/*******************************************************************************
 * Class        ：CommonObjectMapperUtil
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：taitt
 * Change log   ：2020/12/01：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

/**
 * CommonObjectMapperUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class CommonObjectMapperUtil {

    private static ModelMapper modelMapper = new ModelMapper();
    
    private CommonObjectMapperUtil() {
        
    }
    
    /**
     * 
     * mapAll
     * @param <O>
     * @param <S>
     * @param sourceList
     * @param outCLass
     * @return List object after mapper
     * @author taitt
     */
    public static <O, S> List<O> mapAll(final Collection<S> sourceList, Class<O> outCLass) {
        return sourceList.stream().map(entity -> modelMapper.map(entity, outCLass)).collect(Collectors.toList());
    }
    
}
