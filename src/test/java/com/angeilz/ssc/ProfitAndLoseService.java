package com.angeilz.ssc;

import com.angeilz.ssc.service.FiveFourService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglei
 * @version 2018/6/29
 */
public class ProfitAndLoseService extends SscApplicationTests {
    public static final String CQ_TABLE = "cq";
    public static final String XJ_TABlE = "xj";
    public static final String TJTABLE = "tj";
    @Autowired
    FiveFourService fiveFourService;

    @Test
    public void chance() {
        fiveFourService.isHaveChance("12410", "20180629023", "20180629120");
    }


    @Test
    public void profitAndLosePlus() {
        int profit = 0;
        List<Integer> profitList = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            String begin = String.valueOf(i);
            if (i < 10) {
                begin = "0" + begin;
            }
            String end = String.valueOf(i + 1);
            if (i + 1 < 10) {
                end = "0" + end;
            }
            profit += fiveFourService.profitAndLoss("201806" + begin + "024", "201806" + end + "023", CQ_TABLE);
            profitList.add(profit);
        }
        System.out.println("*****************************************************");
        System.out.println("profitList" + profitList);
        System.out.println("profit:" + profit);
    }

    @Test
    public void profitAndLose() {
        fiveFourService.profitAndLoss("20180629024", "20180629120", CQ_TABLE);
    }


    public static void main(String[] args) {
        //oneTime:7,twoTime:3,threeTime:2,fourTime:3,fiveTime:1
        //21+24+40+138+101=320
        // 3 8 20 46 101
        //
        FiveFourService service = new FiveFourService();
        long total = 0;
        for (int i = 0; i < 5; i++) {
            long input = service.input(i + 1);
            total += input;
            System.out.println("input:" + input + ",total:" + total + ",profit:" + (double) Math.round(input * ODDS * 1000) / 1000);
        }
    }

    public static final double ODDS = 1.392;
}

