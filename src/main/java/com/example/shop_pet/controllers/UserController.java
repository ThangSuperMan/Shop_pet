package com.example.shop_pet.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; import org.springframework.web.servlet.view.RedirectView;

import com.example.shop_pet.dto.AuthRequest;
import com.example.shop_pet.models.User;
import com.example.shop_pet.services.User.UserService;
import com.example.shop_pet.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {
  Logger logger = LoggerFactory.getLogger(UserController.class);
  private final int BEGIN_INDEX = 7;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtils jwtUtils;

  @GetMapping("/admin")
  @PreAuthorize("hasAuthority('ADMIN')")
  public String forAdmin() {
    logger.info("UserController forAdmin is running...");
    return "This is secret admin resouces data";
  }

  @GetMapping("/user")
  @PreAuthorize("hasAuthority('USER')")
  public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
    logger.info("UserController getUserProfile method is running...");
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header not found");
    }
    String token = authHeader.substring(BEGIN_INDEX);
    System.out.println("token :>> " + token);
    String username = jwtUtils.extractUsername(token);
    Optional<User> user = userService.selectUserByUsername(username);
    System.out.println("User :>> " + user.toString());
    if (user.isPresent()) {
      Map<String, Object> map = new HashMap<>();
      User selectedUser = user.get();
      // Ignore password
      selectedUser.setPassword(null);
      map.put("user", selectedUser);
      // map.put("user", user.orElse(null));
      logger.info("UserController getUserProfile method is running...");
      return ResponseEntity.ok(map);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    // userService.selectUserByUsername()
    // logger.info("UserController forUser method is running...");
    // return new ResponseEntity<>(map, HttpStatus.OK);
    // return "This is secret user resouces data";
  }

  // @GetMapping("/user")
  // @PreAuthorize("hasAuthority('USER')")
  // public String renderUserResoucePage() {
  // return "This is secret user resouces data";
  // }

  @GetMapping("/users")
  public ResponseEntity<?> getUserByUsername(@RequestParam("username") Optional<String> username) {
    System.out.println("UserController getUserByUsername method is running...");
    HashMap<String, Object> map = new HashMap<>();
    if (username.isPresent()) {
      Optional<User> user = userService.selectUserByUsername(username.get());
      if (!user.isPresent()) {
        map.put("isUsernameAvailable", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
      }
      map.put("isUsernameAvailable", false);
      return new ResponseEntity<>(map, HttpStatus.OK);
    }

    map.put("isInvalidUrlParams", true);
    return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // // USER
  // @GetMapping("/login")
  // public String userLoginPage() {
  // return "<strong>login page</strong>";
  // }

  // ADMIN
  @GetMapping("/admin/login")
  public String adminLoginPage() {
    logger.info("adminLoginpage hehe");
    return "<strong>login page</strong>";
  }

  public Boolean isSamePassword(String password, String confirmPassword) {
    return password.equals(confirmPassword);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody @Valid User user) {
    logger.info("AuthController Signup method is running...");

    // Trim all white spaces
    user.setUsername(user.getUsername().trim());
    user.setPassword(user.getPassword().trim());
    user.setConfirmPassword(user.getConfirmPassword().trim());

    HashMap<String, Object> map = new HashMap<String, Object>();
    if (userService.isUsernameExist(user.getUsername())) {
      String message = "Username: " + user.getUsername() + " exists, please choose another one!";
      map.put("errorMessage", message);
      return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (!isSamePassword(user.getPassword(), user.getConfirmPassword())) {
      String message = "Password and confirm password does not match, make sure you typed it correctly!";
      map.put("errorMessage", message);
      return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    String passwordEncoded = passwordEncoder.encode(user.getPassword());
    user.setPassword(passwordEncoded);

    logger.info("Password Encoder :>> " + passwordEncoder.encode(user.getPassword()));

    int result = userService.insertUser(user);
    if (result > 0) {
      logger.info("Insert account successfully");
      String message = "Signup successfully, now you can login, enjoy :)";
      map.put("message", message);
      map.put("signUpSuccessfully", true);
      return new ResponseEntity<>(map, HttpStatus.OK);
    }

    String message = "Username: " + user.getUsername() + " exists, please choose another one!";
    map.put("errorMessage", message);
    return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public boolean isCorrectPassword( String password, String passwordEncoded) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.matches(password, passwordEncoded);
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) {
    logger.info("/api/v1/signin just triggered!");
    HashMap<String, Object> map = new HashMap<>();
    Optional<User> user = userService.selectUserByUsername(authRequest.getUsername());
    if (user.isEmpty()) {
      logger.info("User is null!");
      throw new UsernameNotFoundException("Not found this username please retry!");
    }

    Optional<User> userAuth = userService.selectUserByUsername(authRequest.getUsername()); 
    if (userAuth.isPresent()) {
      User userSelected = userAuth.get();
      if (!isCorrectPassword(authRequest.getPassword(), userSelected.getPassword())) {
        map.put("errorMessage", "Your password was incorrect, please retry!");    
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
      } 
    }
    
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        authRequest.getUsername(), authRequest.getPassword()));
    logger.info("here");
    logger.info("authentication.isAuthenticated :>> " + authentication.isAuthenticated());
    if (authentication.isAuthenticated()) {
      logger.info("authories :>> " + authentication.getAuthorities());
      String username = authRequest.getUsername();
      Optional<User> user2 = userService.selectUserByUsername(username);
      map.put("jwtToken", jwtUtils.generateToken(authRequest.getUsername()));
      map.put("user", user2);
      return new ResponseEntity<>(map, HttpStatus.OK);
    } else {
      throw new UsernameNotFoundException("invalid user request!");
    }
  }

  @GetMapping("/**")
  public RedirectView notFound() {
    // Authenticated -> Go to the dashboard (if admin) or if user go to the homepage
    // No authenticated -> Have to login first (redirect to login page)
    return new RedirectView("/api/v1/admin/login");
  }
}
