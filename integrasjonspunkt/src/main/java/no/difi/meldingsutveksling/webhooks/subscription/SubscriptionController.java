package no.difi.meldingsutveksling.webhooks.subscription;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import no.difi.meldingsutveksling.domain.webhooks.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Api(tags = "webhooks")
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
@Validated
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    @ApiOperation(value = "List all webhook subscriptions", notes = "List all webhook subscriptions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Subscription[].class),
    })
    public Page<Subscription> listSubscriptions(@PageableDefault Pageable pageable) {
        return subscriptionService.listSubscriptions(pageable);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Get a webhook subscription", notes = "Get a webhook subscription")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Subscription.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)
    })
    public Subscription getSubscription(@ApiParam(value = "id", required = true)
                                        @PathVariable
                                        @NotNull Long id) {
        return subscriptionService.getSubscription(id);
    }

    @PostMapping
    @ApiOperation(value = "Create a webhook subscription", notes = "Create a webhook subscription")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = Subscription.class),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)
    })
    public Subscription createSubscription(
            @ApiParam(name = "Subscription", value = "Subscription", required = true)
            @Valid @RequestBody Subscription subscription) {
        return subscriptionService.createSubscription(subscription);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete a webhook subscription", notes = "Delete a webhook subscription")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found", response = String.class)
    })
    public void deleteSubscription(@ApiParam(value = "id", required = true)
                                   @PathVariable
                                   @NotNull Long id) {

        subscriptionService.deleteSubscription(id);
    }
}