package progetti.restapitest.controller;

import progetti.restapitest.dto.CreateUserRequest;
import progetti.restapitest.dto.UserResponse;
import progetti.restapitest.entities.User;
import progetti.restapitest.security.CustomUserDetails;
import progetti.restapitest.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    private User getLoggedUser() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
        return userDetails.getUser();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsersList() {
        User logged = getLoggedUser();
        if (!logged.getRoleType().name().equals("ADMIN")) {
            return ResponseEntity.status(403).build();
        }
        List<UserResponse> usersDto = userService.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User logged = getLoggedUser();
        if (!logged.getRoleType().name().equals("ADMIN")) {
            return ResponseEntity.status(403).build();
        }
        UserResponse userDto = UserResponse.from(userService.findById(id));
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        User logged = getLoggedUser();
        if (!logged.getRoleType().name().equals("ADMIN")) {
            return ResponseEntity.status(403).build();
        }
        UserResponse userDto = UserResponse.from(userService.findByEmail(email));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/createUser")
    public ResponseEntity<CreateUserRequest> createUser(@RequestBody CreateUserRequest request){
        CreateUserRequest dto = CreateUserRequest.from(
                userService.createUser(request.getName(), request.getEmail(), request.getPassword())
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        User logged = getLoggedUser();
        return ResponseEntity.ok(UserResponse.from(logged));
    }
}
