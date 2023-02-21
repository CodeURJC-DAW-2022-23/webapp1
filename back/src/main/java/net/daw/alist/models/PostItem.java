package net.daw.alist.models;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PostItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String description;

  @Lob
  @JsonIgnore
  private Blob image;

  public PostItem() { }
  
  public PostItem(
    String description,
    Blob image
  ) {
    this.description = description;
    this.image = image;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setImage(Blob image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public Blob getImage() {
    return image;
  }
  
}
