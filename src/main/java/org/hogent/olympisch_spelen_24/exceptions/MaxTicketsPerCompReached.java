package org.hogent.olympisch_spelen_24.exceptions;

public class MaxTicketsPerCompReached extends RuntimeException {
    public MaxTicketsPerCompReached() {
        super("Maximum buyable tickets for this competition has been reached");
    }
}
