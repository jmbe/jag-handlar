

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Answer List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Answer</g:link></span>
        </div>
        <div class="body">
            <h1>Answer List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="question_key" title="Questionkey" />
                        
                   	        <g:sortableColumn property="answer" title="Answer" />
                        
                   	        <th>Student</th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${answerInstanceList}" status="i" var="answerInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${answerInstance.id}">${fieldValue(bean:answerInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:answerInstance, field:'question_key')}</td>
                        
                            <td>${fieldValue(bean:answerInstance, field:'answer')}</td>
                        
                            <td>${fieldValue(bean:answerInstance, field:'student')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${answerInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
