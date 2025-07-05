package vn.com.unit.ep2p.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class SystemOutLogUtils {

	public static void printLogParam(String note, Object... options) {
		StringBuilder sb = new StringBuilder();
		if (options != null && options.length > 0) {
			for (int i = 0; i < options.length; i++) {
				sb.append("   [" + i + "] " + options[i]);
			}
		} else {
			sb.append("   [No additional options]");
		}
		System.out.println("==> " + note + " " + sb.toString());
	}

	public static void printLogObjectDto(String note, Object dto) {
		StringBuilder sb = new StringBuilder();

		if (dto == null) {
			sb.append("   [DTO is null]");
			return;
		}

		Class<?> clazz = dto.getClass();
		Field[] fields = clazz.getDeclaredFields();
		boolean hasOutput = false;

		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object value = field.get(dto);
				if (isNotEmpty(value)) {
					sb.append("   [" + field.getName() + "] = " + value);
					hasOutput = true;
				}
			} catch (IllegalAccessException e) {
				sb.append("   [Error accessing field: " + field.getName() + "]");
			}
		}

		if (!hasOutput) {
			sb.append("   [No meaningful values to print]");
		}
		System.out.println("==> " + note + " " + sb.toString());
	}

	private static boolean isNotEmpty(Object value) {
		if (value == null)
			return false;
		if (value instanceof String && ((String) value).trim().isEmpty())
			return false;
		if (value instanceof Collection && ((Collection<?>) value).isEmpty())
			return false;
		if (value instanceof Map && ((Map<?, ?>) value).isEmpty())
			return false;
		return true;
	}
}
