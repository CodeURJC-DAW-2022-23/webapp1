package net.daw.alist.models;

import java.io.IOException;
import java.sql.Blob;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static net.daw.alist.utils.Utils.pathToImage;

@EqualsAndHashCode
@Entity
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date date;
  private String username;
  private String password;
  private String email;
  @Enumerated(EnumType.STRING)
  private UserRole role;

  private boolean enabled; //For email verification

  private boolean locked; //For banning

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  @Lob
  @JsonIgnore
  private Blob image;
  private String imagePath;

  @OneToMany
  private List<User> follows = new ArrayList<>();

  @OneToMany
  private List<User> followers = new ArrayList<>();

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Post> posts = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
  private List<Comment> comments = new ArrayList<>();

  public User() { }

  public User(
    String username,
    String password,
    String email,
    UserRole role
  ) {
    this.date = new Date();
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
    enabled = false; //Change this if you want to turn off email verification
    locked = false;
    // TODO: default image
    if (role.equals(UserRole.ADMIN)) {
      enabled = true;
    }
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

  public void setRole(UserRole role) {
    this.role = role;
  }

  public void setImage(String imagePath) throws IOException, SQLException {
    this.image = pathToImage(imagePath);
    this.imagePath = imagePath;
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

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public UserRole getRole() {
    return role;
  }

  public Blob getImage() {
    return image;
  }

  public String getImagePath() {
    return imagePath;
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

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority =
            new SimpleGrantedAuthority("ROLE_" + role.name());
    return Collections.singletonList(authority);
  }

  public void addComment(Comment comment){
    comments.add(comment);
  }

  public void addPost(Post post){
    posts.add(post);
  }

}
