package vn.com.unit.ep2p.logger.layout;

import java.io.BufferedReader;
import java.io.FileReader;

import ch.qos.logback.classic.PatternLayout;

public class LogFileHeaderPatternLayout extends PatternLayout  {
	private String filePath;
	private String header;

    @Override
    public String getFileHeader() {
        if (alreadyContainsHeader()) {
            return "";
        } else {
            return header;
        }
    }

    private boolean alreadyContainsHeader() {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.contains(header)) {
                    return true;
                } else {
                    break;
                }
            }
        } catch (Exception ex) {
        	return false;
        }
        return false;
    }


    public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

    public void setHeader(String header) {
        this.header = header;
    }
}
