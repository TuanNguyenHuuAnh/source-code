/*******************************************************************************
 * Class        ：TeamRest
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：MinhNV
 * Change log   ：2020/12/08：01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.rest.AbstractRest;

import java.nio.charset.StandardCharsets;

/**
 * TeamRest
 *
 * @author MinhNV
 * @version 01-00
 * @since 01-00
 */
@RestController
@RequestMapping(AppApiConstant.API_V1 + "/version")
@Api(tags = {"Version"})
public class VersionRest extends AbstractRest {
    @GetMapping("/get")
    @ApiOperation("Get building version ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 402, message = "Forbidden")})
    public String getBuildVersion() {
        long start = System.currentTimeMillis();

        try {
            String fileName = "version-info-api.txt";
            ClassPathResource classPathResource = new ClassPathResource(fileName);
            byte[] bdata = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            String version = new String(bdata, StandardCharsets.UTF_8);
            return version;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
