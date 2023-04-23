package net.daw.alist.models;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import static net.daw.alist.utils.Utils.pathToImage;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@Id")
public class PostItem {

    public Long getId() {
        return id;
    }

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
    String imagePath,
    boolean bool
  ) throws IOException, SQLException {
    this.description = description;
    setImage(imagePath);
  }

  public PostItem(
        String description,
        String base64Image
  ) throws IOException, SQLException {
    this.description = description;
    setImage(base64ToBlob(base64Image));
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

  public void setImage(String imagePath) throws IOException, SQLException {
    if (imagePath == null) {
      imagePath = "static/images/notFound.jpg";
    }
    this.image = pathToImage(imagePath);
  }

    public Blob base64ToBlob(String base64Image) throws SQLException {
        String base64Data = base64Image.substring(base64Image.indexOf(",") + 1);
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);
        Blob blob = new SerialBlob(imageBytes);
        return blob;
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

  public void setImageFile(Blob generateProxy) {
  }

}
