Feature: Validating Place API's

@AddPlace @Regression
Scenario Outline: Verify is place is being successfully added ussing AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "POST" http request
	Then the API call should be successfull with response "200"
	And the "status" in the response body should be "OK"
	And the "scope" in the response body should be "APP"
	And verify place_Id created maps to "<name>" using "GetPlaceAPI"
	
Examples:
	| name             | language      | address                     |
	| Frontline house, | French-IN     | 29, side layout, cohen 09,  |
	| Front one house, | Polish-IN     | 27, front layout, cohen 10, |
	| Front one house, | Polish-IN     | 27, front layout, cohen 10, |
	| Front building,  | Portuguese-IN | 88, left layout, cohen 100, |
	
@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working
	Given DeletePlace payload
	When user calls "DeletePlaceAPI" with "DELETE" http request
	Then the API call should be successfull with response "200"
	And the "status" in the response body should be "OK"