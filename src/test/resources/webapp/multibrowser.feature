Feature: multibrowser

  Scenario Outline: multi browser navigation version 1:
    Given I connect on url http://localhost:8080 with different browsers:
      | <browser> | <parameters> |
    Given j accede a la homePage
    And je suis sur homePage
    When je submit
    Then je suis sur la page result
    And drivers are closed
  Examples:
    | browser | parameters                                             | host |
    | firefox |                                                        | none |
    | default |                                                        | none |
    | chrome  | webdriver.chrome.driver:/opt/chromedriver/chromedriver | none |


  Scenario Outline: multi browser navigation version 2:
    Given I connect on url http://localhost:8080 with different browsers:
      | <browser> | <parameters> | <host> | <version> | <os.platform> |
    Given j accede a la homePage
    And je suis sur homePage
    When je submit
    Then je suis sur la page result
    And drivers are closed
  Examples:
    | browser   | parameters                                                            | host | version | os.platform |
    | firefox   |                                                                       | none |         | win7        |
    #    |firefox                    |                                                                  |http://10.147.2.83:4444/wd/hub |         | win7        |
    | default   |                                                                       | none |         | win7        |
    | phantomjs | phantomjs.binary.path:/opt/phantomjs-1.9.0-linux-x86_64/bin/phantomjs | none |         |             |
    | chrome    | webdriver.chrome.driver:/opt/chromedriver/chromedriver                | none |         |             |
