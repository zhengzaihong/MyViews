package dz.solc.myviews.bean;

import java.util.List;

public class PersonInfoBean {


    /**
     * code : 1
     * data : [{"job":"软件工程师","createdTime":1557476551,"name":"张三","money":"6k"},{"job":"测试人员","createdTime":1557476552,"name":"李四","money":"7k"},{"job":"产品经理","createdTime":1557476553,"name":"王五","money":"10k"},{"job":"市场营销","createdTime":1557476554,"name":"王二腻","money":"8k"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * job : 软件工程师
         * createdTime : 1557476551
         * name : 张三
         * money : 6k
         */

        private String job;
        private String createdTime;
        private String name;
        private String money;

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
