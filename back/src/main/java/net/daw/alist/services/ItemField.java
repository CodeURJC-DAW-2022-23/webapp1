package net.daw.alist.services;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;


@Getter
public class ItemField {
  String description;
  MultipartFile image;
  
  public ItemField(String description, MultipartFile image)
  {
    this.description = description;
    this.image = image;
  }
}
