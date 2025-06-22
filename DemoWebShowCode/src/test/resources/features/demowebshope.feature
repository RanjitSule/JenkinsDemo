@tag
Feature: Shopping Cart Functionality
    @tag1,@test1
  Scenario Outline: Validate user can add Desktop to the cart
    Given User can register and login "<FileName>" "<Scenario01>"
    When User navigates to Computers and Desktop
    And Add Desktop to card
    Then Validate added message
    Examples:
      | FileName            | Scenario01 |
      | demowebshoptestdata | TC001      |

  @tag2,@test2
  Scenario Outline: validate user can add Notebooks to the cart
    Given user is login and go on product page "<FileName>" "<Sceanario02>"
    When user navigate to Computer and notebook
    And Add Notebook to card
    Then Validate added message Notebooks
    Examples:
      | FileName            | Sceanario02 |
      | demowebshoptestdata | TC001       |

  @tag3,@test2
   Scenario Outline: validate user can add Accessories to the cart
    Given user is login and go on product page "<FileName>" "<Sceanario02>"
    When user navigate to Computer and Accessories
    And Add Accessories to card
    Then Validate added message for Accessories
    Examples:
      | FileName            | Sceanario02 |
      | demowebshoptestdata | TC001       |
