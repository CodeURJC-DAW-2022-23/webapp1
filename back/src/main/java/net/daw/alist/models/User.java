package net.daw.alist.models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date date;
  private String username;
  private String password;
  private String email;
  private Boolean admin;

  @Lob
  @JsonIgnore
  private Blob picture;

  @ManyToMany(mappedBy="user")
  private List<User> follows = new ArrayList<>();

  @OneToMany
  private List<Post> posts = new ArrayList<>();

  public User() { }
  
  public User(
    String username,
    String password,
    String email,
    Boolean admin,
    Blob picture
  ) {
    this.date = new Date();
    this.username = username;
    this.password = password;
    this.email = email;
    this.admin = admin;
    this.picture = picture;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public void setPicture(Blob picture) {
    this.picture = picture;
  }

  public void setFollows(List<User> follows) {
    this.follows = follows;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }

  public Date getDate() {
    return date;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public Blob getPicture() {
    return picture;
  }

  public List<User> getFollows() {
    return follows;
  }

  public List<Post> getPosts() {
    return posts;
  }

}
