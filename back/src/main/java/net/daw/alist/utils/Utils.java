package net.daw.alist.utils;

import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.Model;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class Utils {
    public Utils(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    public static Blob pathToImage(String path) throws IOException, SQLException {
        if (path == null) return null;
        Resource image = new ClassPathResource(path);
        Blob blob = BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
        return new SerialBlob(blob);
    }
    public void searchBarInitializer(Model model){
        List<User> userList = userService.findAll();
        model.addAttribute("searchSuggestedUsers", userList);
        List<Post> postList = postService.findAll();
        model.addAttribute("searchSuggestedPosts", postList);
    }

}
