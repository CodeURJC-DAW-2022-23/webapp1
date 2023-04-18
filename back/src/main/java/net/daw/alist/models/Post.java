package net.daw.alist.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@Id")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonIgnore
  @ManyToOne
  private User author;
  private String authorName;

  public String getAuthorName() {
    return authorName = author.getUsername();
  }

  private Date date;
  private String title;

  @JsonIgnore
  @ManyToMany
  private Set<User> upVotes = new HashSet<>();

  @JsonIgnore
  @ManyToMany

  private Set<User> downVotes = new HashSet<>();

  private int numUpvotes = upVotes.size();

  private int numDownvotes = downVotes.size();

  private int votes;

  @JsonIgnore
  @ManyToMany
  private List<Topic> topics = new ArrayList<>();

  public List<String> getTopicNames() {
    return topicNames;
  }

  @ElementCollection
  private List<String> topicNames = new ArrayList<>();

  @OneToMany (orphanRemoval = true)
  private List<PostItem> items = new ArrayList<>();

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  public Post() { }

  public Post(
    User author,
    String title,
    List<Topic> topics,
    List<PostItem> items
    
  ) {
    this.date = new Date();
    this.author = author;
    authorName = author.getUsername();
    this.title = title;
    this.items = items;
    this.topics = topics;
    topicNames = topics.stream().map( topic -> topic.getName()).collect(Collectors.toList());

    updateVotes();
    author.addPost(this);
  }

  public void setAuthor(User author) {
    this.author = author;
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

  public User getAuthor() {
    return author;
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
  public void updateVotes(){
    numDownvotes=downVotes.size();
    numUpvotes=upVotes.size();
    votes = numUpvotes-numDownvotes;
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

  public void addComment(Comment comment){
    this.comments.add(comment);
  }
  public void addUpVote(User user) { this.upVotes.add(user);}
  public void removeUpVote(User user) {this.upVotes.remove(user);}

  public void addDownVote(User user){ this.downVotes.add(user); }

  public void removeDownVote(User user) { this.downVotes.remove(user); }
}
