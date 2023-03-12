package net.daw.alist.services;

import net.daw.alist.utils.ItemField;
import org.springframework.stereotype.Service;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;

import java.io.IOException;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import net.daw.alist.repositories.PostItemRepository;
import net.daw.alist.models.PostItem;

import static net.daw.alist.utils.Utils.pathToImage;

@Service
@AllArgsConstructor
public class PostItemService {

  @Autowired
  PostItemRepository postItemRepository;
  
  public List<PostItem> getPostItems(List<ItemField> itemFields) throws IOException, SQLException {
    List<PostItem> postItems = new ArrayList<>();
    for (ItemField itemField : itemFields) {
      if (!itemField.getDescription().equals("")) {
        MultipartFile itemImageFile = itemField.getImage();
        Blob itemImage;
        if (itemImageFile == null) {
          itemImage = pathToImage("static/images/notFound.jpg");
        } else {
          itemImage = BlobProxy.generateProxy(
            itemImageFile.getInputStream(),
            itemImageFile.getSize()
          );
        }
        PostItem postItem = new PostItem(itemField.getDescription(), itemImage);
        postItemRepository.save(postItem);
        postItems.add(postItem);
      }
    }
    return postItems;
  }

  public Optional<PostItem> findById(Long id) {
    return this.postItemRepository.findById(id);
  }

}
