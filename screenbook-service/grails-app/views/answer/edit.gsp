

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Answer</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Answer List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Answer</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Answer</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${answerInstance}">
            <div class="errors">
                <g:renderErrors bean="${answerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${answerInstance?.id}" />
                <input type="hidden" name="version" value="${answerInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="question_key">Questionkey:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'question_key','errors')}">
                                    <input type="text" id="question_key" name="question_key" value="${fieldValue(bean:answerInstance,field:'question_key')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="answer">Answer:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'answer','errors')}">
                                    <input type="text" id="answer" name="answer" value="${fieldValue(bean:answerInstance,field:'answer')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="book">Book:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'book','errors')}">
                                    <g:select optionKey="id" from="${WorkBook.list()}" name="book.id" value="${answerInstance?.book?.id}" ></g:select>
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
