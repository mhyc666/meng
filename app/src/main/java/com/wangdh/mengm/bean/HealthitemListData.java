package com.wangdh.mengm.bean;

import java.util.List;

public class HealthitemListData {


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


        private int ret_code;
        private PagebeanBean pagebean;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public PagebeanBean getPagebean() {
            return pagebean;
        }

        public void setPagebean(PagebeanBean pagebean) {
            this.pagebean = pagebean;
        }

        public static class PagebeanBean {

            private int allPages;
            private int currentPage;
            private int allNum;
            private int maxResult;
            private List<ContentlistBean> contentlist;

            public int getAllPages() {
                return allPages;
            }

            public void setAllPages(int allPages) {
                this.allPages = allPages;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getAllNum() {
                return allNum;
            }

            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getMaxResult() {
                return maxResult;
            }

            public void setMaxResult(int maxResult) {
                this.maxResult = maxResult;
            }

            public List<ContentlistBean> getContentlist() {
                return contentlist;
            }

            public void setContentlist(List<ContentlistBean> contentlist) {
                this.contentlist = contentlist;
            }

            public static class ContentlistBean {
                /**
                 * id : comos:fxieymv7709919
                 * title : 医生护士被逼抱尸示众 遭死者家属辱骂殴打(图)
                 * keywords : 医护人员,医院,医生,家属,死者
                 * img : http://n.sinaimg.cn/transform/20150929/vGZZ-fxieyms4193794.jpg
                 * tname : 新闻
                 * media_name : 中国网
                 * images : [{"w":312,"u":"http://n.sinaimg.cn/transform/20150929/vGZZ-fxieyms4193794.jpg","t":"手机拍摄：周口太康县人民医院医生护士被逼抱尸示众","h":550},{"w":311,"u":"http://n.sinaimg.cn/transform/20150929/OK_E-fxieymu0934523.jpg","t":"手机拍摄：周口太康县人民医院医生护士被逼抱尸示众","h":550},{"w":317,"u":"http://n.sinaimg.cn/transform/20150929/VV7M-fxieyms4193825.jpg","t":"手机拍摄：周口太康县人民医院医生护士被逼抱尸示众","h":550},{"w":360,"u":"http://n.sinaimg.cn/transform/20150929/RI7X-fxifmki9595836.jpg","t":"监控视频：孩子家属对医护人员殴打辱骂扇耳光","h":288},{"w":360,"u":"http://n.sinaimg.cn/transform/20150929/RqVz-fxieymv7707922.jpg","t":"监控视频：孩子家属对医护人员殴打辱骂扇耳光","h":288},{"w":540,"u":"http://n.sinaimg.cn/transform/20150929/ctxp-fxifmki9595861.jpg","t":"","h":405}]
                 * tid : 562
                 * wapurl : http://health.sina.cn/news/2015-09-29/detail-ifxieymv7709919.d.html
                 * ctime : 2015-09-29 08:34:36.000
                 * intro : 中秋节，本来是一个欢庆团圆的日子，但是晚上6点多，在河南周口太康县人民医院门前发生了令人沉痛和震惊的一幕，医生和护士被逼着轮流抱着一名幼儿的尸体...
                 * url : http://health.sina.com.cn/news/2015-09-29/doc-ifxieymv7709919.shtml
                 * summary : 沃尔玛发声明称北京门店未售瘦肉精猪肉
                 北京青年报讯(记者 李佳)沃尔玛昨天发声明，称已暂停与合作商“北京森顺恒发商贸有限公司”的合作。该企业因供应瘦肉精猪肉被国家食药监局曝光。
                 * stitle : 沃尔玛称北京店未售瘦肉精肉
                 */

                private String id;
                private String title;
                private String keywords;
                private String img;
                private String tname;
                private String media_name;
                private String tid;
                private String wapurl;
                private String ctime;
                private String intro;
                private String url;
                private String summary;
                private String stitle;
                private List<ImagesBean> images;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getKeywords() {
                    return keywords;
                }

                public void setKeywords(String keywords) {
                    this.keywords = keywords;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getTname() {
                    return tname;
                }

                public void setTname(String tname) {
                    this.tname = tname;
                }

                public String getMedia_name() {
                    return media_name;
                }

                public void setMedia_name(String media_name) {
                    this.media_name = media_name;
                }

                public String getTid() {
                    return tid;
                }

                public void setTid(String tid) {
                    this.tid = tid;
                }

                public String getWapurl() {
                    return wapurl;
                }

                public void setWapurl(String wapurl) {
                    this.wapurl = wapurl;
                }

                public String getCtime() {
                    return ctime;
                }

                public void setCtime(String ctime) {
                    this.ctime = ctime;
                }

                public String getIntro() {
                    return intro;
                }

                public void setIntro(String intro) {
                    this.intro = intro;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getSummary() {
                    return summary;
                }

                public void setSummary(String summary) {
                    this.summary = summary;
                }

                public String getStitle() {
                    return stitle;
                }

                public void setStitle(String stitle) {
                    this.stitle = stitle;
                }

                public List<ImagesBean> getImages() {
                    return images;
                }

                public void setImages(List<ImagesBean> images) {
                    this.images = images;
                }

                public static class ImagesBean {
                    /**
                     * w : 312
                     * u : http://n.sinaimg.cn/transform/20150929/vGZZ-fxieyms4193794.jpg
                     * t : 手机拍摄：周口太康县人民医院医生护士被逼抱尸示众
                     * h : 550
                     */

                    private int w;
                    private String u;
                    private String t;
                    private int h;

                    public int getW() {
                        return w;
                    }

                    public void setW(int w) {
                        this.w = w;
                    }

                    public String getU() {
                        return u;
                    }

                    public void setU(String u) {
                        this.u = u;
                    }

                    public String getT() {
                        return t;
                    }

                    public void setT(String t) {
                        this.t = t;
                    }

                    public int getH() {
                        return h;
                    }

                    public void setH(int h) {
                        this.h = h;
                    }
                }
            }
        }
    }
}
