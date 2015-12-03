package no.difi.meldingsutveksling.queue.dao;

import no.difi.meldingsutveksling.queue.config.QueueConfig;
import no.difi.meldingsutveksling.queue.domain.QueueElement;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = QueueConfig.class)
public class QueueDaoIntegrationTest {
    @Autowired
    private QueueDao queueDao;

    @Test
    public void shouldWriteAndReadMetadataToDatabaseWhenNewEntry() {
        QueueElement expected = createQueue("unique1");

        queueDao.saveEntry(expected);
        QueueElement actual = queueDao.retrieve(Status.NEW).get(0);

        assertQueue(expected, actual);
    }

    @Test
    public void shouldWriteTwoEntriesWithStatusNewToDatabaseAndRetrieveBothWhenStatusIsNew() {
        QueueElement expected0 = createQueue("uniqueA1");
        QueueElement expected1 = createQueue("uniqueA2");

        queueDao.saveEntry(expected0);
        queueDao.saveEntry(expected1);
        List<QueueElement> retrieve = queueDao.retrieve(Status.NEW);

        assertEquals(2, retrieve.size());
    }

    @Test
    public void shouldOnlyRetrieveElementsWithStatusRetryWhenRequestedStatusRetry() {
        QueueElement expected1 = createQueue("uniqueB2", Status.RETRY);

        queueDao.saveEntry(createQueue("uniqueB1", Status.NEW));
        queueDao.saveEntry(expected1);
        List<QueueElement> retrieve = queueDao.retrieve(Status.RETRY);

        assertEquals(1, retrieve.size());
        assertEquals(Status.RETRY, retrieve.get(0).getStatus());
    }

    @Test
    public void shouldUpdateStatus() {
        queueDao.saveEntry(createQueue("uniqueC1", Status.NEW));
        queueDao.updateStatus(createQueue("uniqueC1", Status.RETRY));
        QueueElement actual = queueDao.retrieve(Status.RETRY).get(0);

        assertEquals(Status.RETRY, actual.getStatus());
    }

    @Test
    public void shouldRetrieveResultBasedOnStatusSortedOnDate() throws Exception {
        Date date1 = QueueDao.addMinutesToDate(new Date(), -10);
        Date date2 = QueueDao.addMinutesToDate(new Date(), -20);
        Date date3 = QueueDao.addMinutesToDate(new Date(), -30);
        queueDao.saveEntry(createQueue("uniqueD1", date1));
        queueDao.saveEntry(createQueue("uniqueD2", date2));
        queueDao.saveEntry(createQueue("uniqueD3", date3));

        List<QueueElement> actual = queueDao.retrieve(Status.NEW);

        assertEquals(date3.getTime(), actual.get(0).getLastAttemptTime().getTime());
        assertEquals(date2.getTime(), actual.get(1).getLastAttemptTime().getTime());
        assertEquals(date1.getTime(), actual.get(2).getLastAttemptTime().getTime());
    }

    @Test
    public void shouldRetrieveSingleRowWhenCallingWithUniqueId() {
        queueDao.saveEntry(createQueue("uniqueE1"));
        queueDao.saveEntry(createQueue("uniqueE2"));

        QueueElement actual = queueDao.retrieve("uniqueE2");

        assertEquals(actual.getUniqueId(), "uniqueE2");
    }

    @Test
    public void shouldRemoveElementFromListWhenItIsNotTimeToRun() throws Exception {
        String uniqueThatStick = "uniqueF2";
        Date filterOut1 = QueueDao.addMinutesToDate(new Date(), 0);
        Date stick = QueueDao.addMinutesToDate(new Date(), -10);
        Date filterOut2 = QueueDao.addMinutesToDate(new Date(), -2);
        queueDao.saveEntry(createQueue("uniqueF1", filterOut1));
        queueDao.saveEntry(createQueue(uniqueThatStick, stick));
        queueDao.saveEntry(createQueue("uniqueF3", filterOut2));

        List<QueueElement> actual = queueDao.retrieve(Status.NEW);

        assertEquals(1, actual.size());
        assertTrue(stick.getTime() >= actual.get(0).getLastAttemptTime().getTime());
        assertEquals(uniqueThatStick, actual.get(0).getUniqueId());
    }

    @After
    public void tearDown() {
        queueDao.removeAll();
    }
}