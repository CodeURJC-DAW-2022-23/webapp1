package net.daw.alist.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private String description;

  @ManyToMany
  private List<Post> posts = new ArrayList<>();

  public Topic() { }
  
  public Topic(
    String name,
    String description
  ) {
    this.name = name;
    this.description = description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<Post> getPosts() {
    return posts;
  }

}
