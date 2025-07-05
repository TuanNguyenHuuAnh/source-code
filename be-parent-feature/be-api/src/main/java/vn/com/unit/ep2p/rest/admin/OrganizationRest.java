/*******************************************************************************
 * Class        ：MenuRest
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：SonND
 * Change log   ：2020/12/09：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.common.tree.JSTree;
import vn.com.unit.common.tree.TreeObject;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.dto.TreeNodeDto;
import vn.com.unit.ep2p.dto.req.OrganizationAddReq;
import vn.com.unit.ep2p.dto.req.OrganizationUpdateReq;
import vn.com.unit.ep2p.dto.res.OrganizationInfoRes;
import vn.com.unit.ep2p.dto.res.TreeNodeRes;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.ep2p.service.OrganizationService;
import vn.com.unit.ep2p.service.PagingService;

/**
 * MenuRest
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */

@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_ADMIN)
@Api(tags = { AppApiConstant.API_ADMIN_ORGANIZATION_DESCR })
public class OrganizationRest extends AbstractRest {

    @Autowired
    PagingService pagingService;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping(AppApiConstant.API_ADMIN_ORGANIZATION)
    @ApiOperation("List of organization")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021803, message = "Error process list organizaiton"), })
    public DtsApiResponse listOrganization() {
        long start = System.currentTimeMillis();
        try {
            List<TreeObject<OrganizationInfoRes>> resObj = organizationService.getListOrg();
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PostMapping(AppApiConstant.API_ADMIN_ORGANIZATION)
    @ApiOperation("Create organization")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021802, message = "Error organization not found"),
            @ApiResponse(code = 4021804, message = "Error process add organization"),
            @ApiResponse(code = 4021809, message = "Error process save organization path"),
            @ApiResponse(code = 4021811, message = "Error organization code is duplicated"), })
    public DtsApiResponse addOrganization(
            @ApiParam(name = "body", value = "Organization information to add new") @RequestBody OrganizationAddReq organizationAddReq) {
        long start = System.currentTimeMillis();
        try {
            JcaOrganizationDto resObj = organizationService.create(organizationAddReq);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_ADMIN_ORGANIZATION)
    @ApiOperation("Update organization")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021802, message = "Error organization not found"),
            @ApiResponse(code = 4021805, message = "Error process update information organization"),
            @ApiResponse(code = 4021809, message = "Error process save organization path"), })
    public DtsApiResponse updateOrganization(
            @ApiParam(name = "body", value = "Organization information to update") @RequestBody OrganizationUpdateReq organizationUpdateReq) {
        long start = System.currentTimeMillis();
        try {
            organizationService.update(organizationUpdateReq);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_ORGANIZATION + "/{orgId}")
    @ApiOperation("Detail of organization")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402800, message = "Error process get information organization"),
            @ApiResponse(code = 402802, message = "Error organization not found"), })
    public DtsApiResponse detailOrganization(
            @ApiParam(name = "orgId", value = "Get menu information detail on system by id", example = "123") @PathVariable("orgId") Long orgId) {
        long start = System.currentTimeMillis();
        try {
            OrganizationInfoRes resObj = organizationService.getOrganizationInfoResById(orgId);
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @DeleteMapping(AppApiConstant.API_ADMIN_ORGANIZATION)
    @ApiOperation("Delete organization")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402802, message = "Error organization not found"),
            @ApiResponse(code = 402807, message = "Error process delete organization"), })
    public DtsApiResponse deleteOrganization(
            @ApiParam(name = "orgId", value = "Delete organization by id", example = "1") @RequestParam("orgId") Long orgId) {
        long start = System.currentTimeMillis();
        try {
            organizationService.delete(orgId);
            return this.successHandler.handlerSuccess(null, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_ADMIN_ORGANIZATION + "/get-node-child")
    @ApiOperation("Detail of organization")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 402800, message = "Error process get information organization"),
            @ApiResponse(code = 402802, message = "Error organization not found"), })
    public DtsApiResponse getTreeNode(
            @ApiParam(name = "orgId", value = "Get get tree node on system by id", example = "123") @RequestParam(value = "orgId", required = false) Long orgId,
            @ApiParam(name = "checkedId", value = "Get get tree node on system by id", example = "123") @RequestParam(value = "checkedId", required = false) Long checkedId) {
        long start = System.currentTimeMillis();
        try {
            TreeNodeRes resObj = new TreeNodeRes();
            List<TreeNodeDto> treeNodeList = organizationService.getTreeNodeList(orgId);
            resObj.setTreeData(treeNodeList);
            if(null == checkedId && CommonCollectionUtil.isNotEmpty(treeNodeList)) {
                checkedId = treeNodeList.get(0).getKey();
            }
            resObj.setCheckedKeys(Arrays.asList(checkedId));
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
    
    @GetMapping(AppApiConstant.API_ADMIN_ORGANIZATION + "/combobox")
    @ApiOperation("List of organization")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4021803, message = "Error process list organizaiton"), })
    public DtsApiResponse listOrganizationCombobox() {
        long start = System.currentTimeMillis();
        try {
            List<JSTree> resObj = organizationService.getListOrgTreeCombobox();
            return this.successHandler.handlerSuccess(resObj, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
