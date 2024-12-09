package org.models;


public class User {

    private String username;
    private String password;
    private String fullName;
    private String email;
    private int age;
    private String country;
    private String city;
    private String profile_picture;
    private String phone;

    public User(String username,String password){
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
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", profile_picture='" + profile_picture + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}