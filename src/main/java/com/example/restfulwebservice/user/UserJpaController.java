package com.example.restfulwebservice.user;

import com.example.restfulwebservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/jpa")
public class UserJpaController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("ID[%s] not found", id)));
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUserByUser(@PathVariable int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("ID[%s] not found", id)));
        return user.getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Integer id, @Valid @RequestBody Post post) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("ID[%s] not found", id)));
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
