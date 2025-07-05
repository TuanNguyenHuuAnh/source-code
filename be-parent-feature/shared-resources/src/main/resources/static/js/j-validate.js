$(document).ready(function() {
	
	jQuery.validator.addMethod("code", function (value, element, params) {
	    return this.optional( element ) || /^[a-zA-Z0-9-_#]+$/.test( value );
	}, "Field must have no special characters and spaces");
	
	// https://www.whatsmyip.org/html-characters/
	jQuery.validator.addMethod("specialCharacters", function (value, element, params) {
	    return this.optional( element ) || !/[<>†‡‰‹‘’“”™›¡¢£¤¥¦§¨©ª«¬®¯°±²³´µ¶·¸¹º»¼½¾¿ΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩαβγδεζηθικλμξοπ¿ρςστυφχψωϑϒϖ•…′″‾ℑ℘ℜℵ←↑→↓↔↵⇐⇑⇒⇓⇔∀∂∃∅∇∈∉∏∑−∗√∝∞∠∧∨∩∪∫∴∼≅≈≠≡≤≥⊂⊃⊄⊆⊇⊕⊗⊥⋅⌈⌉⌊⌋〈〉◊♠♣♥♦]/.test( value );
	}, "Field must have no special characters");
	
	jQuery.validator.addMethod("currency", function (value, element, params) {
	    return this.optional( element ) || /^\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?\.[0-9]{1,2}$/.test( value );
	}, "Currency must be input with right format");
	
	jQuery.validator.addMethod("username", function (value, element, params) {
	    return this.optional( element ) || /^[a-zA-Z0-9.@\-_]+$/.test( value );
	}, "User name is not valid. Only characters A-Z, a-z, 0-9 , '.', '@', '-' and '_' are  acceptable.");
	
	jQuery.validator.addMethod("phone", function(phone_number, element) {
	    return this.optional(element) ||
	    phone_number.match(/^[0-9.; ()-]*$/);
	}, "Phone is not valid");
	
	jQuery.validator.addMethod("multiemail", function (value, element) {
	    if (this.optional(element)) {
	        return true;
	    }

	    var emails = value.split(','),
	        valid = true;

	    for (var i = 0, limit = emails.length; i < limit; i++) {
	        value = emails[i];
	        valid = valid && jQuery.validator.methods.email.call(this, value, element);
	    }

	    return valid;
	}, "Invalid email format: please use a comma to separate multiple email addresses.");
	
	jQuery.validator.addMethod("integer", function (value, element, params) {
		if (this.optional(element)) {
	        return true;
	    }
		if (isNormalInteger(value))
		{
		   return true;
		}
		
	}, "Input must be integer");
	
	jQuery.validator.addMethod("docName", function (value, element, params) {
		return (null != value && '' != value && ' ' != value);
		
	}, "Field is required");
	
	jQuery.validator.addMethod("url", function (value, element, params) {
	    return this.optional( element ) || /^[a-zA-Z0-9-_/&=?]+$/.test( value );
	}, "Field URL must have no special characters and spaces");
	
	
	jQuery.validator.addClassRules({
			"j-required":{
				required:true
			},
		    "j-remote":{
		    	remote:true
		    },
		    "j-email":{
		    	email:true
		    },
		    "j-url":{
		    	url:true
		    },
		    "j-date":{
		    	date:true
		    },
		    "j-dateISO":{
		    	dateISO:true
		    },
		    "j-number":{
		    	number:true
		    },
		    "j-digits":{
		    	digits:true
		    },
		    "j-creditcard":{
		    	creditcard:true
		    },
		    "j-equalTo":{
		    	equalTo:name
		    },
		    "j-accept":{
		    	accept:true
		    },
		    "j-maxlength":{
		    	maxlength:255
		    },
		    "j-minlength":{
		    	minlength:10
		    },
		    "j-rangelength":{
		    	rangelength:[10,255]
		    },
		    "j-range":{
		    	range:[10,100000]
		    },
		    "j-max":{
		    	max:10000
		    },
		    "j-min":{
		    	min:10
		    },
		    "j-code":{
		    	code:true
		    },
		    "j-specialCharacters":{
		    	specialCharacters:true
		    },
		    "j-currency":{
		    	currency:true
		    },
		    "j-multiemail":{
		    	multiemail:true
		    },
		    "j-int":{
		    	integer:true
		    },
		    "j-username":{
		    	username:true
		    },
		    "j-url":{
		    	url:true
		    },
		    "j-phone":{
		    	phone:true
		    },
		    "j-docName":{
		    	docName:true
		    }
		});
	
	$.validator.setDefaults({
	    ignore: ""
	});
	
	
//	$(".j-currency").maskMoney({thousands:',', decimal:'.', allowNegative: true, allowZero:true,precision: 2, suffix: ' VND'});
});

function isNormalInteger(str) {
    var n = Math.floor(Number(str));
    return n !== Infinity && String(n) === str && n >= 0;
}