/*
@RestController
@RequestMapping("/api/login")
public class LoginAPIController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "", produces = "application/json")
    ResponseEntity<User> checkLogin(@RequestBody User u){
        try {
            User user = userRepository.findByEmail(u.getEmail());
            user.setPassword("");
            return ResponseEntity.ok().body(user);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}*/
