package org.models;


import java.util.Objects;

public class User {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private int age;
    private String country;
    private String city;
    private String profilePictureUrl;
    private String phone;
    private boolean confirmed;
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && confirmed == user.confirmed && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(fullName, user.fullName) && Objects.equals(email, user.email) && Objects.equals(country, user.country) && Objects.equals(city, user.city) && Objects.equals(profilePictureUrl, user.profilePictureUrl) && Objects.equals(phone, user.phone) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, fullName, email, age, country, city, profilePictureUrl, phone, confirmed, role);
    }

    public User(int id, String username, String password, String fullName, String email, int age, String country, String city, String profilePictureUrl, String phone, boolean confirmed, Role role_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.country = country;
        this.city = city;
        this.profilePictureUrl = profilePictureUrl;
        this.phone = phone;
        this.confirmed = confirmed;
        this.role = role_id;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role_id) {
        this.role = role_id;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    public User() {
        username = "";
        password = "";
        fullName = "";
        email = "";
        age = 0;
        country = "";
        city = "";
        profilePictureUrl = "";
        phone = "+375333333333";
    }

    public User(String username, String password, String fullName,  String email, int age, String country, String city) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.country = country;
        this.city = city;
        this.profilePictureUrl = "";
        this.phone = "";
    }

    public User(String username, String fullName, String password, int age, String email, String country, String city, String profilePictureUrl, String phone, boolean confirmed, Role role_id) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.country = country;
        this.city = city;
        this.profilePictureUrl = profilePictureUrl;
        this.phone = phone;
        this.confirmed = confirmed;
        this.role = role_id;
    }

    public User(String username, String password, String email, String fullName, int age, String country, String city, String profilePictureUrl, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.country = country;
        this.city = city;
        this.profilePictureUrl = profilePictureUrl;
        this.phone = phone;
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

    public String getprofilePictureUrl() {
        return profilePictureUrl;
    }

    public void setprofilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", phone='" + phone + '\'' +
                ", confirmed=" + confirmed +
                ", role=" + role +
                '}';
    }
}