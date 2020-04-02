package se.newton.sysjg3.chessapi.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.service.ChallengeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChallengeRestController {
  private ChallengeService challengeService;

  //----- Constructor ------//
  @Autowired
  public ChallengeRestController (ChallengeService challengeService) {
    this.challengeService = challengeService;
  }

  //----- GET Mappings -----//
  @GetMapping("/challenges/challenger")
  public List<Challenge> getChallengesByChallenger(@RequestHeader(value="Token") String tokenString) {
    return challengeService.getChallengesByChallenger(tokenString);
  }

  @GetMapping("/challenges/challenged")
  public List<Challenge> getChallengesByChallenged(@RequestHeader(value="Token") String tokenString) {
    return challengeService.getChallengesByChallenged(tokenString);
  }

  //----- POST Mappings -----//
  @PostMapping("/challenges")
  public Challenge createNewChallenge(@RequestHeader(value="Token") String tokenString,
                                      @RequestBody Player challenged) {
    return challengeService.create(challenged, tokenString);
  }

  @PostMapping("/challenges/{challengeId}")
  public Game answerChallenge(@PathVariable("challengeId") int challengeId,
                              @RequestHeader(value ="Token") String tokenString) {
    return challengeService.acceptChallenge(challengeId, tokenString);
  }

  //----- DELETE Mappings -----//
  @DeleteMapping("/challenges/{challengeId}")
  public String declineChallenge(@PathVariable("challengeId") int challengeId,
                                 @RequestHeader(value ="Token") String tokenString) {
    return challengeService.declineChallenge(challengeId, tokenString);
  }
}
