Feature: feature to test login functionality of vtiger

  Scenario: Check login is successful with valid credentials
    Given user is on login page
    When user enters username and password click on enter
    Then user is navigated to the home page
