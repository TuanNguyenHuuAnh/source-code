package vn.com.unit.ep2p.admin.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class TrainingTraineeDB2Param {
    @In
    public String agentCode;
    
    @In
    public String oficeCode;
    
    @ResultSet
    public List<TrainingTraineeDB2Dto> lstData;
}
