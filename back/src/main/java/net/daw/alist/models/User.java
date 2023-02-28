package net.daw.alist.models;

import java.sql.Blob;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
  private Blob picture;

  @OneToMany
  private List<User> follows = new ArrayList<>();

  @OneToMany
  private List<User> followers = new ArrayList<>();

  @OneToMany
  private List<Post> posts = new ArrayList<>();

  public User() { }
  
  public User(
    String username,
    String password,
    String email,
    UserRole role,
    Blob picture
  ) {
    this.date = new Date();
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
    enabled = false; //Change this if you want to turn off email verification
    locked = false;
    this.picture = picture;

    if(role.equals(UserRole.ADMIN)){
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

  public void setPicture(Blob picture) {
    this.picture = picture;
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

  public Blob getPicture() {
    return picture;
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


}
