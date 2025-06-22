Feature: Demo web shop functionality check
  @tag
  Scenario Outline: Verify login functionality
    Given User can login "<FileName>" "<Scenario01>"
    When User clicked on books in top menu
    And Added items to the cart
    And User can estimate the shipping cost
    And User can get into the checkout page
    Then User can successfully placed the order and logged out

    Examples:
      | FileName            | Scenario01 |
      | demowebshoptestdata | TC001      |

  Scenario Outline: Customer info correct or not
    Given User can login "<FileName>" "<Scenario01>"
    When User navigated to My Account
    Then Validate the Details

    Examples:
      | FileName            | Scenario01 |
      | demowebshoptestdata | TC001      |

