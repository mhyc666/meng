package com.wangdh.mengm.bean;


public class HeaderLayoutBean {

    /**
     * result : {"id":"22727416-d974-411b-b027-8c204a31f167","biaoti":"留题云门草堂（小住初为旬月期）","jieshao":"【注释】\r\n","zuozhe":"陆游","neirong":"【留题云门草堂】\r\n\r\n小住初为旬月期，二年留滞未应非。\r\n寻碑野寺云生屦，迸客溪桥雪满衣。\r\n亲涤砚池馀墨迹，卧看炉面散烟霏。\r\n他年游宦应无此，早买渔蓑未老归。\r\n"}
     * error_code : 0
     * reason : Succes
     */

    private ResultBean result;
    private int error_code;
    private String reason;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static class ResultBean {
        /**
         * id : 22727416-d974-411b-b027-8c204a31f167
         * biaoti : 留题云门草堂（小住初为旬月期）
         * jieshao : 【注释】

         * zuozhe : 陆游
         * neirong : 【留题云门草堂】

         小住初为旬月期，二年留滞未应非。
         寻碑野寺云生屦，迸客溪桥雪满衣。
         亲涤砚池馀墨迹，卧看炉面散烟霏。
         他年游宦应无此，早买渔蓑未老归。

         */

        private String id;
        private String biaoti;
        private String jieshao;
        private String zuozhe;
        private String neirong;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBiaoti() {
            return biaoti;
        }

        public void setBiaoti(String biaoti) {
            this.biaoti = biaoti;
        }

        public String getJieshao() {
            return jieshao;
        }

        public void setJieshao(String jieshao) {
            this.jieshao = jieshao;
        }

        public String getZuozhe() {
            return zuozhe;
        }

        public void setZuozhe(String zuozhe) {
            this.zuozhe = zuozhe;
        }

        public String getNeirong() {
            return neirong;
        }

        public void setNeirong(String neirong) {
            this.neirong = neirong;
        }
    }
}
