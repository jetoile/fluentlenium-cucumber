Feature: harstorage

  Scenario Outline: har browser navigation version 1:
    Given I connect on url http://127.0.0.1:8080 with different browsers and I register the HarStorage Server on 10.147.2.32:5000 with name test:
      | <browser> | <parameters> | <host> | <version> | <platform> |
    Given j accede a la homePage
    And je suis sur homePage
    When je submit
    Then je suis sur la page result
    Then I send har files to the HarStorage Server
    Then drivers are closed
  Examples:
    | browser | parameters | host | version | platform |
    | firefox |            | none |         |          |
#    | firefox |            | http://10.147.2.83:4444/wd/hub |         | win7     |
