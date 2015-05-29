function init() {
	Parse.initialize("5CacqV3GaCWICl8LV19LXZ2G2JbzPmjlupfDKIte", "F5kE5mcmOy2wKS8qfRcPrRICSKdh7f3tjU8V2Qod");
	console.log("Parse Initialized");

	GetAccountDetails();
}

function MiniStatement() {
	Parse.Cloud.run('MiniStatement', {}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}

function GetBalance() {
	Parse.Cloud.run('GetBalance', {}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}

function GetAccountDetails() {
	Parse.Cloud.run('GetAccountDetails', {}, {
		success: function (result) {
			console.log(result);
		},
		error: function (error) {
			console.log(error);
		}
	});
}