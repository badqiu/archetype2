<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="paginator" required="true" type="com.github.rapid.common.util.page.Paginator" description="Paginator" %>
<%@ attribute name="pageSizeSelectList" type="java.lang.Number[]" required="false"  %>
<%@ attribute name="isShowPageSizeList" type="java.lang.Boolean" required="false"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	// set default values
	Integer[] defaultPageSizes = new Integer[]{10,50,100};
	if(jspContext.getAttribute("pageSizeSelectList") == null) {
		jspContext.setAttribute("pageSizeSelectList",defaultPageSizes); 
	}
	
	if(jspContext.getAttribute("isShowPageSizeList") == null) {
		jspContext.setAttribute("isShowPageSizeList",true); 
	} 
%>

<div class="row col-sm-12" style="margin-top:5px;margin-bottom:5px;">
		
	<div  class="pull-left" >
		<jsp:doBody/>
	</div>
	
	<div class="pull-right">
		<span class="buttonLabel">${paginator.startRow} - ${paginator.endRow} of ${paginator.totalItems}</span>
		
		<c:choose>
		<c:when test="${paginator.firstPage}"><img src="<c:url value='/widgets/simpletable/images/firstPageDisabled.gif'/>" style="border:0" ></c:when>
		<c:otherwise><a href="javascript:simpleTable.togglePage(1);"><img src="<c:url value='/widgets/simpletable/images/firstPage.gif'/>" style="border:0" ></a></c:otherwise>
		</c:choose>
		
		<c:choose>
		<c:when test="${paginator.hasPrePage}"><a href="javascript:simpleTable.togglePage(${paginator.prePage});"><img src="<c:url value='/widgets/simpletable/images/prevPage.gif'/>" style="border:0" ></a></c:when>
		<c:otherwise><img src="<c:url value='/widgets/simpletable/images/prevPageDisabled.gif'/>" style="border:0" ></c:otherwise>
		</c:choose>
		
		<c:forEach var="item" items="${paginator.slider}">
		<c:choose>
		<c:when test="${item == paginator.page}">[${item}]</c:when>
		<c:otherwise><a href="javascript:simpleTable.togglePage(${item});">[${item}]</a></c:otherwise>
		</c:choose>
		</c:forEach>
		
		<c:choose>
		<c:when test="${paginator.hasNextPage}"><a href="javascript:simpleTable.togglePage(${paginator.nextPage});"><img src="<c:url value='/widgets/simpletable/images/nextPage.gif'/>" style="border:0" ></a></c:when>
		<c:otherwise><img src="<c:url value='/widgets/simpletable/images/nextPageDisabled.gif'/>" style="border:0" ></c:otherwise>
		</c:choose>
		
		<c:choose>
		<c:when test="${paginator.lastPage}"><img src="<c:url value='/widgets/simpletable/images/lastPageDisabled.gif'/>" style="border:0"></c:when>
		<c:otherwise><a href="javascript:simpleTable.togglePage(${paginator.totalPages});"><img src="<c:url value='/widgets/simpletable/images/lastPage.gif'/>" style="border:0" ></a></c:otherwise>
		</c:choose>
		
		<c:if test="${isShowPageSizeList}">
		<select onChange="simpleTable.togglePageSize(this.value)">
			<c:forEach var="item" items="${pageSizeSelectList}">
				<option value="${item}" ${paginator.pageSize == item ? 'selected' : '' }>${item}</option>
			</c:forEach> 
		</select>
		</c:if>
	</div>
</div>
