
var AppUtil = {
	
	toTimestamp : function(input,format) {
		if(input == null) return null;
			
		if(typeof input === 'string') {
			if(input.trim().length == 0) return null;
			return moment(input,format).toDate().getTime();
		}else if(typeof input === 'number') {
			return input;
		}else {
			throw new "cannot convert input:"+input+" to timestamp";
		}
	},
	
	formatDate : function(value,format) {
		if(value) {
			return moment(value).format(format);
		}
	},

	wsCallback : function(success,error) {
		var fun = function(response) {
			if(AppUtil.onException(response,error)) {
				return;
			}
			success(response);
		};
		return fun;
	},
	
	onException : function(response,error) {
		if(response.errCode != null) {
			if(error) {
				error(response);
			}
			console.warn("rpc response error:"+response.errCode+" msg:"+response.errMsg);
			return true;
		}
		return false;
	},
	
	hasProps : function(obj) {
		for(var prop in obj) {
			return true;
		}
		return false;
	},
	
	getJsonFromSessionStorage : function(key,defaultValue) {
		var json = sessionStorage ? sessionStorage.getItem(key) : null;
		if (json !== null && json !== undefined && json !=='undefined' && json !=='null' && json.length > 0) {
			return JSON.parse(json);
		}
		return defaultValue ? defaultValue : null;
	},
	
	toSelect2Options : function(rows,optionKey,optionLabel) {
		var result = [];
		for(var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var option = {
					//select2
					text:row[optionLabel],
					id:row[optionKey],
					//vue select
					label:row[optionLabel],
					value:row[optionKey]
			};
			result.push(option);
		}
		return result;
	}
}

var reCommentContents = /\/\*!?(?:\@preserve)?[ \t]*(?:\r\n|\n)([\s\S]*?)(?:\r\n|\n)[ \t]*\*\//;

var multiline = function (fn) {
	if (typeof fn !== 'function') {
		throw new TypeError('Expected a function');
	}

	var match = reCommentContents.exec(fn.toString());

	if (!match) {
		throw new TypeError('Multiline comment missing.');
	}

	return match[1];
};

$.jsonp = function(url,data,success,dataType) {
	var jsonpUrl = url.indexOf("?") >=0 ? url + "&__format=jsonp" : url+"?__format=jsonp";
	$.ajax({
		url: jsonpUrl,
		data: data,
		success: success,
		type:"post",
		dataType: "jsonp",
		jsonp: "__jsoncallback"
	});
}
