<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/commons/taglibs.jsp" %>

<rapid:override name="head">
	<title>Vue Router Demo</title>

	<script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>

</rapid:override>


<rapid:override name="content">
	
	<div id="app">
		  <h1>Vue Router Demo</h1>
		  <p>
		  	<router-link to="/group">Go to Foo</router-link>
		  	<router-link to="/user">Go to Bar</router-link>
		  </p>
		  <router-view></router-view>
	</div>
	
	<script type="text/javascript">
		//Vue.use(VueRouter);
		var routes = [
                      {path:'/group',component: GroupMain},
                      {path:'/user',component: UserMain}
                      ];
		
		var router = new VueRouter({routes: routes});
		
		new Vue({router:router}).$mount('#app');
	</script>
</rapid:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>

