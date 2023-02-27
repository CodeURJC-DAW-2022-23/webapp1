package net.daw.alist.models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

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
  private Blob imageFile;
  private String image;

  @OneToMany
  private List<User> follows = new ArrayList<>();

  @OneToMany
  private List<User> followers = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  private List<Post> posts = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy="author")
  private List<Comment> comments = new ArrayList<>();

  public User() { }
  
  public User(
    String username,
    String password,
    String email,
    Boolean admin
  ) {
    this.date = new Date();
    this.username = username;
    this.password = password;
    this.email = email;
    this.admin = admin;
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

  public void setImage(Blob imageFile, String image) {
    this.imageFile = imageFile;
    this.image = image;
  }

  public void setFollows(List<User> follows) {
    this.follows = follows;
  }

  public void setFollowers(List<User> followers) {
    this.followers = followers;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
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

  public Blob getImageFile() {
    return imageFile;
  }

  public String getImage() {
    return image;
  }

  public List<User> getFollows() {
    return follows;
  }

  public List<User> getFollowers() {
    return followers;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public List<Comment> getComments() {
    return comments;
  }

}
