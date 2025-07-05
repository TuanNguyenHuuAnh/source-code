package vn.com.unit.dts.web.rest.common;

import java.io.Serializable;

import lombok.Data;

@Data
public class DtsApiEmpty implements Serializable {
    private static final long serialVersionUID = -1679684846709815655L;
    private String empty;
}
