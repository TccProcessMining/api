package preprocessingmining.com.example.preprocessingmining.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import preprocessingmining.com.example.preprocessingmining.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private UserService userService;

    public AuthenticationFilter(AuthenticationManager authenticationManager,UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            var user = new ObjectMapper()
                    .readValue(request.getInputStream(),
                            preprocessingmining.com.example.preprocessingmining.model.User.class);
            final var authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getMail(), user.getPassword(), new ArrayList<>()));

            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException("Could not read request" + e);
        }
    }

    public Authentication attemptAuthentication(preprocessingmining.com.example.preprocessingmining.model.User user) throws AuthenticationException {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getMail(), user.getPassword(), new ArrayList<>()));
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException {
        String token = Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS512,
                        "711D95FD5D971A3E408D71F944AA0059CC5F6CA2018111FCDDD9E90C528160B16FEEFB237E0A171C5CD8CAC25C095A2FD740CD37794D1807158EAD11C23D27F9"
                                .getBytes())
                .compact();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", "Bearer" + token);
        var userByMail = userService.findUserByMail(((User) authentication.getPrincipal()).getUsername());
        jsonObject.put("id", userByMail.getId());
        response.getWriter().write(jsonObject.toString());
    }
}