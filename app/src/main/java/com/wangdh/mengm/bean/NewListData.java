package com.wangdh.mengm.bean;

import java.util.List;

/**
 * 新闻列表
 */

public class NewListData {


    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result : {"msg":"ok","result":{"num":"10","channel":"头条","list":[{"src":"新浪体育","weburl":"http://sports.sina.com.cn/g/pl/2017-08-24/doc-ifykiuaz0411003.shtml","time":"2017.08.24 11:59:27","pic":"http://api.jisuapi.com/news/upload/201708/24140005_99559.png","title":"11.8亿镑！英超转会费又创纪录 超西甲意甲之和","category":"sports",<\/p>","url":"http://news.sina.cn/gn/2017-08-24/detail-ifykiqfe1131863.d.html?vt=4&pos=108"}]},"status":"0"}
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

    public static class ResultBeanX {
        /**
         * msg : ok
         * result : {"num":"10","channel":"头条","list":[{"src":"新浪体育","weburl":"http://sports.sina.com.cn/g/pl/2017-08-24/doc-ifykiuaz0411003.shtml","time":"2017.08.24 11:59:27","pic":"http://api.jisuapi.com/news/upload/201708/24140005_99559.png","title":"11.8亿镑！英超转会费又创纪录 超西甲意甲之和","category":"sports","content":"","url":"http://news.sina.cn/gn/2017-08-24/detail-ifykiqfe1131863.d.html?vt=4&pos=108"}]}
         * status : 0
         */

        private String msg;
        private ResultBean result;
        private String status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static class ResultBean {
            /**
             * num : 10
             * channel : 头条
             * list : [{"src":"新浪体育","weburl":"http://sports.sina.com.cn/g/pl/2017-08-24/doc-ifykiuaz0411003.shtml","time":"2017.08.24 11:59:27","pic":"http://api.jisuapi.com/news/upload/201708/24140005_99559.png","title":"11.8亿镑！英超转会费又创纪录 超西甲意甲之和","category":"sports","content":"<\/p>","url":"http://news.sina.cn/gn/2017-08-24/detail-ifykiqfe1131863.d.html?vt=4&pos=108"}]
             */

            private String num;
            private String channel;
            private List<ListBean> list;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * src : 新浪体育
                 * weburl : http://sports.sina.com.cn/g/pl/2017-08-24/doc-ifykiuaz0411003.shtml
                 * time : 2017.08.24 11:59:27
                 * pic : http://api.jisuapi.com/news/upload/201708/24140005_99559.png
                 * title : 11.8亿镑！英超转会费又创纪录 超西甲意甲之和
                 * category : sports
                 * content : <p class="art_p">内马尔转会身价1.96亿英镑、卢卡库7500万英镑、莫拉塔7000万英镑……今夏的转会市场，疯狂程度又一次刷新了人们的认知。而这其中花钱最疯狂的，无疑当属英超球队，因为他们的转会费支出又创造了新的历史纪录。</p><p class="art_p">距离欧洲转会市场关闭，还有7天时间。但即便转会还未结束，英超已经再度刷新了联赛总计转会费历史纪录。统计显示，英超今夏截至目前，已经豪砸了11.8亿英镑，这个数字是新的英超纪录。可以预计，接下来的7天时间里（截止英国当地时间8月31日晚23时），英超总计转会费支出，无疑还会继续拔高。</p><p class="art_p">值得一提的是，今夏英超20队中，有18支球队的转会支出，已经打破了队史单笔转会费支出纪录，就连一向精打细算阿森纳，也在拉卡泽特身上创造了队史转会费新纪录（5200万英镑）。</p><p class="art_p">英超的转会费支出，已经把其他欧洲联赛甩在了身后，德甲截至目前的转会支出为4.6亿英镑，西甲的转会支出为3.89亿英镑，意甲为7.47亿英镑，法甲为5.34亿英镑。可以看出，英超的总支出比西甲和意甲加起来还要多。</p><p class="art_p"><strong>附欧洲五大联赛转会数据（英镑）：</strong></p><p class="art_p">英超：买人11.8亿，卖人5.98亿</p><p class="art_p">德甲：买人4.62亿，卖人3.20亿</p><p class="art_p">西甲：买人3.89亿，卖人6.07亿</p><p class="art_p">意甲：买人7.47亿，卖人5.78亿</p><p class="art_p">法甲：买人5.34亿，卖人4.74亿</p><p class="art_p">（小九）</p>
                 * url : http://sports.sina.cn/premierleague/manutd/2017-08-24/detail-ifykiuaz0411003.d.html?vt=4&pos=108
                 */

                private String src;
                private String weburl;
                private String time;
                private String pic;
                private String title;
                private String category;
               // private String content;
                private String url;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public String getWeburl() {
                    return weburl;
                }

                public void setWeburl(String weburl) {
                    this.weburl = weburl;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

//                public String getContent() {
//                    return content;
//                }
//
//                public void setContent(String content) {
//                    this.content = content;
//                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
