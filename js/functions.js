function init() {
	Parse.initialize("5CacqV3GaCWICl8LV19LXZ2G2JbzPmjlupfDKIte", "F5kE5mcmOy2wKS8qfRcPrRICSKdh7f3tjU8V2Qod");
	console.log("Parse Initialized");

	VerifyUserLogin();
}

function MiniStatement() {
	Parse.Cloud.run('MiniStatement', {
		requestDate: '2015-05-18',
		accountId: 'ACT000000000001'
	}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}

function GetBalance() {
	Parse.Cloud.run('GetBalance', {
		requestDate: '2015-05-18',
		accountId: 'ACT000000000001'
	}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}

function GetAccountDetails() {
	Parse.Cloud.run('GetAccountDetails', {
		mobile: "MOB0005"
	}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}

function ProcessChat() {
	Parse.Cloud.run('ProcessChat', {
		inputString: "please recharge 100 200"
	}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}

function VerifyUserLogin() {
	Parse.Cloud.run('VerifyUserLogin', {
		loginId: "LOGINID0001",
		passCode: "PWD0001"
	}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}

function WithinBankTransfer() {
	Parse.Cloud.run('WithinBankTransfer', {
		requestDate: "2015-05-18",
		amount: "999",
		account1: "ACT000000000001",
		remarks1: "Debit Narration",
		account2: "ACT000000000002",
		remarks2: "Credit Narration"
	}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}

function MultiAccntBal() {
	Parse.Cloud.run('MultiAccntBal', {
		requestDate: '2015-05-18',
		accountId: ['ACT000000000001', 'ACT000000000002']
	}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}