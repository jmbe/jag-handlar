

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Student</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Student List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Student</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Student</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${studentInstance}">
            <div class="errors">
                <g:renderErrors bean="${studentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${studentInstance?.id}" />
                <input type="hidden" name="version" value="${studentInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="username">Username:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:studentInstance,field:'username','errors')}">
                                    <input type="text" id="username" name="username" value="${fieldValue(bean:studentInstance,field:'username')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="account">Account:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:studentInstance,field:'account','errors')}">
                                    <g:select optionKey="id" from="${Account.list()}" name="account.id" value="${studentInstance?.account?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="books">Books:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:studentInstance,field:'books','errors')}">
                                    
<ul>
<g:each var="b" in="${studentInstance?.books?}">
    <li><g:link controller="workBook" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="workBook" params="['student.id':studentInstance?.id]" action="create">Add WorkBook</g:link>

                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
