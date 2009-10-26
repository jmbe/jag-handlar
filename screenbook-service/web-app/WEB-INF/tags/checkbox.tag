<%@tag body-content="scriptless"%>
<%@ attribute name="key" required="true"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="checked" required="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="id" value="${empty id ? name : id}" />

<table>
<tr>
<td valign="middle">
<input type="checkbox" name="${name}" id="${id}" class="checkbox" style="width: auto;"
    ${checked ? 'checked="checked" ':''}  />
</td>
<td valign="middle">
<label class="checkbox" for="${id}"><fmt:message key="${key}" /></label>
</td>
</tr>
</table>
