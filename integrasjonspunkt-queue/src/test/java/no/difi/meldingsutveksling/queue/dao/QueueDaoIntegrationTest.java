package no.difi.meldingsutveksling.queue.dao;

import no.difi.meldingsutveksling.queue.config.QueueConfig;
import no.difi.meldingsutveksling.queue.domain.Queue;
import no.difi.meldingsutveksling.queue.domain.Status;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static no.difi.meldingsutveksling.queue.objectmother.QueueObjectMother.assertQueue;
import static no.difi.meldingsutveksling.queue.objectmother.QueueObjectMother.createQueue;
import static no.difi.meldingsutveksling.queue.objectmother.QueueObjectMother.dateHelper;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = QueueConfig.class)
public class QueueDaoIntegrationTest {
    @Autowired
    private QueueDao queueDao;

    @Test
    public void shouldWriteAndReadMetadataToDatabaseWhenNewEntry() {
        Queue expected = createQueue("unique1");

        queueDao.saveEntry(expected);
        Queue actual = queueDao.retrieve(Status.NEW).get(0);

        assertQueue(expected, actual);
    }

    @Test
    public void shouldWriteTwoEntriesWithStatusNewToDatabaseAndRetrieveBothWhenStatusIsNew() {
        Queue expected0 = createQueue("uniqueA1");
        Queue expected1 = createQueue("uniqueA2");

        queueDao.saveEntry(expected0);
        queueDao.saveEntry(expected1);
        List<Queue> retrieve = queueDao.retrieve(Status.NEW);

        assertQueue(expected0, retrieve.get(0));
        assertQueue(expected1, retrieve.get(1));
    }

    @Test
    public void shouldOnlyRetrieveWithStatusFailedWhenRequestedStatusFailed() {
        Queue expected0 = createQueue("uniqueB1", Status.NEW);
        Queue expected1 = createQueue("uniqueB2", Status.FAILED);

        queueDao.saveEntry(expected0);
        queueDao.saveEntry(expected1);
        List<Queue> retrieve = queueDao.retrieve(Status.FAILED);

        assertEquals(1, retrieve.size());
        assertQueue(expected1, retrieve.get(0));
    }

    @Test
    public void shouldUpdateStatus() {
        queueDao.saveEntry(createQueue("uniqueC1", Status.NEW));
        queueDao.updateStatus(createQueue("uniqueC1", Status.FAILED));
        Queue actual = queueDao.retrieve(Status.FAILED).get(0);

        assertEquals(Status.FAILED, actual.getStatus());
    }

    @Test
    public void shouldRetrieveResultBasedOnStatusSortedOnTimestamp() throws Exception {
        Date now = dateHelper(0);
        Date tomorrow = dateHelper(1);
        Date yesterday = dateHelper(-1);
        queueDao.saveEntry(createQueue("uniqueD1", now));
        queueDao.saveEntry(createQueue("uniqueD2", tomorrow));
        queueDao.saveEntry(createQueue("uniqueD3", yesterday));

        List<Queue> actual = queueDao.retrieve(Status.NEW);

        assertEquals(yesterday.getTime(), actual.get(0).getLastAttemptTime().getTime());
        assertEquals(now.getTime(), actual.get(1).getLastAttemptTime().getTime());
        assertEquals(tomorrow.getTime(), actual.get(2).getLastAttemptTime().getTime());
    }

    @Test
    public void shouldRetrieveSingleRowWhenCallingWithUniqueId() {
        queueDao.saveEntry(createQueue("uniqueE1"));
        queueDao.saveEntry(createQueue("uniqueE2"));

        Queue actual = queueDao.retrieve("uniqueE2");

        assertEquals(actual.getUnique(), "uniqueE2");
    }

    @After
    public void tearDown() {
        queueDao.removeAll();
    }
}