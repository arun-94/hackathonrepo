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

Parse.Cloud.define("ProcessChat", function (request, response) {


	var lemma = require('cloud/lemmatizer.js');
	var _ = require('underscore');

	var input = request.params.inputString;
	var lemmatizer = new lemma.Lemmatizer();

	var words = input;
	words = words.split(' ');
	var toLowerCase = function (w) {
		return w.toLowerCase();
	};

	words = _.map(words, toLowerCase);
	var stopWords = ["a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "arent", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "cant", "cannot", "could", "couldnt", "did", "didnt", "do", "does", "doesnt", "doing", "dont", "down", "during", "each", "few", "for", "from", "further", "had", "hadnt", "has", "hasnt", "have", "havent", "having", "he", "hed", "hell", "hes", "her", "here", "heres", "hers", "herself", "him", "himself", "his", "how", "hows", "i", "id", "ill", "im", "ive", "if", "in", "into", "is", "isnt", "it", "its", "its", "itself", "lets", "me", "more", "most", "mustnt", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "shant", "she", "shed", "shell", "shes", "should", "shouldnt", "so", "some", "such", "than", "that", "thats", "the", "their", "theirs", "them", "themselves", "then", "there", "theres", "these", "they", "theyd", "theyll", "theyre", "theyve", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasnt", "we", "wed", "well", "were", "weve", "were", "werent", "what", "whats", "when", "whens", "where", "wheres", "which", "while", "who", "whos", "whom", "why", "whys", "with", "wont", "would", "wouldnt", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves"]
	words = _.filter(words, function (w) {
		return w.match(/^\w+$/) && !_.contains(stopWords, w);
	});

	words = _.uniq(words);
	console.log(words);
	var result = [];
	lemmatizer.initialize().then(
		function (success) {
			for (var i = 0; i < words.length; i++) {
				words[i] = lemmatizer.lemmas(words[i], 'verb')[0][0];
				if (words[i] == "balance") {
					result.push(1);
				} else if (words[i] == "recharge") {
					result.push(2);
				}
			}
			result.push(words);
			response.success(result);
		}, function (error) {
			result.push(0);
			response.error(result);
		}
	);

});