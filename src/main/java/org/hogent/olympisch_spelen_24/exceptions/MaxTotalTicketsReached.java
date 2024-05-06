package org.hogent.olympisch_spelen_24.exceptions;

import jakarta.validation.constraints.Max;

public class MaxTotalTicketsReached extends RuntimeException {
    public MaxTotalTicketsReached() {
        super("Maximum buyable tickets for all competition has been reached");
    }
}
