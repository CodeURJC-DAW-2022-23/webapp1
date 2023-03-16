package net.daw.alist.models;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static net.daw.alist.utils.Utils.pathToImage;

@Entity
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private User author;
  private Date date;
  private String content;
  
  @Lob
  @JsonIgnore
  private Blob image;
  private String imagePath;

  public Comment() { }
  
  public Comment(
    User author,
    String content,
    String imagePath
  ) throws IOException, SQLException {
    this.date = new Date();
    this.author = author;
    this.content = content;
    setImage(imagePath);
    author.addComment(this);
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setImage(String imagePath) throws IOException, SQLException {
    if (imagePath == null) {
      imagePath = "static/images/notFound.jpg";
    }
    this.image = pathToImage(imagePath);
    this.imagePath = imagePath.replace("static", "");
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

  public String getImagePath() {
    return imagePath;
  }

  public Long getId() {return id;}
}
