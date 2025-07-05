/*******************************************************************************
 * Class        ：SwaggerConfig
 * Created date ：2020/11/08
 * Lasted date  ：2020/11/08
 * Author       ：KhoaNA
 * Change log   ：2020/11/08：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vn.com.unit.cms.core.constant.CmsApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiConstant;

/**
 * SwaggerConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Configuration
@Order(1)
@Profile({"!prod"})
public class SwaggerConfig {

    @Bean
    public Docket swaggerEmployeeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("person-api-1.1")
            .select()
                .apis(RequestHandlerSelectors.basePackage("vn.com.unit.ep2p.rest.web"))
                .paths(PathSelectors.any())
            .build()
            .apiInfo(new ApiInfoBuilder().version("1.0").title("TEST API").description("Enterprise Test API v1.0").build());
    }
    
    @Bean
    public Docket swaggerAuthenRest() {
        return new Docket(DocumentationType.SWAGGER_2)
            .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
            .securityContexts(Collections.singletonList(
                    SecurityContext.builder()
                            .securityReferences(
                                    Collections.singletonList(SecurityReference.builder()
                                            .reference("JWT")
                                            .scopes(new AuthorizationScope[0])
                                            .build()
                                    )
                            )
                            .build())
            )
            .groupName("authen-api-1.1")
            .select()
                .apis(RequestHandlerSelectors.basePackage("vn.com.unit.ep2p.rest.authen"))
                .paths(PathSelectors.any())
            .build()
            .tags(new Tag(AppApiConstant.API_AUTHEN_AUTHENTICAITON_DESCR, "Authentication generates login tokens to the ep2p system"))
            .apiInfo(new ApiInfoBuilder().version("1.0").title("AUTHEN API").description("ep2p API authen v1.0").build());
    }
    
    @Bean
    public Docket swaggerUserRest() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("user-api-1.1")
            .select()
                .apis(RequestHandlerSelectors.basePackage("vn.com.unit.ep2p.rest.user"))
                .paths(PathSelectors.any())
            .build()
            .tags(new Tag(AppApiConstant.API_USER_DESCR, "List of information forms created from ep2p system"))
            .apiInfo(new ApiInfoBuilder().version("1.0").title("USER API").description("API user action v1.0").build());
    }
    
    @Bean
    public Docket swaggerAdminRest() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
                .securityContexts(Collections.singletonList(
                        SecurityContext.builder()
                                .securityReferences(
                                        Collections.singletonList(SecurityReference.builder()
                                                .reference("JWT")
                                                .scopes(new AuthorizationScope[0])
                                                .build()
                                        )
                                )
                                .build())
                )
                .groupName("admin-api-1.1")
            .select()
                .apis(RequestHandlerSelectors.basePackage("vn.com.unit.ep2p.rest.admin"))
                .paths(PathSelectors.any())
            .build()
            .tags(new Tag(AppApiConstant.API_ADMIN_ACCOUNT_DESCR, "Account information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_MENU_DESCR, "Menu information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_ORGANIZATION_DESCR, "Organization information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_POSITION_DESCR, "Position information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_ROLE_DESCR, "Role information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_CALENDAR_TYPE_DESCR, "Calendar type information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_GROUP_SYSTEM_CONFIG_DESCR, "Group system config information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_SYSTEM_CONFIG_DESCR, "System config information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_ROLE_FOR_TEAM_DESCR, "Role for group information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_REPOSITORY_DESCR, "Repository information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_ROLE_FOR_COMPANY_DESCR, "Role for company information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_ROLE_FOR_ITEM_DESCR, "Role for item information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_GROUP_CONSTANT_DESCR, "Group constant information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_CONSTANT_DESCR, "Constant information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_FORM_DESCR, "Form information"))
            .tags(new Tag(AppApiConstant.API_ADMIN_ACCOUNT_TEAM_DESCR, "Account for team information"))
            .apiInfo(new ApiInfoBuilder().version("1.0").title("Admin API").description("ep2p API admin action v1.0").build());
    }
    
    @Bean
    public Docket swaggerFireBaseRest() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("firebase-api-1.1")
            .select()
                .apis(RequestHandlerSelectors.basePackage("vn.com.unit.ep2p.rest.fcm"))
                .paths(PathSelectors.any())
            .build()
            .tags(new Tag("firebase", "Test push notify using firebase"))
            .apiInfo(new ApiInfoBuilder().version("1.0").title("FIREBASE API").description("API firebase v1.0").build());
    }
    
    @Bean
    public Docket swaggerAppRest() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
                .securityContexts(Collections.singletonList(
                        SecurityContext.builder()
                                .securityReferences(
                                        Collections.singletonList(SecurityReference.builder()
                                                .reference("JWT")
                                                .scopes(new AuthorizationScope[0])
                                                .build()
                                        )
                                )
                                .build())
                )
                .groupName("app-api-1.1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("vn.com.unit.ep2p.rest.app"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(AppApiConstant.API_APP_DOCUMENT_DESCR, "Document information"))
                .tags(new Tag(AppApiConstant.API_APP_DASHBOARD_DESCR, "Dash board report"))
                .tags(new Tag(AppApiConstant.API_APP_DOCUMENT_MAIN_FILE_DESCR, "Document main file"))
                .tags(new Tag(AppApiConstant.API_APP_JPM_SVC_BOARD_DESCR, "Process service board create document"))
                .tags(new Tag(AppApiConstant.API_APP_DOWNLOAD_DESCR, "Download file"))
                .apiInfo(new ApiInfoBuilder().version("1.0").title("APP API").description("API user action v1.0").build());
    }
    
    @Bean
    public Docket swaggerDsRest() {

        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
                .securityContexts(Collections.singletonList(
                        SecurityContext.builder()
                                .securityReferences(
                                        Collections.singletonList(SecurityReference.builder()
                                                .reference("JWT")
                                                .scopes(new AuthorizationScope[0])
                                                .build()
                                        )
                                )
                                .build())
                )
                .groupName("ds-api-1.1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("vn.com.unit.ep2p.rest.ds"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(AppApiConstant.API_ACCOUNT_QUESTION_DESCR, "Account security question"))
                .apiInfo(new ApiInfoBuilder().version("1.0").title("DS API").description("Ds management v1.0").build());
    }
    
    @Bean
    public Docket swaggerCmsRest() {

        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
                .securityContexts(Collections.singletonList(
                        SecurityContext.builder()
                                .securityReferences(
                                        Collections.singletonList(SecurityReference.builder()
                                                .reference("JWT")
                                                .scopes(new AuthorizationScope[0])
                                                .build()
                                        )
                                )
                                .build())
                )
                .groupName("cms-api-1.1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("vn.com.unit.ep2p.rest.cms"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(CmsApiConstant.API_CMS_ABOUT_DESCR, "About information"))
                .tags(new Tag(CmsApiConstant.API_CMS_ATM_BRANCH_DESCR, "ATM - Branch information"))
                .tags(new Tag(CmsApiConstant.API_CMS_HOME_PAGE_DESCR, "Banner information"))
                .tags(new Tag(CmsApiConstant.API_CMS_BANNER_DESCR, "Homepage information"))
                .tags(new Tag(CmsApiConstant.API_CMS_NEWS_DESCR, "News information"))
                .apiInfo(new ApiInfoBuilder().version("1.0").title("CMS API").description("CMS management v1.0").build());
    }
    
//    
//    @Bean
//    public Docket swaggerErsRest() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
//                .securityContexts(Collections.singletonList(
//                        SecurityContext.builder()
//                                .securityReferences(
//                                        Collections.singletonList(SecurityReference.builder()
//                                                .reference("JWT")
//                                                .scopes(new AuthorizationScope[0])
//                                                .build()
//                                        )
//                                )
//                                .build())
//                )
//                .groupName("ers-api-1.1")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("vn.com.unit.ep2p.rest.ers"))
//                .paths(PathSelectors.any())
//                .build()
//                .tags(new Tag(ErsApiConstant.QUESTIONAIRE_MANAGEMENT_TAG, "Document information"))
//                .apiInfo(new ApiInfoBuilder().version("1.0").title("ERS API").description("ep2p ERS API v1.0").build());
//    }
//    
//    private List<Parameter> globalParameterList() {
//        Parameter authTokenHeader =
//            new ParameterBuilder()
//                .name("Authorization") // name of the header
//                .modelRef(new ModelRef("string")) // data-type of the header
//                .required(true) // required/optional
//                .parameterType("header") // for query-param, this value can be 'query'
//                .description("Basic Auth Token")
//                .build();
//
//        return Collections.singletonList(authTokenHeader);
//    }
}
