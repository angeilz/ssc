package com.angeilz.ssc.mapper;

import com.angeilz.ssc.entity.PlayDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PlayDetailDao {

    List<PlayDetail> findNotEndChance(@Param("figure") int figure, @Param("playStage") String playStage);

    void insert(PlayDetail playDetail);

    void update(PlayDetail playDetail);

    String getNewId();

    List<String> isHaveChanceStage(@Param("chanceStage") String chanceStage);
}
