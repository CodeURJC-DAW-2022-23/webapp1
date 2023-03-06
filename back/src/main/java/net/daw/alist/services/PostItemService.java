package net.daw.alist.services;

import net.daw.alist.utils.ItemField;
import org.springframework.stereotype.Service;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.URI;

import java.sql.Blob;
import java.util.List;
import java.util.ArrayList;

import net.daw.alist.repositories.PostItemRepository;
import net.daw.alist.models.PostItem;

@Service
@AllArgsConstructor
public class PostItemService {

  @Autowired
  PostItemRepository postItemRepository;
  
  public List<PostItem> getPostItems(List<ItemField> itemFields) throws IOException {
    List<PostItem> postItems = new ArrayList<>();
    for (ItemField itemField : itemFields) {
      if (!itemField.getDescription().equals("")) {
        MultipartFile itemImageFile = itemField.getImage();
        Blob itemImage = BlobProxy.generateProxy(
          itemImageFile.getInputStream(),
          itemImageFile.getSize()
        );
        PostItem postItem = new PostItem(itemField.getDescription(), itemImage);
        postItemRepository.save(postItem);
        postItems.add(postItem);
      }
    }
    return postItems;
  }

}
