package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileReq {
    private String fileName;
    private String repoId;

    // Constructor mặc định bắt buộc cho Jackson
    public FileReq() {
    }
    // Constructor accepting repoId as Long and fileName as String
    public FileReq(Long repoId, String fileName) {
        this.repoId = String.valueOf(repoId); // Convert Long to String
        this.fileName = fileName;
    }
}
