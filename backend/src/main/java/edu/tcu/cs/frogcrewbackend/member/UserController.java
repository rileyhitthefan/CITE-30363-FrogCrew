package edu.tcu.cs.frogcrewbackend.member;

import edu.tcu.cs.frogcrewbackend.member.converter.UserDTOToUserConverter;
import edu.tcu.cs.frogcrewbackend.member.converter.UserToUserDTOConverter;
import edu.tcu.cs.frogcrewbackend.member.dto.UserDTO;
import edu.tcu.cs.frogcrewbackend.system.Result;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/crewMember")
public class UserController {
    private final UserService userService;
    private final UserToUserDTOConverter userToUserDTOConverter;
    private final UserDTOToUserConverter userDTOToUserConverter;

    public UserController(UserService userService, UserToUserDTOConverter userToUserDTOConverter, UserDTOToUserConverter userDTOToUserConverter) {
        this.userService = userService;
        this.userToUserDTOConverter = userToUserDTOConverter;
        this.userDTOToUserConverter = userDTOToUserConverter;
    }

    @PostMapping
    public Result createMember(@RequestBody @Valid UserDTO userDTO) {
        Member newMember = this.userDTOToUserConverter.convert(userDTO);
        Member savedMember = this.userService.createMember(newMember);
        UserDTO savedMemberDTO = this.userToUserDTOConverter.convert(savedMember);
        return new Result(true, StatusCode.SUCCESS, "Member created", savedMemberDTO);
    }
}
