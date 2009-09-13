

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Account List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Account</g:link></span>
        </div>
        <div class="body">
            <h1>Account List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="username" title="Username" />
                        
                   	        <g:sortableColumn property="passwd" title="Password" />

                            <g:sortableColumn property="passwd" title="Apikey" />
                        
                   	        <g:sortableColumn property="enabled" title="Enabled" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accountInstanceList}" status="i" var="accountInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accountInstance.id}">${fieldValue(bean:accountInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:accountInstance, field:'username')}</td>
                        
                            <td>${fieldValue(bean:accountInstance, field:'passwd')}</td>

                            <td>${fieldValue(bean:accountInstance, field:'apikey')}</td>
                        
                            <td>${fieldValue(bean:accountInstance, field:'enabled')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accountInstanceTotal}" />
            </div>
        </div>
    </body>
</html>

