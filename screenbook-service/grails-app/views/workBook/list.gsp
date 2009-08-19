

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>WorkBook List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New WorkBook</g:link></span>
        </div>
        <div class="body">
            <h1>WorkBook List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <th>Book</th>
                   	    
                   	        <th>Student</th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${workBookInstanceList}" status="i" var="workBookInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${workBookInstance.id}">${fieldValue(bean:workBookInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:workBookInstance, field:'book')}</td>
                        
                            <td>${fieldValue(bean:workBookInstance, field:'student')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${workBookInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
