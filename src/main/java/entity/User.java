package entity;

public class User {
    private String userid;
    private String username;
    private String password;
    private String remark;
    private boolean isdisable;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isIsdisable() {
        return isdisable;
    }

    public void setIsdisable(boolean isdisable) {
        this.isdisable = isdisable;
    }
}
