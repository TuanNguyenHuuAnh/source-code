package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * RegisterAccountReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public class RegisterAccountRes {
    private String fullname;

    private String email;

    private boolean passwordToEmail;

    private String phone;

    private boolean passWordToPhone;

    private Date registerDate;
}
