package com.fils.fils.controller;

import com.fils.fils.model.Coordinator;
import com.fils.fils.model.User;
import com.fils.fils.repository.CoordinatorRepository;
import com.fils.fils.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @PutMapping("/update-project/{id}")
    public ResponseEntity<String> updateProject(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setProject(updatedUser.getProject());
            existingUser.setProjectDescription(updatedUser.getProjectDescription());
            userRepository.save(existingUser);
            return ResponseEntity.ok("Project updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        Coordinator coordinator = coordinatorRepository.findByUsername(loginRequest.getUsername());
    
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("/login/home"));
        } else if (coordinator != null && coordinator.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("/login/home_coor"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    

    @PutMapping("/update-coordinator")
    public ResponseEntity<String> updateCoordinator(@RequestBody Coordinator updatedCoordinator) {
        Coordinator existingCoordinator = coordinatorRepository.findById(updatedCoordinator.getId()).orElse(null);
        if (existingCoordinator != null) {
            existingCoordinator.setName(updatedCoordinator.getName());
            existingCoordinator.setSurname(updatedCoordinator.getSurname());
            existingCoordinator.setOffice(updatedCoordinator.getOffice());
            existingCoordinator.setUsername(updatedCoordinator.getUsername());
            if (updatedCoordinator.getPassword() != null && !updatedCoordinator.getPassword().isEmpty()) {
                existingCoordinator.setPassword(updatedCoordinator.getPassword());
            }
            coordinatorRepository.save(existingCoordinator);
            return ResponseEntity.ok("Coordinator updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coordinator not found");
        }
    }
   

    @GetMapping("/interns/exclude-current")
    public ResponseEntity<List<User>> getAllInternsExceptCurrent(@RequestHeader("username") String username) {
        List<User> interns = userRepository.findAll();
        List<User> filteredInterns = interns.stream()
                .filter(user -> !user.getUsername().equals(username))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filteredInterns);
    }

    @GetMapping("/interns")
    public ResponseEntity<List<User>> getAllInterns() {
        List<User> interns = userRepository.findAll();
        return ResponseEntity.ok(interns);
    }

    @GetMapping("/current-username")
    public ResponseEntity<Map<String, String>> getCurrentUsername() {
        try {
            // Replace with actual logic to get the current logged-in username
            String currentUsername = getCurrentLoggedInUsername();
            Map<String, String> response = new HashMap<>();
            response.put("username", currentUsername);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve current username");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private String getCurrentLoggedInUsername() {
        // Implement logic to retrieve the current logged-in username
        // This might be from the security context or session
        return "actualCurrentUsername"; // Replace with actual logic
    }

    @GetMapping("/coordinator/{username}")
    public ResponseEntity<Coordinator> getCoordinatorByUsername(@PathVariable String username) {
        Coordinator coordinator = coordinatorRepository.findByUsername(username);
        if (coordinator != null) {
            return ResponseEntity.ok(coordinator);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update-intern")
    public ResponseEntity<String> updateIntern(@RequestBody User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setProject(updatedUser.getProject());
            existingUser.setProjectDescription(updatedUser.getProjectDescription());
            existingUser.setRemainingDays(updatedUser.getRemainingDays());
            existingUser.setOffice(updatedUser.getOffice());
            existingUser.setSchool(updatedUser.getSchool());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            userRepository.save(existingUser);
            return ResponseEntity.ok("Intern updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Intern not found");
        }
    }


    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add-intern")
    public ResponseEntity<String> addIntern(@RequestBody User newUser) {
        System.out.println("Received User: " + newUser);
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Intern added successfully");
    }
    


    @GetMapping("/user/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class LoginResponse {
        private String redirectUrl;

        public LoginResponse(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }
    }
}
