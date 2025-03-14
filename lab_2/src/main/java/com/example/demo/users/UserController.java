package com.example.demo.users;
import com.example.demo.users.dto.CreateUsersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping(path = "api/lab2_Aspect")

public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Integer id) {
        return this.userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody CreateUsersDto createUserDto) {
        return this.userService.createUser(createUserDto);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody CreateUsersDto updatedUser) {
        return this.userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        return this.userService.deleteUser(id);
    }
}
