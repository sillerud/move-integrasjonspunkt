Feature: Message on internal queue is expired
  NextMove messages has a field which indicates how long this message should be allowed to attempt being delivered
  this is referred to as "time to live". Registering status is important because this allows developers to implement
  the status into their own applications which leads to more informative messages for end users. Expired messages should
  be stopped from being sent further.

  Scenario: An outgoing message has been put on internal queue and time to live expires while on queue
    Given a message exists on the internal queue
    And the message is expired
    And the application attempts to send the message
    Then error status will be registered and message stopped