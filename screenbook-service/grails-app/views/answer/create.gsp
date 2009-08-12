

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Answer</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Answer List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Answer</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${answerInstance}">
            <div class="errors">
                <g:renderErrors bean="${answerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                                    <label for="student">Student:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:answerInstance,field:'student','errors')}">
                                    <g:select optionKey="id" from="${Student.list()}" name="student.id" value="${answerInstance?.student?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
