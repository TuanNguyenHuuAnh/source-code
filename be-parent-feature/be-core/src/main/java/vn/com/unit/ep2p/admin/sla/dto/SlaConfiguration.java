package vn.com.unit.ep2p.admin.sla.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlaConfiguration {

    private SlaInfomation slaInfo;

    @Getter
    @Setter
    public class SlaInfomation {

        private Long id;
        private Long businessId;
        private Long companyId;
        private Long processId;
        private String slaName;
        private Long calendarType;
        private List<StepInfomation> stepInfomations;

        @Getter
        @Setter
        public class StepInfomation {

            private Long id;
            private Boolean actived;
            private Long businessId;
            private Long processId;
            private Long slaInfoId;
            private Long stepId;
            private String timeType;
            private Double workTime;
            private Boolean autoAction;
            private Long actionButtonId;
            private List<SettingInfomation> settingInfomations;

            @Getter
            @Setter
            public class SettingInfomation {

                private Long id;
                private Double alertTime;
                private String alertType;
                private Long emailTemplateId;
                private Boolean immediately;
                private Long slaStepId;
                private Long unitTimeId;
                private Boolean isTransfer;
                private List<DetailSettingInfomation> detailInfomations;

                @Getter
                @Setter
                public class DetailSettingInfomation {

                    private Long id;
                    private Long accountId;
                    private Long groupId;
                    private Long slaSettingId;
                    private String userType;
                }

            }

        }

    }

}
