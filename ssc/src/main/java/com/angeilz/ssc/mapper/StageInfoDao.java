package com.angeilz.ssc.mapper;

import com.angeilz.ssc.entity.StageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StageInfoDao {

    void insert(StageInfo stageInfo);

    List<StageInfo> findListByTime(@Param("begin") String begin, @Param("end") String end);

    String getLastID();
}
