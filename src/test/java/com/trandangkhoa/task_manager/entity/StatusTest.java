package com.trandangkhoa.task_manager.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusTest {

    @Test
    void next_cyclesThroughAllStatuses() {
        assertEquals(Status.DOING, Status.TODO.next());
        assertEquals(Status.DONE, Status.DOING.next());
        assertEquals(Status.TODO, Status.DONE.next());
    }
}