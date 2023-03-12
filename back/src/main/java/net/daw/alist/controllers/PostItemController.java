package net.daw.alist.controllers;

import net.daw.alist.models.PostItem;
import net.daw.alist.services.PostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Controller
public class PostItemController {

    @Autowired
    private PostItemService postItemService;

    @GetMapping({"/{id}/image", "/*/{id}/image"})
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
        Optional<PostItem> postItem = postItemService.findById(id);
        if (postItem.isPresent() && postItem.get().getImage() != null) {
            Blob image = postItem.get().getImage();
            InputStreamResource file = new InputStreamResource(image.getBinaryStream());
            return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_TYPE,
                "image/jpeg"
            ).contentLength(image.length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
