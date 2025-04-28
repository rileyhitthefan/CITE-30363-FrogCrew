package edu.tcu.cs.frogcrewbackend.member;

import edu.tcu.cs.frogcrewbackend.member.dto.InviteDTO;
import edu.tcu.cs.frogcrewbackend.system.Result;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.endpoint.base-url}/invite")
public class InviteController {
    private final InviteService inviteService;

    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping
    public Result inviteMember(@RequestBody @Valid InviteDTO invite) {
        this.inviteService.sendInvite(invite.emails());
        return new Result(true, StatusCode.SUCCESS, "Invite success", invite);
    }

    @GetMapping("/{token}")
    public Result validateInviteToken(@PathVariable String token) {
        boolean isValid = this.inviteService.validateInviteToken(token);

        if (isValid) {
            Map<String, String> tokenData = new HashMap<>();
            tokenData.put("token", token);
            return new Result(true, StatusCode.SUCCESS, "Invitation valid", tokenData);
        } else {
            return new Result(false, StatusCode.NOT_FOUND, "Invitation not valid", null);
        }
    }
}
