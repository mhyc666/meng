package com.wangdh.mengm.bean;


import java.util.List;

public class MeizhiData {

    /**
     * error : false
     * results : [{"_id":"59a755a2421aa901c85e5fea","createdAt":"2017-08-31T08:17:38.117Z","desc":"8-31","publishedAt":"2017-08-31T08:22:07.982Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fj2ld81qvoj20u00xm0y0.jpg","used":true,"who":"daimajia"},{"_id":"59a35f6d421aa901b9dc4643","createdAt":"2017-08-28T08:10:21.141Z","desc":"8-28","publishedAt":"2017-08-29T12:19:18.634Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fiz4ar9pq8j20u010xtbk.jpg","used":true,"who":"代码家"},{"_id":"599e2220421aa901b9dc462d","createdAt":"2017-08-24T08:47:28.949Z","desc":"8-24","publishedAt":"2017-08-24T12:43:10.124Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fiuiw5givwj20u011h79a.jpg","used":true,"who":"daimajia"},{"_id":"599ccace421aa901c85e5fb8","createdAt":"2017-08-23T08:22:38.611Z","desc":"8-23","publishedAt":"2017-08-23T12:12:15.166Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fitcjyruajj20u011h412.jpg","used":true,"who":"daimajia"},{"_id":"599b7cf5421aa901c1c0a867","createdAt":"2017-08-22T08:38:13.732Z","desc":"8-22","publishedAt":"2017-08-22T12:02:15.769Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fis7dvesn6j20u00u0jt4.jpg","used":true,"who":"代码家"},{"_id":"599a299a421aa901b9dc460f","createdAt":"2017-08-21T08:30:18.487Z","desc":"8-21","publishedAt":"2017-08-21T11:38:57.363Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fir1jbpod5j20ip0newh3.jpg","used":true,"who":"daimajia"},{"_id":"599386fe421aa9672cdf0812","createdAt":"2017-08-16T07:42:54.135Z","desc":"8-16","publishedAt":"2017-08-17T11:36:42.967Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fil82i7zsmj20u011hwja.jpg","used":true,"who":"daimajia"},{"_id":"599237dd421aa96729c57246","createdAt":"2017-08-15T07:53:01.962Z","desc":"8-15","publishedAt":"2017-08-15T13:32:36.998Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fik2q1k3noj20u00u07wh.jpg","used":true,"who":"daimajia"},{"_id":"59907386421aa9672cdf07ff","createdAt":"2017-08-13T23:43:02.253Z","desc":"8-13","publishedAt":"2017-08-14T17:04:50.266Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fiiiyfcjdoj20u00u0ju0.jpg","used":true,"who":"dmj"},{"_id":"598bb8f0421aa90ca3bb6c01","createdAt":"2017-08-10T09:37:52.684Z","desc":"8-10","publishedAt":"2017-08-11T14:05:53.749Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fiednrydq8j20u011itfz.jpg","used":true,"who":"带马甲"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 59a755a2421aa901c85e5fea
         * createdAt : 2017-08-31T08:17:38.117Z
         * desc : 8-31
         * publishedAt : 2017-08-31T08:22:07.982Z
         * source : chrome
         * type : 福利
         * url : https://ws1.sinaimg.cn/large/610dc034ly1fj2ld81qvoj20u00xm0y0.jpg
         * used : true
         * who : daimajia
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
