Meta:
@author Szanto Peter

Narrative:
As a new user
In order to log in
I want to register 

Scenario: Basic registration

Given I am in <country>

When I register a teen with age <age>
Then I get an error message You must be a teen

When I register an adult with age <age>
Then I get a valid ID

Examples:
|country|age|
|UK|19|
|FR|20|