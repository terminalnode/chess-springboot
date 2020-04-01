package se.newton.sysjg3.chessapi.rest.controllers;


import org.springframework.web.bind.annotation.*;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;
import se.newton.sysjg3.chessapi.service.ChallengeService;
import se.newton.sysjg3.chessapi.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChallengeRestController {

    private ChallengeService challengeService;

    @GetMapping("/challenge/challenger")
    public List<Challenge> getChallengesbyChallenger(@RequestBody Player player) {
        return challengeService.getChallengesByChallenger(player);
    }

    @GetMapping("/challenge/challenged")
    public List<Challenge> getChallengesbyChallenged(@RequestBody Player player) {
        return challengeService.getChallengesByChallenged(player);
    }
}

