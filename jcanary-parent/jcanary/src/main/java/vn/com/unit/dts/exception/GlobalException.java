package vn.com.unit.dts.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * @author: chien
 */
@Getter
@AllArgsConstructor
public class GlobalException extends Exception {

    private static final long serialVersionUID = 2392926148247590423L;

    protected ExceptionCode exceptionCode;
}
