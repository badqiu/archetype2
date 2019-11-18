
if(window.location.hostname == 'localhost') {
	var appMode = 'dev';
}else {
	var appMode = 'prod';
}

if(appMode=='dev') {
	var globalWsUrl="http://localhost:6060/rpc";
}else {
	var globalWsUrl="http://webservice.demoproject.company.com/rpc";
}