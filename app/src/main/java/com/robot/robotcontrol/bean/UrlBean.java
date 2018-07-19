package com.robot.robotcontrol.bean;

public class UrlBean {

    /**
     * code : 200
     * meg : 操作成功
     * data : {"EZOPEN":"ezopen://open.ys7.com/C12757580/1.live","EZOPENhd":"ezopen://open.ys7.com/C12757580/1.hd.live"}
     */

    private String code;
    private String meg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMeg() {
        return meg;
    }

    public void setMeg(String meg) {
        this.meg = meg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * EZOPEN : ezopen://open.ys7.com/C12757580/1.live
         * EZOPENhd : ezopen://open.ys7.com/C12757580/1.hd.live
         */

        private String EZOPEN;
        private String EZOPENhd;

        public String getEZOPEN() {
            return EZOPEN;
        }

        public void setEZOPEN(String EZOPEN) {
            this.EZOPEN = EZOPEN;
        }

        public String getEZOPENhd() {
            return EZOPENhd;
        }

        public void setEZOPENhd(String EZOPENhd) {
            this.EZOPENhd = EZOPENhd;
        }
    }
}
