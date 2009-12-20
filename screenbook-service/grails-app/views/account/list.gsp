<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>${title}</title>
    </head>
    <body>
        <div class="nav">
          <span class="menuButton not-important"><a class="home" href="${resource(dir:'/account/list')}">Hem</a></span>
          <span class="menuButton"><g:link class="list" action="list">Alla konton</g:link></span>
          <span class="menuButton"><g:link class="list-new" action="listNew">Nya konton</g:link></span>
          <span class="menuButton"><g:link class="list-newsletter" action="listNewsletter">Nyhetsbrev</g:link></span>
          <span class="menuButton not-important"><g:link class="create" action="create">Nytt konto</g:link></span>
        </div>
        <div class="body">
            <h1>${title}</h1>


            <g:if test="${newsletter}">
              <div class="newsletter">
                <g:link class="" action="exportSubscriptionList">Exportera listan till fil</g:link>
              </div>
            </g:if>
              

            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="username" title="Användarnamn" />

                            <g:sortableColumn property="email" title="Epostadress" />
                        
                   	        <g:sortableColumn property="newsLetterSubscribe" title="Nyhetsbrev" />

                            <g:sortableColumn property="newAccount" title="Nytt konto" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accountInstanceList}" status="i" var="accountInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" class="user" id="${accountInstance.id}">${fieldValue(bean:accountInstance, field:'username')}</g:link></td>
                        

                            <td>${fieldValue(bean:accountInstance, field:'email')}</td>
                        
                            <td>
                              <c:choose>
                                <c:when test="${fieldValue(bean:accountInstance, field:'newsLetterSubscribe')}">
                                  Ja
                                </c:when>
                                <c:otherwise>
                                  Nej
                                </c:otherwise>
                              </c:choose>
                            </td>

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

