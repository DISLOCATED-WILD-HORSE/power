package entity.javabean;

import java.util.List;

public class ChildrenZtree {
    private int id;
    private int pid;
    private String name;
    private boolean isChecked;
    private List<ThirdChildrenTree> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public List<ThirdChildrenTree> getChildren() {
        return children;
    }

    public void setChildren(List<ThirdChildrenTree> children) {
        this.children = children;
    }
}
