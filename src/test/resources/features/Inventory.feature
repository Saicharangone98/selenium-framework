Feature: Inventory & Cart Operations

  Background:
    Given User is on the login page
    When User enters username "standard_user" and password "secret_sauce"

  @smoke @regression @Inventory
  Scenario: Add product to cart successfully
    Then User should see the products header "Products"
    When User adds Sauce Labs Backpack to the cart
    Then The cart badge count should be "1"