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

    @Autowired
    FiveFourService fiveFourService;

    @Test
    public void chance() {
        fiveFourService.isHaveChance("12410", "20180628009", "201806280010");
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
            profit += fiveFourService.profitAndLoss("201806" + begin + "024", "201806" + end + "023");
            profitList.add(profit);
        }
        System.out.println("*****************************************************");
        System.out.println("profitList" + profitList);
        System.out.println("profit:" + profit);
    }

    @Test
    public void profitAndLose() {
        fiveFourService.profitAndLoss("20180601024", "20180631023");
    }


    public static void main(String[] args) {
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

