package sasf.net.apiusers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sasf.net.apiusers.model.Usuario;

@Service
public class InfoUserService implements UserDetailsService {

    private List<Usuario> users = new ArrayList<>();

    public InfoUserService() {
        users.add(new Usuario("user1", "username1", "$2a$10$rUC3IF/clVOX6NXcjqz08eLbqYpKa5fteC4NokTlPNytAz7wqRtwW", "email1", "phone1", "ROLE_ADMIN"));
        users.add(new Usuario("user2", "username2", "$2a$10$rUC3IF/clVOX6NXcjqz08eLbqYpKa5fteC4NokTlPNytAz7wqRtwW", "email2", "phone2", "ROLE_USER"));
        users.add(new Usuario("user3", "username3", "$2a$10$rUC3IF/clVOX6NXcjqz08eLbqYpKa5fteC4NokTlPNytAz7wqRtwW", "email3", "phone3", "ROLE_USER"));
    }

    public List<Usuario> getUsers() {
        return users;
    }

    public void add(Usuario user) {
        users.add(user);
    }

    public void update(String name, Usuario user) {
        for (int i = 0; i < users.size(); i++) {
            Usuario u = users.get(i);
            if (u.getName().equals(name)) {
                users.set(i, user);
                return;
            }
        }
    }

    public void delete(String name) {
        users.removeIf(u -> u.getName().equals(name));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = users.stream().filter(u -> u.getName().equals(username))
                .findFirst();
        return user.map(UserInfoDetails::new).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
