package vn.com.unit.ep2p.dto;

public class OZReport {
    private String reportType;
    private String reportDesc;
    private String reportPath;
    private String reportName;
    private String serviceName;
    private String usedFlag;
    private String deviceType;
    
    public String getReportType() {
        return reportType;
    }
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    public String getReportDesc() {
        return reportDesc;
    }
    public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }
    public String getReportPath() {
        return reportPath;
    }
    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }
    public String getReportName() {
        return reportName;
    }
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getUsedFlag() {
		return usedFlag;
	}
	public void setUsedFlag(String usedFlag) {
		this.usedFlag = usedFlag;
	}
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reportName == null) ? 0 : reportName.hashCode());
        result = prime * result + ((reportType == null) ? 0 : reportType.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OZReport other = (OZReport) obj;
        if (reportName == null) {
            if (other.reportName != null)
                return false;
        } else if (!reportName.equals(other.reportName))
            return false;
        if (reportType == null) {
            if (other.reportType != null)
                return false;
        } else if (!reportType.equals(other.reportType))
            return false;
        return true;
    }
    
}
