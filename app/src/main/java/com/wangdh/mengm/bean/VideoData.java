package com.wangdh.mengm.bean;

import java.util.List;

public class VideoData {


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
                 * text :
                 她在京都穿汉服上下班，日本当地人赞叹：像仙女

                 * hate : 10
                 * videotime : 0
                 * voicetime : 0
                 * weixin_url : http://m.budejie.com/detail-26213798.html/
                 * profile_image : http://wimg.spriteapp.cn/profile/large/2016/07/26/57974925b34a6_mini.jpg
                 * width : 0
                 * voiceuri :
                 * type : 10
                 * ct : 2017-09-01 14:20:03.568
                 * id : 26213798
                 * love : 77
                 * height : 0
                 * _id : 59a8fc136e365cb148601935
                 * name : 我有了一个男朋友ヽ、
                 * create_time : 2017-09-01 14:20:03
                 * voicelength : 0
                 * video_uri : http://mvideo.spriteapp.cn/video/2017/0831/59a7c5b0f41c9cut_wpc.mp4
                 */

                private String text;
                private String hate;
                private String videotime;
                private String voicetime;
                private String weixin_url;
                private String profile_image;
                private String width;
                private String voiceuri;
                private String type;
                private String ct;
                private String id;
                private String love;
                private String height;
                private String _id;
                private String name;
                private String create_time;
                private String voicelength;
                private String video_uri;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getHate() {
                    return hate;
                }

                public void setHate(String hate) {
                    this.hate = hate;
                }

                public String getVideotime() {
                    return videotime;
                }

                public void setVideotime(String videotime) {
                    this.videotime = videotime;
                }

                public String getVoicetime() {
                    return voicetime;
                }

                public void setVoicetime(String voicetime) {
                    this.voicetime = voicetime;
                }

                public String getWeixin_url() {
                    return weixin_url;
                }

                public void setWeixin_url(String weixin_url) {
                    this.weixin_url = weixin_url;
                }

                public String getProfile_image() {
                    return profile_image;
                }

                public void setProfile_image(String profile_image) {
                    this.profile_image = profile_image;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getVoiceuri() {
                    return voiceuri;
                }

                public void setVoiceuri(String voiceuri) {
                    this.voiceuri = voiceuri;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getCt() {
                    return ct;
                }

                public void setCt(String ct) {
                    this.ct = ct;
                }


                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getLove() {
                    return love;
                }

                public void setLove(String love) {
                    this.love = love;
                }


                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getVoicelength() {
                    return voicelength;
                }

                public void setVoicelength(String voicelength) {
                    this.voicelength = voicelength;
                }

                public String getVideo_uri() {
                    return video_uri;
                }

                public void setVideo_uri(String video_uri) {
                    this.video_uri = video_uri;
                }
            }
        }
    }
}
