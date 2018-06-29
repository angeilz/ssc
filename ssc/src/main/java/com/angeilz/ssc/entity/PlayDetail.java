package com.angeilz.ssc.entity;

import java.util.Date;

public class PlayDetail {

    private int id;
    private String playLaws;
    private String chanceStage;
    private int chanceFigure;
    private double beginAmount;
    private double inputAmount;
    private double outputAmount;
    private int endFlag;
    private Date createDate;
    private Date updateDate;
    private int inputTime;
    private String playStage;
    private double odds;

    public PlayDetail() {
    }

    //插入
    public PlayDetail(String playLaws, String chanceStage, int chanceFigure,
                      double beginAmount, String playStage, double odds) {
        this.playLaws = playLaws;
        this.chanceStage = chanceStage;
        this.chanceFigure = chanceFigure;
        this.beginAmount = beginAmount;
        this.playStage = playStage;
        this.odds = odds;
    }

    //更新
    public PlayDetail(double inputAmount, double outputAmount, int endFlag, int inputTime, String playStage) {
        this.outputAmount = outputAmount;
        this.endFlag = endFlag;
        this.inputTime = inputTime;
        this.playStage = playStage;
        this.inputAmount = inputAmount;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public double getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(double inputAmount) {
        this.inputAmount = inputAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayLaws() {
        return playLaws;
    }

    public void setPlayLaws(String playLaws) {
        this.playLaws = playLaws;
    }

    public String getChanceStage() {
        return chanceStage;
    }

    public void setChanceStage(String chanceStage) {
        this.chanceStage = chanceStage;
    }

    public int getChanceFigure() {
        return chanceFigure;
    }

    public void setChanceFigure(int chanceFigure) {
        this.chanceFigure = chanceFigure;
    }

    public double getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(double beginAmount) {
        this.beginAmount = beginAmount;
    }

    public double getOutputAmount() {
        return outputAmount;
    }

    public void setOutputAmount(double outputAmount) {
        this.outputAmount = outputAmount;
    }

    public int getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(int endFlag) {
        this.endFlag = endFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getInputTime() {
        return inputTime;
    }

    public void setInputTime(int inputTime) {
        this.inputTime = inputTime;
    }

    public String getPlayStage() {
        return playStage;
    }

    public void setPlayStage(String playStage) {
        this.playStage = playStage;
    }

    @Override
    public String toString() {
        return "PlayDetail{" +
                "playLaws='" + playLaws + '\'' +
                ", chanceStage='" + chanceStage + '\'' +
                ", chanceFigure=" + chanceFigure +
                ", beginAmount=" + beginAmount +
                ", outputAmount=" + outputAmount +
                ", endFlag=" + endFlag +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", inputTime=" + inputTime +
                ", playStage='" + playStage + '\'' +
                '}';
    }
}
