package net.daw.alist.models;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import static net.daw.alist.utils.Utils.pathToImage;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@Id")
public class PostItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String description;
  private String imagePath;

  @Lob
  @JsonIgnore
  private Blob image;


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
    Blob image
  ) {
    this.description = description;
    this.image = image;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public void setImage(String imagePath) throws IOException, SQLException {
    if (imagePath == null) {
      imagePath = "static/images/notFound.jpg";
    }
    this.image = pathToImage(imagePath);
  }

  public void setImage(Blob image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }
  public String getImagePath() {
    return imagePath;
  }
  public Blob getImage() {
    return image;
  }

  public void setImageFile(Blob generateProxy) {
  }

}
