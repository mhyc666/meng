package com.wangdh.mengm.bean;

import java.util.List;

public class HealthitemData {

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"list":[{"id":562,"name":"新闻"},{"id":563,"name":"曝光"},{"id":570,"name":"保健"},{"id":569,"name":"养生"},{"id":568,"name":"心理"},{"id":578,"name":"常见病"},{"id":579,"name":"疑难病"},{"id":580,"name":"用药"},{"id":565,"name":"产经"},{"id":663,"name":"两性"},{"id":664,"name":"专栏"},{"id":567,"name":"挑食"},{"id":752,"name":"图集"}],"ret_code":0}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * list : [{"id":562,"name":"新闻"},{"id":563,"name":"曝光"},{"id":570,"name":"保健"},{"id":569,"name":"养生"},{"id":568,"name":"心理"},{"id":578,"name":"常见病"},{"id":579,"name":"疑难病"},{"id":580,"name":"用药"},{"id":565,"name":"产经"},{"id":663,"name":"两性"},{"id":664,"name":"专栏"},{"id":567,"name":"挑食"},{"id":752,"name":"图集"}]
         * ret_code : 0
         */

        private int ret_code;
        private List<ListBean> list;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 562
             * name : 新闻
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
