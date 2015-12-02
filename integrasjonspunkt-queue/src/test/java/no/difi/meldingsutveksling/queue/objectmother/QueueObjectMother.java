package no.difi.meldingsutveksling.queue.objectmother;

import no.difi.meldingsutveksling.queue.dao.QueueDao;
import no.difi.meldingsutveksling.queue.domain.Queue;
import no.difi.meldingsutveksling.queue.domain.Status;
import no.difi.meldingsutveksling.queue.rule.RuleDefault;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class QueueObjectMother {
    public static Queue createQueue(String uniqueId, Date lastAttemptTime) {
        return createQueueBuilder().uniqueId(uniqueId).lastAttemptTime(lastAttemptTime).build();
    }

    public static Queue createQueue(String uniqueId, Status status) {
        return createQueueBuilder().uniqueId(uniqueId).status(status).build();
    }

    public static Queue createQueue(String uniqueId, int numberOfErrors) {
        return createQueueBuilder().uniqueId(uniqueId).numberAttempt(numberOfErrors).build();
    }

    public static Queue createQueue(String uniqueId) {
        return createQueueBuilder().uniqueId(uniqueId).build();
    }

    public static Queue createQueue(String uniqueId, String filename) {
        return createQueueBuilder().uniqueId(uniqueId).location(filename).build();
    }

    public static Queue createQueue(String uniqueId, int numberAttempts, String filename) {
        return createQueueBuilder().uniqueId(uniqueId).numberAttempt(numberAttempts).location(filename).build();
    }

    public static Queue createQueue(String uniqueId, Status status, Date lastAttempt) {
        return createQueueBuilder().uniqueId(uniqueId).status(status).lastAttemptTime(lastAttempt).build();
    }

    public static void assertQueue(Queue expected, Queue result) {
        assertEquals(result.getRuleName(), expected.getRuleName());
        assertEquals(result.getChecksum(), expected.getChecksum());
        assertEquals(result.getUniqueId(), expected.getUniqueId());
        assertEquals(result.getLastAttemptTime().getTime(), expected.getLastAttemptTime().getTime());
        assertEquals(result.getNumberAttempts(), expected.getNumberAttempts());
        assertEquals(result.getStatus(), expected.getStatus());
        assertEquals(result.getFileLocation(), expected.getFileLocation());
    }

    private static Queue.Builder createQueueBuilder() {
        return new Queue.Builder()
                .uniqueId("unique1")
                .numberAttempt(2)
                .rule(RuleDefault.getRule())
                .checksum("checksum")
                .lastAttemptTime(QueueDao.addMinutesToDate(new Date(), -10))
                .location("file.123.xml")
                .status(Status.NEW);
    }
}