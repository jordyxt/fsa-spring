package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.User;
import es.tfm.fsa.domain.services.UserService;
import es.tfm.fsa.infraestructure.api.dtos.TokenDto;
import es.tfm.fsa.infraestructure.api.dtos.UserDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('ADMIN') or hasRole('BASIC')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String BASIC_USERS = "/basic-users";
    public static final String TOKEN = "/token";

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public Optional<TokenDto> login(@AuthenticationPrincipal org.springframework.security.core.userdetails.User activeUser) {
        return userService.login(activeUser.getUsername())
                .map(TokenDto::new);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public Optional<User> createUser(@Valid @RequestBody UserDto creationUserDto) {
        return this.userService.createUser(creationUserDto.toUser(), this.extractRoleClaims());
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = BASIC_USERS)
    public Optional<User> registerUser(@Valid @RequestBody UserDto creationUserDto) {
        creationUserDto.doDefault();
        if (creationUserDto.getRole().equals(Role.BASIC)) {
            return this.userService.createUser(creationUserDto.toUser(), Role.BASIC);
        }
        return Optional.empty();
    }

    private Role extractRoleClaims() {
        List<String> roleClaims = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        System.out.println(roleClaims);
        return Role.of(roleClaims.get(0));  // it must only be a role
    }
}
