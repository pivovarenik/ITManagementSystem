package dto;

import dao.RoleDAO;
import entities.Role;
import entities.User;
import service.RoleService;

public class UserDTO {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private int age;
    private String country;
    private String city;
    private String profile_picture;
    private String phone;
    private boolean confirmed;
    private int role_id;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role_id = user.getRole() != null ? user.getRole().getId() : null;
        this.fullName = user.getFullName();
        this.age = user.getAge();
        this.country = user.getCountry();
        this.city = user.getCity();
        this.phone = user.getPhone();
        this.confirmed = user.getConfirmed();
        this.profile_picture = user.getProfilePictureUrl();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", profile_picture='" + profile_picture + '\'' +
                ", phone='" + phone + '\'' +
                ", confirmed=" + confirmed +
                ", role_id=" + role_id +
                '}';
    }
    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setFullName(this.fullName);
        user.setEmail(this.email);
        user.setAge(this.age);
        user.setCountry(this.country);
        user.setCity(this.city);
        user.setPhone(this.phone);
        user.setConfirmed(this.confirmed);
        user.setProfilePictureUrl(this.profile_picture);
        RoleDAO roleService = new RoleDAO();
        user.setRole(roleService.findRoleById(this.role_id));
        return user;
    }
}