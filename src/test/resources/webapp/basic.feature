Feature: basic

  Scenario: simple navigation
    Given j accede a la homePage
    And je suis sur homePage
    When je submit
    Then je suis sur la page result


  Scenario Outline: multi browser navigation:
    Given I connect on url http://localhost:8080 with different browsers:
     |<browser> |
    Given j accede a la homePage
    And je suis sur homePage
    When je submit
    Then je suis sur la page result
    Examples:
      |browser                    |host  |
      |firefox                    |none  |
      |default                    |none  |
#      |chrome                     |none  |

