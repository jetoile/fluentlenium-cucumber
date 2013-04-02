Feature: multibrowser

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


  Scenario Outline: multi browser navigation:
    Given I connect on url http://localhost:8080 with different browsers:
      |<browser> |<parameters>|<host>|<version>|<os.platform>|
    Given j accede a la homePage
    And je suis sur homePage
    When je submit
    Then je suis sur la page result
  Examples:
    |browser                    |parameters                                                        |host | version | os.platform |
    |firefox                    |                                                                  |none |         | win7        |
#    |firefox                    |                                                                  |http://10.147.2.83 |         | win7        |
    |default                    |                                                                  |none |         | win7        |
    |phantomjs                  |phantomjs.binary.path:/usr/local/phantomjs/bin/phantomjs          |none     |         |             |
#      |chrome                    |                                                                  |none |
