package vn.com.unit.fcm.dto;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FcmPushNotificationRequest {

    private String title;
    private String message;
    private String topic;
    private String token;
    private List<String> multipleTokens = Lists.newLinkedList();
}
