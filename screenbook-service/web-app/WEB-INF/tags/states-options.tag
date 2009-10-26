<%@tag body-content="scriptless"%>
<%@ attribute name="collection" required="true" type="java.util.Collection" %>
<%@ attribute name="selected" required="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<c:forEach items="${collection}" var="state">

<option value="${state.abbreviation}" ${not empty selected and selected eq state.abbreviation ? 'selected="selected"' :'' } } >${state.name}</option>
</c:forEach>

