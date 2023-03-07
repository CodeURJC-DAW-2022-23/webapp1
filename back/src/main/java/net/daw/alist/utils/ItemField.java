package net.daw.alist.utils;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemField {

  private String description;
  private MultipartFile image;

}
