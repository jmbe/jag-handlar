

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Role</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Role List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Role</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Role</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${roleInstance}">
            <div class="errors">
                <g:renderErrors bean="${roleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${roleInstance?.id}" />
                <input type="hidden" name="version" value="${roleInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="authority">Authority:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roleInstance,field:'authority','errors')}">
                                    <input type="text" id="authority" name="authority" value="${fieldValue(bean:roleInstance,field:'authority')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roleInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:roleInstance,field:'description')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="people">People:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:roleInstance,field:'people','errors')}">
                                    <g:select name="people"
from="${Account.list()}"
size="5" multiple="yes" optionKey="id"
value="${roleInstance?.people}" />

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
