package net.daw.alist.services;

import net.daw.alist.models.*;
import net.daw.alist.repositories.*;
import net.daw.alist.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;

import static java.util.Arrays.asList;
import static net.daw.alist.models.UserRole.ADMIN;
import static net.daw.alist.models.UserRole.USER;

@Service
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PostItemRepository postItemRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() throws IOException, SQLException {
        if (postRepository.count() == 0) {

            User cr7Lover = new User("CR7Lover", passwordEncoder.encode("1234"), "cr7@alist.com", USER);
            cr7Lover.setImage("static/images/example/cr7_2.jpg");
            User manolo = new User("Manolo", passwordEncoder.encode("1234"), "manolo@alist.com", USER);
            manolo.setImage("static/images/example/manolo.jpg");
            User peepo = new User("Peepo", passwordEncoder.encode("1234"), "peepo@alist.com", USER);
            peepo.setImage("static/images/example/peepo.jpg");
            User shanks = new User("Shanks", passwordEncoder.encode("1234"), "shanks@alist.com", USER);
            shanks.setImage("static/images/example/shanks.jpg");
            User admin = new User("admin", passwordEncoder.encode("1234"), "admin@alist.com", ADMIN);
            admin.setImage("static/images/example/admin.jpg");

            userRepository.save(peepo);
            userRepository.save(shanks);
            userRepository.save(manolo);
            userRepository.save(cr7Lover);
            userRepository.save(admin);

            Topic sports = new Topic("Sports", "General topic about sports");
            Topic football = new Topic("Football", "Topic about football teams, players, etc");
            Topic basketball = new Topic("Basketball", "Topic about basketball teams, players, etc");
            Topic cinema = new Topic("Cinema", "General topic about cinema");
            Topic films = new Topic("Films", "Topic about films, actors, etc");
            Topic series = new Topic("Series", "Topic about series, actors, etc");
            Topic tennis = new Topic("Tennis", "Topic about tennis players, tournaments etc");
            Topic tech = new Topic("Tech", "General topic about tech");
            Topic webPages = new Topic("Web Pages", "General topic about web pages");
            Topic nba = new Topic("NBA", "Topic about NBA");
            Topic laLiga = new Topic("LaLiga", "Topic about LaLiga");
            Topic anime = new Topic("Anime", "General topic about web pages");
            Topic programming = new Topic("Programming", "General topic about programming");
            Topic f1 = new Topic("F1", "General topic about F1");

            topicRepository.save(sports);
            topicRepository.save(football);
            topicRepository.save(basketball);
            topicRepository.save(cinema);
            topicRepository.save(films);
            topicRepository.save(series);
            topicRepository.save(tennis);
            topicRepository.save(tech);
            topicRepository.save(webPages);
            topicRepository.save(nba);
            topicRepository.save(laLiga);
            topicRepository.save(anime);
            topicRepository.save(programming);
            topicRepository.save(f1);

            PostItem attackOnTitan = new PostItem("Attack on Titan", "static/images/example/cr7.jpg");
            PostItem fullMetalAlchemist = new PostItem("Fullmetal Alchemist", "static/images/example/fullmetalAlchemist.jpg");
            PostItem inazumaEleven = new PostItem("Inazuma Eleven", "static/images/example/inazumaEleven.jpg");
            PostItem myLittlePony = new PostItem("My Little Pony", "static/images/example/myLittlePony.jpg");
            PostItem onePiece = new PostItem("One Piece", "static/images/example/onePiece.jpg");
            PostItem giannisAntetokounmpo = new PostItem("Giannis Antetokounmpo", "static/images/example/gianis.jpg");
            PostItem lebronJames = new PostItem("Lebron James", "static/images/example/lebron.jpg");
            PostItem stephenCurry = new PostItem("Stephen Curry", "static/images/example/steph.jpg");
            PostItem kevinDurant = new PostItem("Kevin Durant", "static/images/example/kevinDurant.jpg");
            PostItem antonyDavis = new PostItem("Antony Davis", "static/images/example/anthonyDavis.jpg");
            PostItem getafe = new PostItem("Getafe", "static/images/example/getafe.jpg");
            PostItem rayoVayecano = new PostItem("Rayo Vayecano", "static/images/example/rayoVayecano.jpg");
            PostItem betis = new PostItem("Betis", "static/images/example/betis.jpg");
            PostItem realMadrid = new PostItem("Real Madrid", "static/images/example/realMadrid.jpg");
            PostItem barcelona = new PostItem("Barcelona", "static/images/example/barsa.jpg");
            PostItem cr7 = new PostItem("Cristiano Ronaldo", "static/images/example/cr7.jpg");

            postItemRepository.save(attackOnTitan);
            postItemRepository.save(onePiece);
            postItemRepository.save(myLittlePony);
            postItemRepository.save(fullMetalAlchemist);
            postItemRepository.save(inazumaEleven);
            postItemRepository.save(lebronJames);
            postItemRepository.save(giannisAntetokounmpo);
            postItemRepository.save(stephenCurry);
            postItemRepository.save(kevinDurant);
            postItemRepository.save(antonyDavis);
            postItemRepository.save(getafe);
            postItemRepository.save(betis);
            postItemRepository.save(barcelona);
            postItemRepository.save(realMadrid);
            postItemRepository.save(rayoVayecano);
            postItemRepository.save(cr7);

            Post firstPost = new Post(cr7Lover, "CR7 is the best", asList(sports, football), asList(cr7));
            Post secondPost = new Post(shanks, "Best animes", asList(anime, series), asList(myLittlePony, inazumaEleven, onePiece, fullMetalAlchemist, attackOnTitan));
            Post thirdPost = new Post(peepo, "Best LaLiga teams", asList(sports, football, laLiga), asList(getafe, rayoVayecano, betis, realMadrid, barcelona));
            Post fourthPost = new Post(manolo, "Best NBA players", asList(sports, basketball, nba), asList(lebronJames, kevinDurant, giannisAntetokounmpo, stephenCurry, antonyDavis));

            postRepository.save(firstPost);
            postRepository.save(secondPost);
            postRepository.save(thirdPost);
            postRepository.save(fourthPost);

            Comment firstComment = new Comment(manolo, "Messi is better than Cristiano", null);
            firstPost.addComment(firstComment);
            Comment secondComment = new Comment(shanks, "Where is LeganesCF in this Top?!?", null);
            thirdPost.addComment(secondComment);
            Comment thirdComment = new Comment(peepo, "Lebron is the GOAT", null);
            fourthPost.addComment(thirdComment);

            commentRepository.save(firstComment);
            commentRepository.save(secondComment);
            commentRepository.save(thirdComment);

        }
    }

}
