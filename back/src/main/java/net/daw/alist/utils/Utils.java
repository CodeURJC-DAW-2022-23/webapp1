package net.daw.alist.utils;

import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class Utils {

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

    public String getCurrentUserRole(Authentication authentication) {
        if(authentication == null)
            return "GUEST";
        User loggedUser = (User) authentication.getPrincipal();
        Optional<User> user = userService.findByID(loggedUser.getId());
        if (user.isPresent()){
            return user.get().getRole().toString();
        }
        else{
            throw new RuntimeException();
        }
    }

}
