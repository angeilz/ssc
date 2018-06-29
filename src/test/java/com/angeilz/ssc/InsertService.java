package com.angeilz.ssc;

import com.angeilz.ssc.service.StageInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhanglei
 * @version 2018/6/29
 */
public class InsertService extends SscApplicationTests {

    public static final String CQ_TABLE = "cq";
    public static final String XJ_TABlE = "xj";
    public static final String TJTABLE = "tj";
    @Autowired
    StageInfoService service;
    /*********************XJ************************/
    @Test
    public void insertXJ() {
        service.insert("20180101001", "20180131120", XJ_TABlE);
    }

    @Test
    public void autoInsertXJ() {
        service.autoInsert(XJ_TABlE);
    }

    /*********************CQ************************/
    @Test
    public void insertCQ() {
        service.insert("20180101001", "20180131120", CQ_TABLE);
    }

    @Test
    public void autoInsertCQ() {
        service.autoInsert(CQ_TABLE);
    }

    /*********************TJ************************/
    @Test
    public void insertTJ() {
        service.insert("20180101001", "20180131120", TJTABLE);
    }

    @Test
    public void autoInsertTJ() {
        service.autoInsert(TJTABLE);
    }


}
