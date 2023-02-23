package net.daw.alist.models;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date date;
  private String content;

  @OneToOne
  private User author;
  
  @Lob
  @JsonIgnore
  private Blob image;

  public Comment() { }
  
  public Comment(
    User author,
    String content,
    Blob image
  ) {
    this.date = new Date();
    this.author = author;
    this.content = content;
    this.image = image;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setImage(Blob image) {
    this.image = image;
  }

  public Date getDate() {
    return date;
  }

  public User getAuthor() {
    return author;
  }

  public String getContent() {
    return content;
  }

  public Blob getImage() {
    return image;
  }

}
