Feature: Login functionality for SauceDemo

  Scenario Outline: Valid login with different credentials
    Given User is on the login page
    When User enters username "<UserName>" and password "<Password>"
    Then "<ExpectedOutCome>"

    @smoke @regression
    Examples: valid credentials
      | UserName      | Password     | ExpectedOutCome                                                           |
      | standard_user | secret_sauce | redirect                                                                  |

    @regression
    Examples: invalid credentials
      | UserName      | Password     | ExpectedOutCome                                                           |
      | Saicharan     | User@12345   | Epic sadface: Username and password do not match any user in this service |
      |               |              | Epic sadface: Username is required                                        |
      | Charan001     |              | Epic sadface: Password is required                                        |

  @test_failure
  Scenario: Intentional failure to test screenshot logging
    Given User is on the login page
    When User enters username "standard_user" and password "wrong_password"
    Then User should see the products header "Products"