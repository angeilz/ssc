package com.angeilz.ssc.entity;

public class StageInfo {
    String id;
    String number;
    String tableName;

    public StageInfo() {

    }

    public StageInfo(String id, String number,String tableName) {
        this.id = id;
        this.number = number;
        this.tableName=tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Cq{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
