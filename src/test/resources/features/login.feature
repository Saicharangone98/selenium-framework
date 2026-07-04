Feature: Login functionality for SauceDemo

  Scenario: Valid login with correct credentials
    Given User is on the login page
    When User enters username "standard_user" and password "secret_sauce"
    Then User should be redirected to the inventory page