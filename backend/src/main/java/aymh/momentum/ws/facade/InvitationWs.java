package aymh.momentum.ws.facade;

import aymh.momentum.security.common.dto.Response;
import aymh.momentum.service.user.facade.InvitationService;
import aymh.momentum.ws.converter.InvitationConverter;
import aymh.momentum.ws.dto.InvitationDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invitations")
public class InvitationWs {

    private final InvitationService service;
    private final InvitationConverter converter;

    @PostMapping("/sent")
    public ResponseEntity<Response<InvitationDto>> sendInvitation(
            @RequestBody InvitationDto request
    ) throws MessagingException {
        try {
            InvitationDto invitation = converter.toDto(service.sendInvitation(converter.toBean(request)));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("Invitation sent Successfully",true,invitation)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<Response<Void>> acceptInvitation(
            @RequestBody InvitationDto request
    ) {
        try {
            service.acceptInvitation(converter.toBean(request));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("Invitation accepted Successfully",true,null)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }

    @PostMapping("/decline")
    public ResponseEntity<Response<Void>> declineInvitation(
            @RequestBody InvitationDto request
    ) {
        try {
            service.declineInvitation(converter.toBean(request));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("Invitation declined Successfully",true,null)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }
}
