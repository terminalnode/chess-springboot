package se.newton.sysjg3.chessapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;
import se.newton.sysjg3.chessapi.rest.exceptions.LoginFailedException;
import se.newton.sysjg3.chessapi.service.LoginService;
import se.newton.sysjg3.chessapi.service.PlayerService;

@RestController
@RequestMapping("/api")
public class LoginRestController {

    private LoginService loginService;

    @Autowired
    public LoginRestController(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping("/login")
    public Token loginPlayer(@RequestBody Player player) {

        Token token = loginService.loginPlayer(player);
        if (token == null) {
            throw new LoginFailedException("The passwords do not match!");
        }

        return token;
    }



}
