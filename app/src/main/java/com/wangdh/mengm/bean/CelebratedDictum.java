package com.wangdh.mengm.bean;

/**
 * 启动页名人名言
 */

public class CelebratedDictum {

    /**
     * result : {"famous_name":"德谟克利特","famous_saying":"智慧有三果：一是思考周到，二是语言得当，三是行为公正。"}
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
         * famous_name : 德谟克利特
         * famous_saying : 智慧有三果：一是思考周到，二是语言得当，三是行为公正。
         */

        private String famous_name;
        private String famous_saying;

        public String getFamous_name() {
            return famous_name;
        }

        public void setFamous_name(String famous_name) {
            this.famous_name = famous_name;
        }

        public String getFamous_saying() {
            return famous_saying;
        }

        public void setFamous_saying(String famous_saying) {
            this.famous_saying = famous_saying;
        }
    }
}
