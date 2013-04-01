Feature: basic

  Scenario: simple navigation
    Given j accede a la homePage
    And je suis sur homePage
    When je submit
    Then je suis sur la page result
