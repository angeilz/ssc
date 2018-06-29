package com.angeilz.ssc.service;

import com.angeilz.ssc.entity.PlayDetail;
import com.angeilz.ssc.entity.StageInfo;
import com.angeilz.ssc.mapper.PlayDetailDao;
import com.angeilz.ssc.mapper.StageInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FiveFourService {

    private static final int NINE = 9;
    private static final int ZERO = 0;
    private static final String PLAY_LAWS = "五星不定位一码出54买09";
    private static final double ODDS = 1.392;
    private static final long BEGIN_AMOUNT = 3;
    @Autowired
    PlayDetailDao dao;
    @Autowired
    StageInfoDao cqDao;

    public void newChance(String chanceStage, String playStage, int figure) {
        PlayDetail detailZero = new PlayDetail(PLAY_LAWS, chanceStage, figure, BEGIN_AMOUNT, playStage, ODDS);
        dao.insert(detailZero);
    }

    public void oldChance(String kjNumber, String kjStage, String nextStage, int figure, Map<Integer, Double> map) {
        //查看往期投注是否中了
        List<PlayDetail> details = dao.findNotEndChance(figure, kjStage);
        if (details.size() > 0) {
            //中了
            if (kjNumber.contains(figure + "")) {
                for (PlayDetail detail : details) {
                    detail.setEndFlag(1);
                    detail.setOutputAmount(profit(detail.getInputTime()));
                    dao.update(detail);
                }
            } else {
                //没中
                for (PlayDetail detail : details) {
                    int time = detail.getInputTime();
                    if (time == 5) {
                        //没有机会了。。。。
                        detail.setEndFlag(1);
                        detail.setOutputAmount(lose(1));
                        dao.update(detail);
                    } else {
                        //不怕,还有机会
                        detail.setInputTime(detail.getInputTime() + 1);
                        //总投入=当前投入+前期投入总额
                        double totalAmount = input(detail.getInputTime()) + detail.getInputAmount();
                        detail.setInputAmount(totalAmount);
                        detail.setPlayStage(nextStage);
                        dao.update(detail);
                        map.put(figure, map.get(figure) + input(detail.getInputTime()));
                    }
                }
            }
        }
    }

    public Map<Integer, Double> isHaveChance(String kjNumber, String kjStage, String nextStage) {
        System.out.println("**********************" + kjStage + "*************************");
        Map<Integer, Double> map = new HashMap<>();
        map.put(ZERO, 0.0);
        map.put(NINE, 0.0);
        //判断是否中奖
        oldChance(kjNumber, kjStage, nextStage, ZERO, map);
        oldChance(kjNumber, kjStage, nextStage, NINE, map);

        //判断是否有新机会
        if (kjNumber.contains(4 + "") && kjNumber.contains(5 + "")) {
            //判断是否机会期数是否存在
            List<String> chanceStages = dao.isHaveChanceStage(kjStage);
            if (chanceStages.size() > 0) {
                System.out.println("chanceStage:" + kjStage + "已存在");
                return map;
            }
            //来机会啦
            newChance(kjStage, nextStage, ZERO);
            newChance(kjStage, nextStage, NINE);
            map.put(ZERO, map.get(ZERO) + BEGIN_AMOUNT);
            map.put(NINE, map.get(NINE) + BEGIN_AMOUNT);
        }
        map.put(ZERO, (double) Math.round(map.get(ZERO) * 1000) / 1000);
        map.put(NINE, (double) Math.round(map.get(NINE) * 1000) / 1000);
        System.out.println("下注信息:" + map);
        System.out.println();
        return map;

    }

    public String getNewId() {
        return dao.getNewId();
    }

    //算法4
    public double profitAndLoss(String begin, String end,String tableName) {
        int oneTime = 0, twoTime = 0, threeTime = 0, fourTime = 0,
                fiveTime = 0, loseTime = 0;
        List<StageInfo> list = cqDao.findListByTime(begin, end,tableName);
        double profitAndLoss = 0;
        List<Double> profitAndLossList = new ArrayList<>();
        List<String> loseIdList = new ArrayList<>();
        List<String> profitIdList = new ArrayList<>();

        for (int i = 0; i < list.size() - 5; i++) {
            String number = list.get(i).getNumber();
            if (!number.contains("4") || !number.contains("5")) {
                continue;
            }
            String next1 = list.get(i + 1).getNumber();
            String next2 = list.get(i + 2).getNumber();
            String next3 = list.get(i + 3).getNumber();
            String next4 = list.get(i + 4).getNumber();
            String next5 = list.get(i + 5).getNumber();
            for (String s : "09".split("")) {
                if (!next1.contains(s) && !next2.contains(s) && !next3.contains(s) &&
                        !next4.contains(s) && !next5.contains(s)) {
                    loseTime++;
                    int k = i + 6;
                    int maxTime = 5;
                    while (k + 5 < list.size() && !list.get(k).getNumber().contains(s)) {
                        k++;
                        maxTime++;
                    }
                    loseIdList.add(list.get(i).getId() + "|figure:" + s + "|maxMissTime:" + maxTime);
                    profitAndLoss -= lose(1);
                    continue;
                }
                if (next1.contains(s)) {
                    oneTime++;
                    profitAndLoss += profit(1);
                    profitIdList.add(list.get(i).getId() + "|figure:" + s + ",time:" + 1);
                } else if (next2.contains(s)) {
                    twoTime++;
                    profitAndLoss += profit(2);
                    profitIdList.add(list.get(i).getId() + "|figure:" + s + ",time:" + 2);
                } else if (next3.contains(s)) {
                    threeTime++;
                    profitAndLoss += profit(3);
                    profitIdList.add(list.get(i).getId() + "|figure:" + s + ",time:" + 3);
                } else if (next4.contains(s)) {
                    fourTime++;
                    profitAndLoss += profit(4);
                    profitIdList.add(list.get(i).getId() + "|figure:" + s + ",time:" + 4);
                } else if (next5.contains(s)) {
                    fiveTime++;
                    profitAndLoss += profit(5);
                    profitIdList.add(list.get(i).getId() + "|figure:" + s + ",time:" + 5);
                }
                profitAndLossList.add((double) Math.round(profitAndLoss * 1000) / 1000);
            }
        }
        profitAndLoss = (double) Math.round(profitAndLoss * 1000) / 1000;
        System.out.println("***************************************************************");
        System.out.println("date:" + begin.substring(0, 8));
//        System.out.println("loseIdList:" + loseIdList);
//        System.out.println("profitIdList:" + profitIdList);
        System.out.println("profitList:" + profitAndLossList);
        System.out.println("loseTime:" + loseTime + ",oneTime:" + oneTime + ",twoTime:" + twoTime + ","
                + "threeTime:" + threeTime + ",fourTime:" + fourTime + ",fiveTime:" + fiveTime);
        System.out.println("profitAndLoss:" + profitAndLoss);
        return profitAndLoss;
    }

    public long lose(int loseTime) {
        long total = 0;
        for (int i = 0; i < 5; i++) {
            total += input(i + 1);
        }
        return loseTime * total;
    }

    public long input(int time) {
        long begin = BEGIN_AMOUNT;
        for (int i = 1; i < time; i++) {
            begin = Math.round(begin * (ODDS + 1));
        }
        return begin;
    }

    public double profit(int time) {
        return (double) Math.round(input(time) * ODDS * 1000) / 1000;
    }


}
