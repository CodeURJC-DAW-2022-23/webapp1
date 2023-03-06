package net.daw.alist.services;

import org.springframework.stereotype.Service;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;


import lombok.AllArgsConstructor;


import java.io.IOException;
import java.net.URI;

import java.util.List;
import java.util.ArrayList;

import net.daw.alist.repositories.PostItemRepository;
import net.daw.alist.models.PostItem;




@Service
@AllArgsConstructor
public class PostItemService {

  @Autowired
  PostItemRepository item;
  
  public List<PostItem> itemList(ArrayList<ItemField> list) throws IOException
  {
    List<PostItem> result = new ArrayList<>();
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    int i = 1;

    for (ItemField iterator : list)
    {
      if (!iterator.description.equals(""))
      {
        PostItem itemNew = new PostItem(iterator.getDescription(),
          (BlobProxy.generateProxy(iterator.getImage().getInputStream(), iterator.getImage().getSize())),
          (String) location.toString() + Integer.toString(i));
        item.save(itemNew);
        result.add(itemNew);
      }
      i++;
    }

    return result;
  }

}
