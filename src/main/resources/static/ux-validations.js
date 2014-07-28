Ext.data.validations.rangeMessage = 'is not in range';
Ext.data.validations.range = function(config, value) {

	if (value === undefined || value === null) {
		return false;
	}

	if (!Ext.isNumeric(value)) {
		return false;
	}

	var numericValue = parseFloat(value), min = config.min, max = config.max;

	if ((min && numericValue < min) || (max && numericValue > max)) {
		return false;
	}
	return true;

};

Ext.data.validations.digitsMessage = 'is not a number';
Ext.data.validations.digits = function(config, value) {

	if (value === undefined || value === null) {
		return false;
	}

	if (!Ext.isNumeric(value)) {
		return false;
	}

	var stringValue = String(value), integer = config.integer, fraction = config.fraction;
	var splitted = stringValue.split(".");

	if ((integer && splitted[0].length > integer) || (fraction && fraction[1].length > fraction)) {
		return false;
	}

	return true;
};

Ext.data.validations.futureMessage = 'is not in the future';
Ext.data.validations.future = function(config, value) {
	if (value === undefined || value === null) {
		return false;
	}

	if (!Ext.isDate(value)) {
		return false;
	}

	var today = new Date();
	return today.getTime() > value.getTime();
};

Ext.data.validations.pastMessage = 'is not in the past';
Ext.data.validations.past = function(config, value) {
	if (value === undefined || value === null) {
		return false;
	}

	if (!Ext.isDate(value)) {
		return false;
	}

	var today = new Date();
	return today.getTime() < value.getTime();
};

Ext.data.validations.creditCardNumberCards = {
	'mc': '5[1-5][0-9]{14}',
	'ec': '5[1-5][0-9]{14}',
	'vi': '4(?:[0-9]{12}|[0-9]{15})',
	'ax': '3[47][0-9]{13}',
	'dc': '3(?:0[0-5][0-9]{11}|[68][0-9]{12})',
	'bl': '3(?:0[0-5][0-9]{11}|[68][0-9]{12})',
	'di': '6011[0-9]{12}',
	'jcb': '(?:3[0-9]{15}|(2131|1800)[0-9]{11})',
	'er': '2(?:014|149)[0-9]{11}'
};

Ext.data.validations.creditCardNumberMessage = 'is not a creditcard numbr';
Ext.data.validations.creditCardNumber = function(config, value) {

	var ccvalue = String(value).replace(/[^0-9]/g, '');

	var sum = 0, parity = ccvalue.length % 2;

	for ( var i = 0; i <= (ccvalue.length - 1); i++) {
		var digit = parseInt(ccvalue[i], 10);

		if (i % 2 == parity) {
			digit = digit * 2;
		}
		if (digit > 9) {
			digit = digit - 9;
		}

		sum += digit;
	}

	if ((sum % 10) == 0) {

		for ( var i in Ext.data.validations.creditCardNumberCards) {
			if (ccvalue.match('^' + Ext.data.validations.creditCardNumberCards[i] + '$')) {
				return true;
			}
		}
	}
	return false;

};

Ext.data.validations.notBlankMessage = 'must not be blank';
Ext.data.validations.notBlank = function(config, value) {
	if (value === undefined || value === null) {
		return false;
	}

	return Ext.String.trim(value).length > 0;

};
