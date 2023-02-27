package net.daw.alist.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Post {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date date;
  private String title;

  @OneToMany
  private Set<User> upVotes = new HashSet<>();

  @OneToMany
  private Set<User> downVotes = new HashSet<>();
  
  @ManyToMany
  private List<Topic> topics = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  private List<PostItem> items = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();

  public Post() { }

  public Post(
    String title,
    List<Topic> topics,
    List<PostItem> items
  ) {
    this.date = new Date();
    this.title = title;
    this.topics = topics;
    this.items = items;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUpVotes(Set<User> upVotes) {
    this.upVotes = upVotes;
  }

  public void setDownVotes(Set<User> downVotes) {
    this.downVotes = downVotes;
  }

  public void setTopics(List<Topic> topics) {
    this.topics = topics;
  }

  public void setItems(List<PostItem> items) {
    this.items = items;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public Date getDate() {
    return date;
  }

  public String getTitle() {
    return title;
  }

  public Set<User> getUpVotes() {
    return upVotes;
  }

  public Set<User> getDownVotes() {
    return downVotes;
  }

  public List<Topic> getTopics() {
    return topics;
  }

  public List<PostItem> getItems() {
    return items;
  }
  
  public List<Comment> getComments() {
    return comments;
  }

}
