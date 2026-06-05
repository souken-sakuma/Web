package com.example.demo.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(unique = true)
    private Long id;
    private String name;
    private Integer age;
    private String username;
    private String password;
    private String role;
    private String memberNumber;

    public User() { 
    	
    }
    
    public User(String username, String password, String role, String name, Integer age, String memberNumber) {
    	this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.age = age;
        this.memberNumber = memberNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getMemberNumber() {
    	return memberNumber;
    }
    
    public void setMemberNumber(String memberNumber) {
    	this.memberNumber = memberNumber;
    }
}
