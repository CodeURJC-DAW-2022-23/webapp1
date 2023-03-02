package net.daw.alist.services;

import net.daw.alist.models.*;
import net.daw.alist.repositories.*;
import net.daw.alist.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static java.util.Arrays.asList;
import static net.daw.alist.models.UserRole.ADMIN;
import static net.daw.alist.models.UserRole.USER;
import static net.daw.alist.utils.Utils.pathToImage;

@Service
public class DatabaseInitializer {


    private CommentRepository commentRepository;

    @Autowired
    private PostItemRepository postItemRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() throws IOException {
        if (postRepository.count() == 0) {

            User cr7Lover = new User("CR7Lover", passwordEncoder.encode("1234"), "cr7@alist.com", USER);
            User manolo = new User("Manolo", passwordEncoder.encode("1234"), "manolo@alist.com", USER);
            User peepo = new User("Peepo", passwordEncoder.encode("1234"), "peepo@alist.com", USER);
            User shanks = new User("Shanks", passwordEncoder.encode("1234"), "shanks@alist.com", USER);
            User admin = new User("admin", passwordEncoder.encode("1234"), "admin@alist.com", ADMIN);

            shanks.setImage(pathToImage("static/example/shanks.jpg"), "static/example/shanks.jpg");
            peepo.setImage(pathToImage("static/example/peepo.jpg"), "static/example/peepo.jpg");
            cr7Lover.setImage(pathToImage("static/example/cr7_2.jpg"), "static/example/cr7_2.jpg");
            manolo.setImage(pathToImage("static/example/manolo.jpg"), "static/example/manolo.jpg");
            admin.setImage(pathToImage("static/example/admin.jpg"), "static/example/admin.jpg");


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


            PostItem attack_on_titan = new PostItem("Attack on Titan", "static/example/cr7.jpg", pathToImage("static/example/cr7.jpg"));
            PostItem fullmetal_alchemist = new PostItem("Fullmetal Alchemist", "static/example/fullmetalAlchemist.jpg", pathToImage("static/example/fullmetalAlchemist.jpg"));
            PostItem inazuma_eleven = new PostItem("Inazuma Eleven", "static/example/inazumaEleven.jpg", pathToImage("static/example/inazumaEleven.jpg"));
            PostItem my_little_pony = new PostItem("My Little Pony", "static/example/myLittlePony.jpg", pathToImage("static/example/myLittlePony.jpg"));
            PostItem one_piece = new PostItem("One Piece", "static/example/onePiece.jpg", pathToImage("static/example/onePiece.jpg"));
            PostItem giannis_antetokounmpo = new PostItem("Giannis Antetokounmpo", "static/example/gianis.jpg", pathToImage("static/example/gianis.jpg"));
            PostItem lebron_james = new PostItem("Lebron James", "static/example/lebron.jpg", pathToImage("static/example/lebron.jpg"));
            PostItem stephen_curry = new PostItem("Stephen Curry", "static/example/steph.jpg", pathToImage("static/example/steph.jpg"));
            PostItem kevin_durant = new PostItem("Kevin Durant", "static/example/kevinDurant.jpg", pathToImage("static/example/kevinDurant.jpg"));
            PostItem antony_davis = new PostItem("Antony Davis", "static/example/anthonyDavis.jpg", pathToImage("static/example/anthonyDavis.jpg"));
            PostItem getafe = new PostItem("Getafe", "static/example/getafe.jpg", pathToImage("static/example/getafe.jpg"));
            PostItem rayo_vayecano = new PostItem("Rayo Vayecano", "static/example/rayoVayecano.jpg", pathToImage("static/example/rayoVayecano.jpg"));
            PostItem betis = new PostItem("Betis", "static/example/betis.jpg", pathToImage("static/example/betis.jpg"));
            PostItem real_madrid = new PostItem("Real Madrid", "static/example/realMadrid.jpg", pathToImage("static/example/realMadrid.jpg"));
            PostItem barcelona = new PostItem("Barcelona", "static/example/barsa.jpg", pathToImage("static/example/barsa.jpg"));
            PostItem cr7 = new PostItem("Cristiano Ronaldo", "static/example/cr7.jpg", pathToImage("static/example/cr7.jpg"));

            Post post1 = new Post("CR7 is the best", asList(sports, football), asList(cr7, cr7, cr7, cr7, cr7));
            post1.setAuthor(cr7Lover);
            Post post2 = new Post("Best animes", asList(anime, series), asList(my_little_pony, inazuma_eleven, one_piece, fullmetal_alchemist, attack_on_titan));
            post2.setAuthor(shanks);
            Post post3 = new Post("Best LaLiga teams", asList(sports, football, laLiga), asList(getafe, rayo_vayecano, betis, real_madrid, barcelona));
            post3.setAuthor(peepo);
            Post post4 = new Post("Best NBA players", asList(sports, basketball, nba), asList(lebron_james, kevin_durant, giannis_antetokounmpo, stephen_curry, antony_davis));
            post4.setAuthor(manolo);

            Comment comment1 = new Comment("Messi is better than Cristiano", null, null);
            comment1.setAuthor(manolo);
            post1.addComment(comment1);
            Comment comment2 = new Comment("Where is LeganesCF in this Top?!?", null, null);
            comment1.setAuthor(shanks);
            post3.addComment(comment1);
            Comment comment3 = new Comment("Lebron is the GOAT", null, null);
            comment1.setAuthor(peepo);
            post4.addComment(comment1);

            userRepository.save(peepo);
            userRepository.save(shanks);
            userRepository.save(manolo);
            userRepository.save(cr7Lover);
            userRepository.save(admin);

            postItemRepository.save(attack_on_titan);
            postItemRepository.save(one_piece);
            postItemRepository.save(my_little_pony);
            postItemRepository.save(fullmetal_alchemist);
            postItemRepository.save(inazuma_eleven);
            postItemRepository.save(lebron_james);
            postItemRepository.save(giannis_antetokounmpo);
            postItemRepository.save(stephen_curry);
            postItemRepository.save(kevin_durant);
            postItemRepository.save(antony_davis);
            postItemRepository.save(getafe);
            postItemRepository.save(betis);
            postItemRepository.save(barcelona);
            postItemRepository.save(real_madrid);
            postItemRepository.save(rayo_vayecano);
            postItemRepository.save(cr7);

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

            commentRepository.save(comment1);
            commentRepository.save(comment2);
            commentRepository.save(comment3);

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);
            postRepository.save(post4);

        }
    }

}
