package com.angeilz.ssc.service;

import com.angeilz.ssc.entity.StageInfo;
import com.angeilz.ssc.mapper.StageInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhanglei
 * @version 2018/6/25
 */
@Service
public class StageInfoService {

    @Autowired
    StageInfoDao dao;

    public boolean autoInsert(String tableName) {
        String lastId = dao.getLastID(tableName);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(new Date());
        List<String> list = DataSources.getDataByTimeZone(lastId, date + "120", tableName);
        if (list.size() == 0) {
            return false;
        }
        insert(list, tableName);
        return true;
    }

    public void insert(String begin, String end, String tableName) {
        List<String> list = DataSources.getDataByTimeZone(begin, end, tableName);
        insert(list, tableName);
    }

    private void insert(List<String> list, String tableName) {
        for (String str : list) {
            String id = str.split("\\|")[0];
            String num = str.split("\\|")[1].replace(" ", "");
            try {
                dao.insert(new StageInfo(id, num, tableName));
                System.out.println("id:" + id + ",num:" + num + " 插入成功");
            } catch (Exception e) {
                System.out.println("第" + id + "期已存在");
            }
        }
    }

}
