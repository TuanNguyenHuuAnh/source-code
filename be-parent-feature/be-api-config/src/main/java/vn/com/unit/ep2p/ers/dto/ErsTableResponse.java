package vn.com.unit.ep2p.ers.dto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import vn.com.unit.ep2p.core.ers.annotation.IesTableHeader;

public class ErsTableResponse<T extends Object> extends ErsResponse<T> {

	@Getter
	private List<ErsTableHeaderDto> header;

	public void setHeader(Class<T> t) {

		List<ErsTableHeaderDto> rs = new ArrayList<>();

		List<Field> lstField = new ArrayList<>();

		lstField.addAll(Arrays.asList(t.getDeclaredFields()));
		lstField.addAll(Arrays.asList(t.getSuperclass().getDeclaredFields()));

		for (Field f : lstField) {
			IesTableHeader h = f.getAnnotation(IesTableHeader.class);
			if (null != h) {
				ErsTableHeaderDto th = new ErsTableHeaderDto();
				th.setTitle(h.value());
				th.setDataIndex(f.getName());
				th.setWidth(h.width());

				rs.add(th);
			}
		}

		this.header = rs;
	};

}
