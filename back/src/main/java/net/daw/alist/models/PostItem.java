package net.daw.alist.models;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static net.daw.alist.utils.Utils.pathToImage;

@Entity
public class PostItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String description;

  @Lob
  @JsonIgnore
  private Blob image;
  private String imagePath;

  public PostItem() { }
  
  public PostItem(
    String description,
    String imagePath
  ) throws IOException, SQLException {
    this.description = description;
    setImage(imagePath);
  }

 public PostItem(
    String description,
    Blob image,
    String imagePath
  ) {
    this.description = description;
    this.imagePath = imagePath;
    this.image = image;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setImage(String imagePath) throws IOException, SQLException {
    this.image = pathToImage(imagePath);
    this.imagePath = imagePath;
  }

  public String getDescription() {
    return description;
  }

  public Blob getImage() {
    return image;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImageFile(Blob generateProxy) {
  }

}
