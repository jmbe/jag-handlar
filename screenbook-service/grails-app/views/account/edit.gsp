

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Account</title>
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
            <h1>Edit Account</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${accountInstance}">
            <div class="errors">
                <g:renderErrors bean="${accountInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${accountInstance?.id}" />
                <input type="hidden" name="version" value="${accountInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="username">Username:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'username','errors')}">
                                    <input type="text" id="username" name="username" value="${fieldValue(bean:accountInstance,field:'username')}"/>
                                </td>
                            </tr> 

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="passwd">Passwd:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'passwd','errors')}">
                                    <input type="text" id="passwd" name="passwd" value=""/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email">Email:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'email','errors')}">
                                    <input type="text" id="email" name="email" value="${fieldValue(bean:accountInstance,field:'email')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="contactPerson">Kontaktperson:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'contactPerson','errors')}">
                                    <input type="text" id="contactPerson" name="contactPerson" value="${fieldValue(bean:accountInstance,field:'contactPerson')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="phoneNumber">Telefonnummer:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'phoneNumber','errors')}">
                                    <input type="text" id="phoneNumber" name="phoneNumber" value="${fieldValue(bean:accountInstance,field:'phoneNumber')}"/>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled">Enabled:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'enabled','errors')}">
                                    <g:checkBox name="enabled" value="${accountInstance?.enabled}" ></g:checkBox>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled">Nytt konto:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'newAccount','errors')}">
                                    <g:checkBox name="newAccount" value="${accountInstance?.newAccount}" ></g:checkBox>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled">Skickat påminnelse dag före:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'dayBeforeNoticeSent','errors')}">
                                    <g:checkBox name="dayBeforeNoticeSent" value="${accountInstance?.dayBeforeNoticeSent}" ></g:checkBox>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled">Skickat påminnelse 2v före:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'twoWeeksNoticeSent','errors')}">
                                    <g:checkBox name="twoWeeksNoticeSent" value="${accountInstance?.twoWeeksNoticeSent}" ></g:checkBox>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled">Skickat påminnelse 6v före:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'sixWeeksNoticeSent','errors')}">
                                    <g:checkBox name="sixWeeksNoticeSent" value="${accountInstance?.sixWeeksNoticeSent}" ></g:checkBox>
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label>Authorities:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:accountInstance,field:'authorities','errors')}">
                                    
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
