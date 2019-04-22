package entity.javabean;

import java.io.Serializable;
import java.util.List;

public class ParentZtree implements Serializable {
    private int id;
    private int pid;
    private String name;
    private boolean isChecked;
    private List<ChildrenZtree> children;

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

    public List<ChildrenZtree> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenZtree> children) {
        this.children = children;
    }
}
