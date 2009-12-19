<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>${title}</title>
    </head>
    <body>
        <div class="nav">
          <span class="menuButton"><a class="home" href="${resource(dir:'/')}">Hem</a></span>
          <span class="menuButton"><g:link class="list" action="list">Alla konton</g:link></span>
          <span class="menuButton"><g:link class="list-new" action="listNew">Nya konton</g:link></span>
          <span class="menuButton"><g:link class="create" action="create">Nytt konto</g:link></span>
        </div>
        <div class="body">
            <h1>${title}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="username" title="Username" />
                        
                   	        <g:sortableColumn property="passwd" title="Password" />

                            <g:sortableColumn property="passwd" title="Apikey" />

                            <g:sortableColumn property="email" title="Email" />
                        
                   	        <g:sortableColumn property="enabled" title="Enabled" />

                            <g:sortableColumn property="newAccount" title="Nytt konto" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accountInstanceList}" status="i" var="accountInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accountInstance.id}">${fieldValue(bean:accountInstance, field:'username')}</g:link></td>
                        
                            <td>${fieldValue(bean:accountInstance, field:'passwd')}</td>

                            <td>${fieldValue(bean:accountInstance, field:'apikey')}</td>

                            <td>${fieldValue(bean:accountInstance, field:'email')}</td>
                        
                            <td>${fieldValue(bean:accountInstance, field:'enabled')}</td>

                            <td>
                              <c:choose>
                                <c:when test="${fieldValue(bean:accountInstance, field:'newAccount')}">
                                  <g:link class="yes" action="show"  id="${accountInstance.id}"><span class="yes">Ja</span></g:link>

                                </c:when>
                                <c:otherwise>
                                  <span class="not-important">Nej</span>
                                </c:otherwise>

                              </c:choose>
                              </td>

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

