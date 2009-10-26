<%@tag body-content="scriptless"%>
<%@ attribute name="collection" required="true" type="java.util.Collection" %>
<%@ attribute name="selected" required="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<c:forEach items="${collection}" var="country">

<option value="${country.code}" ${not empty selected and selected eq country.code ? 'selected="selected"' :'' } } >${country.name}</option>
</c:forEach>

