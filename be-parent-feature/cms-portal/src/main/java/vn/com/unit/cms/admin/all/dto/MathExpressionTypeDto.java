package vn.com.unit.cms.admin.all.dto;

public class MathExpressionTypeDto {
	private String typeName;
	private String typeMessageKey;
	
	public MathExpressionTypeDto(){
		
	}
	
	public MathExpressionTypeDto(String name, String messageKey) {
		this.typeName = name;
		this.typeMessageKey = messageKey;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeMessageKey() {
		return typeMessageKey;
	}
	public void setTypeMessageKey(String typeMessageKey) {
		this.typeMessageKey = typeMessageKey;
	}

}
