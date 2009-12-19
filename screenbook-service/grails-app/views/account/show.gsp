<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Visa konto</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Hem</a></span>
            <span class="menuButton"><g:link class="list" action="list">Alla konton</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">Nytt konto</g:link></span>
        </div>
        <div class="body">
            <h1>Konto</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:accountInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Username:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:accountInstance, field:'username')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Passwd:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:accountInstance, field:'passwd')}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">apikey:</td>

                            <td valign="top" class="value">${fieldValue(bean:accountInstance, field:'apikey')}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">email:</td>

                            <td valign="top" class="value">${fieldValue(bean:accountInstance, field:'email')}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Enabled:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:accountInstance, field:'enabled')}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name">Bookmark reminder:</td>

                            <td valign="top" class="value">${fieldValue(bean:accountInstance, field:'showBookmarkReminder')}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Authorities:</td>
                            
                            <td  valign="top" style="text-align:left;" class="value">
                                <ul>
                                <g:each var="a" in="${accountInstance.authorities}">
                                    <li><g:link controller="role" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${accountInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="Ändra" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete2000" onclick="return confirm('Are you sure?');" value="Ta bort" /></span>
                </g:form>
            </div>

        </div>

        <div class="purchases" style="float:left">
            <h1>Fakturor</h1>
            <table>
                <thead>
                  <tr>
                    <th>Kundnummer</th>
                    <th>Licens</th>
                    <th>Köpdatum</th>
                    <th>Fakturadatum</th>
                    <th>Avslutas</th>
                    <th>Fakturastatus</th>
                    <th>&nbsp;</th>
                  </tr>
                </thead>
                <g:each in="${accountInstance?.purchases}" var="purchase">
                    <tr>
                      <td>${purchase.customerNumber}</td>
                      <td>${purchase.license} (${purchase.amount} ${purchase.currency})</td>
                      <td><fmt:formatDate value="${purchase.purchaseDate}"
                        dateStyle="short" type="date" /></td>
                      <td><fmt:formatDate value="${purchase.invoiceDate}" dateStyle="short"
                        type="date" /></td>
                      <td><fmt:formatDate value="${purchase.endDate}" dateStyle="short"
                        type="date" /></td>
                      <td><c:choose>
                        <c:when test="${purchase.invoiceSent}">
                          <span>Fakturerad</span>
                        </c:when>
                        <c:otherwise>
                          <span>Ej fakturerad</span>
                        </c:otherwise>
                      </c:choose></td>


                      <td class="buttons">
                        <g:form>
                          <input type="hidden" name="accountId" value="${accountInstance?.id}" />
                          <input type="hidden" name="purchaseId" value="${purchase.id}" />
                          <span class="button"><g:actionSubmit class="accept" action="activatePurchase"  value="Godkänn köp (faktura skickad)" /></span>
                        </g:form>
                      </td>

                    </tr>
                </g:each>
              </table>
            </div>
    </body>
</html>
