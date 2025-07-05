/*******************************************************************************
 * Class        ：FileProtocol
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：HungHT
 * Change log   ：2020/07/21：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.storage.enumdef;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * FileProtocol
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FileProtocol {

    LOCAL("Local", 1L),
    WEBDAV("WebDAV", 2L),
    ECM_ALFRESCO("ECM-Alfresco", 3L),
    FILENET("FileNet", 4L);

    private String text;
    private Long value;

    private static final Map<Long, FileProtocol> mappings = new HashMap<>(FileProtocol.values().length);
    static {
        for (FileProtocol fileProtocol : values()) {
            mappings.put(fileProtocol.getValue(), fileProtocol);
        }
    }

    /**
     * resolveByValue
     * 
     * @param value FileStorage type
     * @return FileProtocol
     * @author HungHT
     */
    public static FileProtocol resolveByValue(Long value) {
        for (FileProtocol fileProtocol : values()) {
            mappings.put(fileProtocol.getValue(), fileProtocol);
        }
        return (value != null ? mappings.get(value) : null);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
