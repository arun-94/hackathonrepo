// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function (request, response) {

	var Buffer = require('buffer').Buffer,
		buffer = new Buffer(
			'<?xml version="1.0" encoding="utf-8"?>' +
			'<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">' +
			'   <soap12:Body>' +
			'       <get_server_time xmlns="http://tempuri.org/" />' +
			'   </soap12:Body>' +
			'</soap12:Envelope>'
		);

	Parse.Cloud.httpRequest({
		method: 'POST',
		url: 'https://services.rs.ge/WayBillService/WayBillService.asmx',
		headers: {
			'Content-Type': 'text/xml; charset=utf-8'
		},
		body: buffer,
		success: function (httpResponse) {
			response.success(httpResponse.text);
		},
		error: function (httpResponse) {
			response.error('Request failed with response code ' + httpResponse.status);
		}
	});
});