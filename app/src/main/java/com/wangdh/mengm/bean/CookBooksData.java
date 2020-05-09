package com.wangdh.mengm.bean;


import java.io.Serializable;
import java.util.List;

public class CookBooksData implements Serializable {

    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result : {"status":"0","msg":"ok","result":[{"classid":"1","name":"功效","parentid":"0","list":[{"classid":"2","name":"减肥","parentid":"1"},{"classid":"3","name":"瘦身","parentid":"1"},{"classid":"4","name":"消脂","parentid":"1"},{"classid":"5","name":"丰胸","parentid":"1"},{"classid":"6","name":"美容","parentid":"1"},{"classid":"7","name":"养颜","parentid":"1"},{"classid":"8","name":"美白","parentid":"1"},{"classid":"9","name":"防晒","parentid":"1"},{"classid":"10","name":"排毒","parentid":"1"},{"classid":"11","name":"祛痘","parentid":"1"},{"classid":"12","name":"祛斑","parentid":"1"},{"classid":"13","name":"保湿","parentid":"1"},{"classid":"14","name":"补水","parentid":"1"},{"classid":"15","name":"通乳","parentid":"1"},{"classid":"16","name":"催乳","parentid":"1"},{"classid":"17","name":"回奶","parentid":"1"},{"classid":"18","name":"下奶","parentid":"1"},{"classid":"19","name":"调经","parentid":"1"},{"classid":"20","name":"安胎","parentid":"1"},{"classid":"21","name":"抗衰老","parentid":"1"},{"classid":"22","name":"抗氧化","parentid":"1"},{"classid":"23","name":"延缓衰老","parentid":"1"},{"classid":"24","name":"补钙","parentid":"1"},{"classid":"25","name":"补铁","parentid":"1"},{"classid":"26","name":"补锌","parentid":"1"},{"classid":"27","name":"补碘","parentid":"1"},{"classid":"28","name":"补硒","parentid":"1"}]}]}
     */

    private String code;
    private boolean charge;
    private String msg;
    private ResultBeanX result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBeanX getResult() {
        return result;
    }

    public void setResult(ResultBeanX result) {
        this.result = result;
    }

    public static class ResultBeanX implements Serializable{
        /**
         * status : 0
         * msg : ok
         * result : [{"classid":"1","name":"功效","parentid":"0","list":[{"classid":"2","name":"减肥","parentid":"1"},{"classid":"3","name":"瘦身","parentid":"1"},{"classid":"4","name":"消脂","parentid":"1"},{"classid":"5","name":"丰胸","parentid":"1"},{"classid":"6","name":"美容","parentid":"1"},{"classid":"7","name":"养颜","parentid":"1"},{"classid":"8","name":"美白","parentid":"1"},{"classid":"9","name":"防晒","parentid":"1"},{"classid":"10","name":"排毒","parentid":"1"},{"classid":"11","name":"祛痘","parentid":"1"},{"classid":"12","name":"祛斑","parentid":"1"},{"classid":"13","name":"保湿","parentid":"1"},{"classid":"14","name":"补水","parentid":"1"},{"classid":"15","name":"通乳","parentid":"1"},{"classid":"16","name":"催乳","parentid":"1"},{"classid":"17","name":"回奶","parentid":"1"},{"classid":"18","name":"下奶","parentid":"1"},{"classid":"19","name":"调经","parentid":"1"},{"classid":"20","name":"安胎","parentid":"1"},{"classid":"21","name":"抗衰老","parentid":"1"},{"classid":"22","name":"抗氧化","parentid":"1"},{"classid":"23","name":"延缓衰老","parentid":"1"},{"classid":"24","name":"补钙","parentid":"1"},{"classid":"25","name":"补铁","parentid":"1"},{"classid":"26","name":"补锌","parentid":"1"},{"classid":"27","name":"补碘","parentid":"1"},{"classid":"28","name":"补硒","parentid":"1"}]}]
         */

        private String status;
        private String msg;
        private List<ResultBean> result;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean implements Serializable{
            /**
             * classid : 1
             * name : 功效
             * parentid : 0
             * list : [{"classid":"2","name":"减肥","parentid":"1"},{"classid":"3","name":"瘦身","parentid":"1"},{"classid":"4","name":"消脂","parentid":"1"},{"classid":"5","name":"丰胸","parentid":"1"},{"classid":"6","name":"美容","parentid":"1"},{"classid":"7","name":"养颜","parentid":"1"},{"classid":"8","name":"美白","parentid":"1"},{"classid":"9","name":"防晒","parentid":"1"},{"classid":"10","name":"排毒","parentid":"1"},{"classid":"11","name":"祛痘","parentid":"1"},{"classid":"12","name":"祛斑","parentid":"1"},{"classid":"13","name":"保湿","parentid":"1"},{"classid":"14","name":"补水","parentid":"1"},{"classid":"15","name":"通乳","parentid":"1"},{"classid":"16","name":"催乳","parentid":"1"},{"classid":"17","name":"回奶","parentid":"1"},{"classid":"18","name":"下奶","parentid":"1"},{"classid":"19","name":"调经","parentid":"1"},{"classid":"20","name":"安胎","parentid":"1"},{"classid":"21","name":"抗衰老","parentid":"1"},{"classid":"22","name":"抗氧化","parentid":"1"},{"classid":"23","name":"延缓衰老","parentid":"1"},{"classid":"24","name":"补钙","parentid":"1"},{"classid":"25","name":"补铁","parentid":"1"},{"classid":"26","name":"补锌","parentid":"1"},{"classid":"27","name":"补碘","parentid":"1"},{"classid":"28","name":"补硒","parentid":"1"}]
             */

            private String classid;
            private String name;
            private String parentid;
            private List<ListBean> list;

            public String getClassid() {
                return classid;
            }

            public void setClassid(String classid) {
                this.classid = classid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean implements Serializable{
                /**
                 * classid : 2
                 * name : 减肥
                 * parentid : 1
                 */

                private String classid;
                private String name;
                private String parentid;

                public String getClassid() {
                    return classid;
                }

                public void setClassid(String classid) {
                    this.classid = classid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getParentid() {
                    return parentid;
                }

                public void setParentid(String parentid) {
                    this.parentid = parentid;
                }
            }
        }
    }
}
