function init() {
	Parse.initialize("5CacqV3GaCWICl8LV19LXZ2G2JbzPmjlupfDKIte", "F5kE5mcmOy2wKS8qfRcPrRICSKdh7f3tjU8V2Qod");
	console.log("Parse Initialized");

	cloudCall();
}

function cloudCall() {
	Parse.Cloud.run('hello', {}, {
		success: function (result) {
			console.log(result);
			// result is 'Hello world!'
		},
		error: function (error) {
			console.log(error);
		}
	});
}