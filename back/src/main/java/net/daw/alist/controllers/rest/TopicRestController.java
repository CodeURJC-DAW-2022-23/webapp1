package net.daw.alist.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.daw.alist.models.Topic;
import net.daw.alist.services.TopicService;
import net.daw.alist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicRestController {

  @Autowired
  private TopicService topicService;

  @Autowired
  private Utils utils;

  @Operation(summary = "Get specific topic")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Topic found", content = {
                  @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Topic.class)))
          }),
          @ApiResponse(responseCode = "404", description = "Topic not found", content = @Content)
  })
  @GetMapping("/byName/{topicName}")
  public ResponseEntity<Topic> getTopic(@PathVariable String topicName) {
    Optional<Topic> optionalTopic = topicService.findByName(topicName);
    if (optionalTopic.isPresent()) {
      Topic topic = optionalTopic.get();
      return new ResponseEntity<>(topic, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Get specific topic")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Topic found", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Topic.class)))
    }),
    @ApiResponse(responseCode = "404", description = "Topic not found", content = @Content)
  })
  @GetMapping("/{topicId}")
  public ResponseEntity<Topic> getTopic(@PathVariable long topicId) {
    Optional<Topic> optionalTopic = topicService.findById(topicId);
    if (optionalTopic.isPresent()) {
      Topic topic = optionalTopic.get();
      return new ResponseEntity<>(topic, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Create topic")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Topic created", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Topic.class)))
    }),
    @ApiResponse(responseCode = "403", description = "Create topic only for admin", content = @Content)
  })
  @PostMapping("/")
  public ResponseEntity<Topic> createTopic(Authentication auth, @RequestBody Data content) {
    if (auth == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    boolean isAdmin = utils.getCurrentUserRole(auth).equals("ADMIN");
    if (!isAdmin) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    Topic topic = new Topic(content.getName(), content.getDescription());
    topicService.save(topic);
    return new ResponseEntity<>(topic, HttpStatus.CREATED);
  }

  @Operation(summary = "Delete specific topic")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Topic deleted", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Topic.class)))
    }),
    @ApiResponse(responseCode = "403", description = "Delete topic only for admin", content = @Content),
    @ApiResponse(responseCode = "404", description = "Topic not found", content = @Content)
  })
  @DeleteMapping("/{topicId}")
  public ResponseEntity<Topic> deleteTopic(Authentication auth, @PathVariable long topicId) {
    if (auth == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    boolean isAdmin = utils.getCurrentUserRole(auth).equals("ADMIN");
    if (!isAdmin) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    Optional<Topic> optionalTopic = topicService.findById(topicId);
    if (optionalTopic.isPresent()) {
      Topic topic = optionalTopic.get();
      topicService.delete(topic);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Delete specific topic")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Topic deleted", content = {
                  @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Topic.class)))
          }),
          @ApiResponse(responseCode = "403", description = "Delete topic only for admin", content = @Content),
          @ApiResponse(responseCode = "404", description = "Topic not found", content = @Content)
  })
  @DeleteMapping("/byName/{topicName}")
  public ResponseEntity<Topic> deleteTopic(Authentication auth, @PathVariable String topicName) {
    if (auth == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    boolean isAdmin = utils.getCurrentUserRole(auth).equals("ADMIN");
    if (!isAdmin) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    Optional<Topic> optionalTopic = topicService.findByName(topicName);
    if (optionalTopic.isPresent()) {
      Topic topic = optionalTopic.get();
      topicService.delete(topic);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @AllArgsConstructor
  @Getter
  @Setter
  @EqualsAndHashCode
  private static class Data {
    private final String name;
    private final String description;
  }

}
