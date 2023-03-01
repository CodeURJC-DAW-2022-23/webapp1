package net.daw.alist.models;

import java.io.IOException;
import java.sql.Blob;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Entity
public class PostItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String description;

  @Lob
  @JsonIgnore
  private Blob imageFile;
  private String image;

  public PostItem() { }
  
  public PostItem(
    String description,
    String image,
    Blob imageFile
  ) {
    this.description = description;
    this.imageFile = imageFile;
    this.image = image;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setImage(Blob imageFile, String image) {
    this.imageFile = imageFile;
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public Blob getImageFile() {
    return imageFile;
  }

  public String getImage() {
    return image;
  }

  public void setImageFile(Blob generateProxy) {
  }

}
