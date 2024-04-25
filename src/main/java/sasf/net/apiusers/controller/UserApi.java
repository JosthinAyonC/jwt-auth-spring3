package sasf.net.apiusers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sasf.net.apiusers.model.AuthRequest;
import sasf.net.apiusers.model.Usuario;
import sasf.net.apiusers.service.InfoUserService;
import sasf.net.apiusers.service.JwtService;

@RestController
@RequestMapping("/auth")
public class UserApi {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private InfoUserService infoUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Usuario> getUsers() {
        return infoUserService.getUsers();
    }

    @GetMapping("user/{name}")
    public Usuario getUser(@PathVariable String name) {
        return infoUserService.getUsers().stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

    @PostMapping
    public Usuario addUser(@RequestBody Usuario user) {
        infoUserService.add(user);
        return user;
    }

    @PutMapping("user/{name}")
    public Usuario updateUser(@PathVariable String name, @RequestBody Usuario user) {
        infoUserService.update(name, user);
        return user;
    }

    @DeleteMapping("user/{name}")
    public void deleteUser(@PathVariable String name) {
        infoUserService.delete(name);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtService.generateToken(authentication);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}
