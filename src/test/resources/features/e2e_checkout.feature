Feature: E2E checkout flow

  Background:
    Given User is on the login page
    When User enters username "standard_user" and password "secret_sauce"
    @E2E
    Scenario: Complete full checkout journey
      When User should be redirected to the inventory page
      And User should see the products header "Products"
      And User adds Sauce Labs Backpack to the cart
      When User navigates to the shopping cart
      Then The cart should contain item "Sauce Labs Backpack"
      And User completes checkout with details "John", "Doe", "500001"
      Then The order confirmation header should be "Thank you for your order!"