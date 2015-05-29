// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("MiniStatement", function (request, response) {

	var soap = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org"> <soapenv:Header/> <soapenv:Body> <tem:MiniStatement_Request><tem:Header><tem:UniqueId>MINIST001</tem:UniqueId><tem:ServiceRequestId>MW.HKN</tem:ServiceRequestId><tem:ServiceRequestVersion>1.0</tem:ServiceRequestVersion> <tem:ChannelId>HKN</tem:ChannelId> </tem:Header> <tem:MiniStatement_Req_Body> <tem:RequestDate>2015-05-18</tem:RequestDate> <tem:Currency>INR</tem:Currency> <tem:AccountInfo> <tem:AccountId>ACT000000000001</tem:AccountId> </tem:AccountInfo> </tem:MiniStatement_Req_Body> </tem:MiniStatement_Request> </soapenv:Body> </soapenv:Envelope>';

	Parse.Cloud.httpRequest({
		method: 'POST',
		url: 'http://hackathon.axisbank.com:8523/CoreMWService',
		headers: {
			'Content-Type': 'text/xml'
		},
		body: soap,
		success: function (httpResponse) {
			var xmlreader = require('cloud/xml-reader.js');
			var temp = httpResponse.text;
			temp = temp.replace(/:/g, '');
			var summary = [];
			xmlreader.read(temp, function (err, res) {
				if (err) return console.log(err);
				var abcd = res.NS1Envelope.NS1Body.NS2MiniStatement_Response.NS2MiniStatement_Rsp_Body;
				console.log(abcd.NS2AccountBalance);
				summary.push(abcd.NS2RequestDate.text());
				summary.push(abcd.NS2Currency.text());
				summary.push(abcd.NS2Status.text());
				var accountBalance = abcd.NS2AccountBalance.array;
				var temp1 = [];
				for (var i = 0; i < accountBalance.length; i++) {
					temp1.push(accountBalance[i].NS2BalanceType.text());
					temp1.push(accountBalance[i].NS2BalanceAmount.NS2Amount.text());
				}
				summary.push(temp1);

				var temp2 = [];
				var trnDetails = abcd.NS2TrnDetail.NS2TransactionDetails.array;
				for (var i = 0; i < trnDetails.length; i++) {
					temp2.push(trnDetails[i].text());
				}
				summary.push(temp2);
			});
			response.success(summary);
		},
		error: function (httpResponse) {
			response.error(httpResponse.message);
		}
	});
});

Parse.Cloud.define("GetBalance", function (request, response) {

	var soap = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org"> <soapenv:Header/> <soapenv:Body> <tem:GetBalance_Request> <tem:Header> <tem:UniqueId>000000008</tem:UniqueId> <tem:ServiceRequestId>MW.HKN</tem:ServiceRequestId> <tem:ServiceRequestVersion>1.0</tem:ServiceRequestVersion> <tem:ChannelId>HKN</tem:ChannelId> </tem:Header> <tem:GetBalance_Req_Body> <tem:RequestDate>2015-05-18</tem:RequestDate> <tem:Currency>INR</tem:Currency> <tem:AccountInfo> <tem:AccountId>ACT000000000001</tem:AccountId> </tem:AccountInfo> </tem:GetBalance_Req_Body> </tem:GetBalance_Request> </soapenv:Body> </soapenv:Envelope>';
	Parse.Cloud.httpRequest({
		method: 'POST',
		url: 'http://hackathon.axisbank.com:8523/CoreMWService',
		headers: {
			'Content-Type': 'text/xml'
		},
		body: soap,
		success: function (httpResponse) {
			var xmlreader = require('cloud/xml-reader.js');
			var temp = httpResponse.text;
			temp = temp.replace(/:/g, '');
			var summary = [];
			xmlreader.read(temp, function (err, res) {
				if (err) return console.log(err);
				var abcd = res.NS1Envelope.NS1Body.NS2GetBalance_Response.NS2GetBalance_Rsp_Body;
				console.log(abcd.NS2AccountBalance);
				summary.push(abcd.NS2RequestDate.text());
				summary.push(abcd.NS2Currency.text());
				summary.push(abcd.NS2Status.text());
				var accountBalance = abcd.NS2AccountBalance.array;
				var temp1 = [];
				for (var i = 0; i < accountBalance.length; i++) {
					temp1.push(accountBalance[i].NS2BalanceType.text());
					temp1.push(accountBalance[i].NS2BalanceAmount.NS2Amount.text());
				}
				summary.push(temp1);
			});
			response.success(summary);
		},
		error: function (httpResponse) {
			response.error(httpResponse.message);
		}
	});
});

Parse.Cloud.define("GetAccountDetails", function (request, response) {

	var soap = '<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:tem="http://tempuri.org"> <soap:Header> <tem:subHeader> <tem:UniqueId>SB0000001</tem:UniqueId> <tem:ServiceRequestId>MW.HKN</tem:ServiceRequestId> <tem:ServiceRequestVersion>1.0</tem:ServiceRequestVersion> <tem:ChannelId>HKN</tem:ChannelId> </tem:subHeader> </soap:Header> <soap:Body> <tem:SMSBankingInfoRequest> <tem:SearchFilterString>MOBILE</tem:SearchFilterString> <tem:MobileNumber>MOB0005</tem:MobileNumber> </tem:SMSBankingInfoRequest> </soap:Body> </soap:Envelope>';
	Parse.Cloud.httpRequest({
		method: 'POST',
		url: 'http://hackathon.axisbank.com:8523/SMSBankingMWService',
		headers: {
			'Content-Type': 'text/xml'
		},
		body: soap,
		success: function (httpResponse) {
			var xmlreader = require('cloud/xml-reader.js');
			var temp = httpResponse.text;
			temp = temp.replace(/:/g, '');
			var summary = [];
			xmlreader.read(temp, function (err, res) {
				if (err) return console.log(err);
				var abcd = res.NS1Envelope.NS1Body.NS3SMSBankingInfoResponse.NS3SMSBankingInfoDetails;
				summary.push(abcd.NS3CustomerID.text());
				summary.push(abcd.NS3AccountNumber.text());
				summary.push(abcd.NS3MobileNumber.text());
				summary.push(abcd.NS3Relation.text());
				summary.push(abcd.NS3ModeOfOperation.text());
				summary.push(abcd.NS3DeletionFlag.text());
			});
			response.success(summary);
		},
		error: function (httpResponse) {
			response.error(httpResponse.message);
		}
	});
});