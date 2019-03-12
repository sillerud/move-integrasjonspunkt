package no.difi.meldingsutveksling.receipt.service;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import no.difi.meldingsutveksling.exceptions.MessageStatusNotFoundException;
import no.difi.meldingsutveksling.exceptions.NotContentException;
import no.difi.meldingsutveksling.receipt.MessageStatus;
import no.difi.meldingsutveksling.receipt.MessageStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequiredArgsConstructor
@RequestMapping("/api/statuses")
public class MessageStatusController {

    private final MessageStatusRepository statusRepo;

    @GetMapping
    @ApiOperation(value = "Get all statuses", notes = "Get a list of all statuses with given parameters")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = MessageStatus[].class)
    })
    public Page<MessageStatus> conversations(
            @QuerydslPredicate(root = MessageStatus.class) Predicate predicate,
            @PageableDefault(sort = "statId", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return statusRepo.findAll(predicate, pageable);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Get status", notes = "Get status with given id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = MessageStatus.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)
    })
    public MessageStatus status(
            @ApiParam(value = "Status id", required = true)
            @PathVariable("id") Integer id) {

        return statusRepo.findByStatId(id)
                .orElseThrow(() -> new MessageStatusNotFoundException(id));
    }

    @GetMapping("peek")
    @ApiOperation(value = "Latest status", notes = "Get status with latest update")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = MessageStatus.class),
            @ApiResponse(code = 204, message = "No Content", response = String.class)
    })
    public MessageStatus statusPeek() {
        return statusRepo.findFirstByOrderByLastUpdateAsc()
                .orElseThrow(NotContentException::new);
    }
}
