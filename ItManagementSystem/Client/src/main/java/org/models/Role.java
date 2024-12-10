package org.models;

public class Role {
    private int id;
    private String name;
    private String description;

    public Role(String roleName, String roleDesc) {
        this.name = roleName;
        this.description = roleDesc;
    }

    public Role(int id, String roleName, String roleDesc) {
        this.id = id;
        this.name = roleName;
        this.description = roleDesc;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + name + '\'' +
                ", roleDesc='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role() {
    }

    public String getRoleName() {
        return name;
    }

    public void setRoleName(String roleName) {
        this.name = roleName;
    }

    public String getRoleDesc() {
        return description;
    }

    public void setRoleDesc(String roleDesc) {
        this.description = roleDesc;
    }

}
