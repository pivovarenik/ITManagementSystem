package org.models;


public class User {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(int id, String username, String password, String fullName, String email, int age, String country, String city, String profile_picture, String phone, boolean confirmed, int role_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.country = country;
        this.city = city;
        this.profile_picture = profile_picture;
        this.phone = phone;
        this.confirmed = confirmed;
        this.role_id = role_id;
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
        profile_picture = "";
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
        this.profile_picture = "";
        this.phone = "";
    }

    public User(String username, String fullName, String password, int age, String email, String country, String city, String profile_picture, String phone, boolean confirmed, int role_id) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.country = country;
        this.city = city;
        this.profile_picture = profile_picture;
        this.phone = phone;
        this.confirmed = confirmed;
        this.role_id = role_id;
    }

    public User(String username, String password, String email, String fullName, int age, String country, String city, String profile_picture, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.country = country;
        this.city = city;
        this.profile_picture = profile_picture;
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
                ", profile_picture='" + profile_picture + '\'' +
                ", phone='" + phone + '\'' +
                ", confirmed=" + confirmed +
                ", role_id=" + role_id +
                '}';
    }
}